package top.biduo.exchange.ui.my_promotion;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.PromotionRecordAdapter;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.base.BaseLazyFragment;
import top.biduo.exchange.entity.PromotionRecord;
import top.biduo.exchange.utils.WonderfulCodeUtils;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class PromotionRecordFragment extends BaseLazyFragment implements PromotionRecordContract.View{

    @BindView(R.id.rvPeomotionFriend)
    RecyclerView rvPeomotionFriend;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private int page=1;

    private PromotionRecordContract.Presenter presenter;
    private List<PromotionRecord> records = new ArrayList<>();
    private PromotionRecordAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_promotion_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new PromotionRecordPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        page=1;
        presenter.getPromotion(getmActivity().getToken(),page+"","20");
        adapter.setEnableLoadMore(false);
        adapter.loadMoreEnd(false);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        initRv();
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPeomotionFriend.setLayoutManager(manager);
        adapter = new PromotionRecordAdapter(R.layout.adapter_promotion_record, records);
        adapter.bindToRecyclerView(rvPeomotionFriend);
        adapter.setEmptyView(R.layout.empty_no_message);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvPeomotionFriend);
        adapter.setEnableLoadMore(false);



    }

    private void loadMore() {
        page=page+1;
        refreshLayout.setEnabled(false);
        presenter.getPromotion(getmActivity().getToken(),page+"","20");

    }

    @Override
    protected void loadData() {
        presenter.getPromotion(getmActivity().getToken(),page+"","20");
        isNeedLoad = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(PromotionRecordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getPromotionFail(Integer code, String toastMessage) {
        try {
            adapter.setEnableLoadMore(true);
            refreshLayout.setEnabled(true);
            refreshLayout.setRefreshing(false);
            WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
        }catch (Exception e){

        }

    }

    @Override
    public void getPromotionSuccess(List<PromotionRecord> obj) {
        try {
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
            refreshLayout.setEnabled(true);
            refreshLayout.setRefreshing(false);
            if (page==1){
                records.clear();
                if (obj.size() == 0) adapter.loadMoreEnd();
                else this.records.addAll(obj);
            }else {
                if (obj.size() != 0) {
                    this.records.addAll(obj);
                }else {
                    adapter.loadMoreEnd();
                }
            }
            adapter.notifyDataSetChanged();
            adapter.disableLoadMoreIfNotFullPage();
        }catch (Exception e){

        }

    }
}
