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
import top.biduo.exchange.adapter.ScoreRecordAdapter;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.base.BaseLazyFragment;
import top.biduo.exchange.entity.PromotionReward;
import top.biduo.exchange.entity.ScoreRecordBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class PromotionRewardFragment extends BaseLazyFragment implements PromotionRewardContract.View{

    @BindView(R.id.rvPromotionReward)
    RecyclerView rvPromotionReward;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private int page=1;

    private PromotionRewardContract.Presenter presenter;
    private List<ScoreRecordBean> rewards = new ArrayList<>();
    private ScoreRecordAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_promotion_reward;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new PromotionRewardPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        page=1;
        presenter.getPromotionReward(getmActivity().getToken(),page+"","20");
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
        rvPromotionReward.setLayoutManager(manager);
        adapter = new ScoreRecordAdapter(R.layout.adapter_score_record, rewards);
        adapter.bindToRecyclerView(rvPromotionReward);
        adapter.setEmptyView(R.layout.empty_no_message);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvPromotionReward);
        adapter.setEnableLoadMore(false);

    }

    private void loadMore() {
        page=1+page;
        refreshLayout.setEnabled(false);
        presenter.getPromotionReward(getmActivity().getToken(),page+"","20");

    }

    @Override
    protected void loadData() {
        presenter.getPromotionReward(getmActivity().getToken(),page+"","20");
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
    public void setPresenter(PromotionRewardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getPromotionRewardFail(Integer code, String toastMessage) {
        adapter.setEnableLoadMore(true);
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void getPromotionRewardSuccess(List<ScoreRecordBean> obj) {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);

        if (page==1){
            rewards.clear();
            if (obj.size() == 0) adapter.loadMoreEnd();
            else this.rewards.addAll(obj);
        }else {
            if (obj.size() != 0) {
                this.rewards.addAll(obj);
            }else {
                adapter.loadMoreEnd();
            }
        }

        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();
    }
}
