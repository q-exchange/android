package top.biduo.exchange.ui.my_order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.ui.order_detail.OrderDetailActivity;
import top.biduo.exchange.adapter.OrderAdapter;
import top.biduo.exchange.base.BaseLazyFragment;
import top.biduo.exchange.entity.Order;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

/**
 * Created by Administrator on 2018/2/5.
 */

public class OrderFragment extends BaseLazyFragment implements OrderContract.View {
    
    @BindView(R.id.rvIngOrder)
    RecyclerView rvIngOrder;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private List<Order> orders = new ArrayList<>();
    private OrderAdapter adapter;
    private Status status;
    private OrderContract.Presenter presenter;
    private int pageNo = 0;
    private int pageSize = 20;

    public static OrderFragment getInstance(Status status) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ing_order;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new OrderPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        presenter.myOrder(getmActivity().getToken(), status.getStatus(), pageNo = 0, pageSize);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreEnd(false);
    }

    @Override
    protected void obtainData() {
        this.status = (Status) getArguments().getSerializable("status");
    }

    @Override
    protected void fillWidget() {
        initRv();
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvIngOrder.setLayoutManager(manager);
        adapter = new OrderAdapter(getActivity(),R.layout.adapter_order, orders);
        adapter.bindToRecyclerView(rvIngOrder);
        adapter.setEmptyView(R.layout.empty_no_order);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvIngOrder);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailActivity.actionStart(getActivity(), orders.get(position).getOrderSn());
            }
        });
        adapter.setEnableLoadMore(false);
    }

    private void loadMore() {
        refreshLayout.setEnabled(false);
        presenter.myOrder(SharedPreferenceInstance.getInstance().getTOKEN(), status.getStatus(), ++pageNo, pageSize);
    }

    @Override
    protected void loadData() {
        //SharedPreferenceInstance.getInstance().getTOKEN()
        presenter.myOrder(SharedPreferenceInstance.getInstance().getTOKEN(), status.getStatus(), pageNo, pageSize);
//        presenter.myOrder(getmActivity().getToken(), status.getStatus(), pageNo, pageSize);
        isNeedLoad = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void myOrderFail(Integer code, String toastMessage) {
        try {
            adapter.setEnableLoadMore(true);
            refreshLayout.setEnabled(true);
            refreshLayout.setRefreshing(false);
            WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void myOrderSuccess(List<Order> obj) {
        try {
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
            refreshLayout.setEnabled(true);
            refreshLayout.setRefreshing(false);
            if (obj == null) return;
            if (pageNo == 0) this.orders.clear();
            else if (obj.size() == 0) adapter.loadMoreEnd();
            this.orders.addAll(obj);
            adapter.notifyDataSetChanged();
            adapter.disableLoadMoreIfNotFullPage();
        }catch (Exception e){

        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public enum Status {
        CANC(0, MyApplication.getApp().getResources().getStringArray(R.array.order_status)[3]),
        UNPAID(1, MyApplication.getApp().getResources().getStringArray(R.array.order_status)[0]),
        PAID(2, MyApplication.getApp().getResources().getStringArray(R.array.order_status)[1]),
        DONE(3, MyApplication.getApp().getResources().getStringArray(R.array.order_status)[2]),
        COMPLAINING(4, MyApplication.getApp().getResources().getStringArray(R.array.order_status)[4]);

        private int status;
        private String statusStr;

        Status(int status, String statusStr) {
            this.status = status;
            this.statusStr = statusStr;
        }

        public int getStatus() {
            return status;
        }

        public String getStatusStr() {
            return statusStr;
        }
    }
}
