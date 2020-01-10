package top.biduo.exchange.ui.main.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.kline.KlineActivity;
import top.biduo.exchange.ui.main.MarketBaseFragment;
import top.biduo.exchange.ui.main.presenter.CommonPresenter;
import top.biduo.exchange.ui.main.presenter.ICommonView;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.adapter.HomesAdapter;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ETHMarketFragment extends MarketBaseFragment implements ICommonView {

    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    private HomesAdapter adapter;
    private List<Currency> currencies = new ArrayList<>();
    private CommonPresenter commonPresenter;

    public static ETHMarketFragment getInstance( ) {
        ETHMarketFragment ethMarketFragment = new ETHMarketFragment();
        Bundle bundle = new Bundle();
        ethMarketFragment.setArguments(bundle);
        return ethMarketFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_eth_market;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new CommonPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        initRvContent();
    }

    private void initRvContent() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(manager);
        adapter = new HomesAdapter( currencies, 1);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                collectClick(position);
//            }
//        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KlineActivity.actionStart(getActivity(), currencies.get(position).getSymbol());
            }
        });
        adapter.isFirstOnly(true);
        rvContent.setAdapter(adapter);
        View head=View.inflate(getActivity(),R.layout.adapter_home_head,null);
        adapter.setHeaderView(head);
    }

    private void collectClick(int position) {
        if (!MyApplication.getApp().isLogin()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            return;
        }
        String symbol = currencies.get(position).getSymbol();
        if (currencies.get(position).isCollect()) commonPresenter.delete(getmActivity().getToken(), symbol, position);
        else commonPresenter.add(getmActivity().getToken(), symbol, position);
    }

    @Override
    protected void loadData() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    public void dataLoaded(List<Currency> baseEth) {
        this.currencies.clear();
        this.currencies.addAll(baseEth);
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(CommonPresenter presenter) {
        this.commonPresenter = presenter;
    }

    @Override
    public void deleteFail(Integer code, String toastMessage) {
        if (code == GlobalConstant.TOKEN_DISABLE1) LoginActivity.actionStart(getActivity());
        else WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void deleteSuccess(String obj, int position) {
        this.currencies.get(position).setCollect(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addFail(Integer code, String toastMessage) {
        if (code == GlobalConstant.TOKEN_DISABLE1) LoginActivity.actionStart(getActivity());
        else WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void addSuccess(String obj, int position) {
        this.currencies.get(position).setCollect(true);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getActivity().getSupportFragmentManager().putFragment(outState, "USDT", this);
    }

    public void tcpNotify() {
        if (getUserVisibleHint() && adapter != null) adapter.notifyDataSetChanged();
    }
}
