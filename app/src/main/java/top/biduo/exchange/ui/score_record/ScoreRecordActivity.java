package top.biduo.exchange.ui.score_record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.ScoreRecordAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.ScoreRecordBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class ScoreRecordActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    int pageNum = 1;
    int pageSize = 20;

    List<ScoreRecordBean> mData = new ArrayList<>();
    private ScoreRecordAdapter adapter;
    @BindView(R.id.rc_score_record)
    RecyclerView rc_score_record;
    @BindView(R.id.tv_current_score)
    TextView tv_current_score;
    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_score_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_current_score.setText(MyApplication.getApp().getCurrentUser().getIntegration());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_score_record.setLayoutManager(manager);
        adapter = new ScoreRecordAdapter(R.layout.adapter_score_record, mData);
        adapter.isFirstOnly(true);
        rc_score_record.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getScoreRecord();
            }
        },rc_score_record);
    }

    @Override
    protected void obtainData() {

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
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getScoreRecord();
    }

    private void getScoreRecord() {
        RemoteDataSource.getInstance().getScoreRecord(getToken(), pageNum + "", pageSize + "", "", "", "", new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                List<ScoreRecordBean> scoreRecordBeans = (List<ScoreRecordBean>) obj;
                updateRecyclerView(scoreRecordBeans);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(ScoreRecordActivity.this,code,toastMessage);
            }
        });
    }

    private void updateRecyclerView(List<ScoreRecordBean> scoreRecordBeans) {
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


}
