package top.biduo.exchange.ui.score_record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.CandyRecordAdapter;
import top.biduo.exchange.adapter.ScoreRecordAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.CandyRecordBean;
import top.biduo.exchange.entity.ScoreRecordBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class CandyRecordActivity extends BaseActivity {
    List<CandyRecordBean> mData = new ArrayList<>();
    private CandyRecordAdapter adapter;
    @BindView(R.id.rc_candy_record)
    RecyclerView rc_candy_record;
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_candy_box;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_candy_record.setLayoutManager(manager);
        adapter = new CandyRecordAdapter(R.layout.adapter_score_record, mData);
        adapter.isFirstOnly(true);
        rc_candy_record.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getCandyRecord();
            }
        },rc_candy_record);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            isSetTitle = true;
            ImmersionBar.setTitleBar(this, llTitle);
        }
    }
    @Override
    protected void loadData() {
        getCandyRecord();
    }

    int pageNum=1;
    int pageSize=20;


    private void getCandyRecord() {
        RemoteDataSource.getInstance().getCandyRecord(getToken(), pageNum + "", pageSize + "", "", "", new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                List<CandyRecordBean> beans = new Gson().fromJson((String) obj, new TypeToken<List<CandyRecordBean>>() {
                }.getType());
                updateRecyclerView(beans);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(CandyRecordActivity.this,code,toastMessage);
            }
        });
    }

    private void updateRecyclerView(List<CandyRecordBean> beans) {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        if (beans == null) {
            return;
        }
        if (pageNum == 1) {
            mData.clear();
            if (beans.size() == 0) {
                adapter.loadMoreEnd();
            } else {
                mData.addAll(beans);
            }
        } else {
            if (beans.size() != 0) {
                mData.addAll(beans);
            } else {
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();


    }

}
