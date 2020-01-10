package top.biduo.exchange.ui.main.drawer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.main.MarketBaseFragment;
import top.biduo.exchange.ui.main.presenter.CommonPresenter;
import top.biduo.exchange.ui.main.presenter.ICommonView;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.adapter.Homes2Adapter;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Favorite2Fragment extends MarketBaseFragment implements ICommonView {

    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    private Homes2Adapter adapter;
    private List<Currency> currencies = new ArrayList<>();
    private List<Currency> favorites = new ArrayList<>();
    private CommonPresenter commonPresenter;

    public static Favorite2Fragment getInstance( ) {
        Favorite2Fragment favoriteFragment = new Favorite2Fragment();
        Bundle bundle = new Bundle();
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }
    public void isChange(boolean isLoad){
        if(adapter!=null){
            adapter.setLoad(isLoad);
            adapter.notifyDataSetChanged();
        }
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
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    protected void fillWidget() {
        initRvContent();
    }

    private void initRvContent() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(manager);
        adapter = new Homes2Adapter( favorites, 2);
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
                WonderfulLogUtils.logi("jiejie","  FavoriteFragment  symble    "+Favorite2Fragment.this.adapter.getItem(position).getSymbol());
                ((MarketOperateCallback) getActivity()).itemClick(Favorite2Fragment.this.adapter.getItem(position) );
            }
        });
        adapter.isFirstOnly(true);
        rvContent.setAdapter(adapter);
    }

    private void collectClick(int position) {
        if (!MyApplication.getApp().isLogin()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            return;
        }
        String symbol = favorites.get(position).getSymbol();
        if (favorites.get(position).isCollect()) commonPresenter.delete(getmActivity().getToken(), symbol, position);
        else commonPresenter.add(getmActivity().getToken(), symbol, position);
    }

    @Override
    protected void loadData() {
        notifyData();
    }

    private void notifyData() {
        if (adapter == null) return;
        this.favorites.clear();
        for (Currency currency : currencies) {
            if (currency.isCollect()) this.favorites.add(currency);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    public void dataLoaded(List<Currency> currencies) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
        this.favorites.clear();
        for (Currency currency : currencies) {
            if (currency.isCollect()) this.favorites.add(currency);
        }
//        Log.d("jiejie","234---" + favorites.size());
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
        this.favorites.get(position).setCollect(false);
        notifyData();
    }

    @Override
    public void addFail(Integer code, String toastMessage) {
        if (code == GlobalConstant.TOKEN_DISABLE1) LoginActivity.actionStart(getActivity());
        else WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void addSuccess(String obj, int position) {
        this.favorites.get(position).setCollect(true);
        notifyData();
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
