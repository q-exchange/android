package top.biduo.exchange.ui.main.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import top.biduo.exchange.R;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.ui.fiat_exchange.FiatExchangeActivity;
import top.biduo.exchange.ui.main.TestActivity;
import top.biduo.exchange.utils.sysinfo.QMUIStatusBarHelper;
import top.biduo.exchange.ui.main.MainActivity;

public class ThreeFragment extends BaseTransFragment {
    public static final String TAG = ThreeFragment.class.getSimpleName();
    Coin2CoinFragment coin2CoinFragment = new Coin2CoinFragment();
    BaseCoinFragment baseCoinFragment = new BaseCoinFragment();
    @BindView(R.id.ll_title)
    LinearLayout ll_title;

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initViewPager();
        new Coin2CoinPresenter(Injection.provideTasksRepository(getActivity()), coin2CoinFragment);
        new BaseCoinPresenter(Injection.provideTasksRepository(getActivity()), baseCoinFragment);

    }

    @BindViews({R.id.tv_title_exchange, R.id.tv_title_margin, R.id.tv_title_fiat})
    TextView[] titles;
    List<Fragment> fragmentList = new ArrayList<>();
    private void initViewPager() {
        fragmentList.add(coin2CoinFragment);
        fragmentList.add(baseCoinFragment);
        for(int i=0;i<titles.length;i++){
            final int finalI = i;
            titles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==2){
                        startActivity(new Intent(getActivity(), FiatExchangeActivity.class));
                        return;
                    }
                    setTitleSelectedByPosition(finalI);
                }
            });
        }
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container_trade,coin2CoinFragment);
        transaction.commitAllowingStateLoss();
        setTitleSelectedByPosition(0);
    }

    private void setTitleSelectedByPosition(int position) {
        for (TextView textView : titles) {
            textView.setSelected(textView == titles[position]);
            showTargetFragment(position);
        }
    }

    public void showPageFragment(Currency currency, int position) {
        if (coin2CoinFragment != null) {
            coin2CoinFragment.resetSymbol(currency, position);
        }
    }

    public void setViewContent(List<Currency> currencyListAll) {
        if (coin2CoinFragment != null) coin2CoinFragment.setViewContent(currencyListAll);
    }

    public void resetSymbol(Currency currency, int currentMenuType) {
        switch (currentMenuType) {
            case MainActivity.MENU_TYPE_EXCHANGE:
                if (coin2CoinFragment != null) {
                    coin2CoinFragment.resetSymbol(currency);
                }
                break;

            default:
        }
    }

    public void tcpNotify() {
        if (coin2CoinFragment != null) {
            coin2CoinFragment.tcpNotify();
        }
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    public void showTargetFragment(int position) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.show(fragmentList.get(position));
        transaction.hide(fragmentList.get((position+1)%2));
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(getActivity(), ll_title);
            isSetTitle = true;
        }
    }
}
