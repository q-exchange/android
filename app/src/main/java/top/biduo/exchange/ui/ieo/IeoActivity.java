package top.biduo.exchange.ui.ieo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.IeoAdapter;
import top.biduo.exchange.adapter.ScoreRecordAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.IeoBean;
import top.biduo.exchange.entity.ScoreRecordBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class IeoActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @BindView(R.id.tb_ieo)
    TabLayout tb_ieo;
    @BindView(R.id.rc_ieo)
    RecyclerView rc_ieo;

    @OnClick(R.id.ibBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void startIeoOrderRecord() {
        startActivity(new Intent(this,IeoOrderRecordActivity.class));
    }

    int selectedPosition = 0;
    private IeoAdapter adapter;
    List<IeoBean> mData = new ArrayList<>();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_ieo;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tb_ieo.addTab(tb_ieo.newTab().setText(getResources().getString(R.string.ieo_status_all)));
        tb_ieo.addTab(tb_ieo.newTab().setText(getResources().getString(R.string.ieo_status_preheating)));
        tb_ieo.addTab(tb_ieo.newTab().setText(getResources().getString(R.string.ieo_status_ongoing)));
        tb_ieo.addTab(tb_ieo.newTab().setText(getResources().getString(R.string.ieo_status_finish)));
        tb_ieo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedPosition = tab.getPosition();
                pageNum = 1;
                getIeoList(selectedPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_ieo.setLayoutManager(manager);
        adapter = new IeoAdapter(R.layout.adapter_ieo, mData);
        adapter.isFirstOnly(true);
        rc_ieo.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getIeoList(selectedPosition);
            }
        }, rc_ieo);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IeoDetailActivity.actionStart(IeoActivity.this, mData.get(position));
            }
        });
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getIeoList(selectedPosition);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

    int pageNum = 1;
    String pageSize = "20";
    String[] status = {"", "1", "2", "3"};//1-预热中，2-进行中，3-已结束

    private void getIeoList(int selectedPosition) {
        RemoteDataSource.getInstance().getIeoList(pageNum + "", pageSize, status[selectedPosition], getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                List<IeoBean> list = (List<IeoBean>) obj;
                updateRecyclerView(list);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(IeoActivity.this, code, toastMessage);
            }
        });
    }


    private void updateRecyclerView(List<IeoBean> scoreRecordBeans) {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        if (scoreRecordBeans == null) {
            return;
        }
        if (pageNum == 1) {
            mData.clear();
            if (scoreRecordBeans.size() == 0) {
                adapter.loadMoreEnd();
            } else {
                mData.addAll(scoreRecordBeans);
            }
        } else {
            if (scoreRecordBeans.size() != 0) {
                mData.addAll(scoreRecordBeans);
            } else {
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();
    }

    public static void actionStrat(Activity activity) {
        Intent intent = new Intent(activity, IeoActivity.class);
        activity.startActivity(intent);
    }
}
