package top.biduo.exchange.ui.main.asset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import org.xutils.common.util.LogUtil;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.PagerAdapter;
import top.biduo.exchange.adapter.TextWatcher;
import top.biduo.exchange.adapter.WalletAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseFragment;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.FiatAssetBean;
import top.biduo.exchange.entity.MarginAssetBean;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.common.ChooseCoinActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.ui.main.trade.ThreeFragment;
import top.biduo.exchange.ui.score_record.ScoreRecordActivity;
import top.biduo.exchange.ui.wallet.TiBiJLActivity;
import top.biduo.exchange.ui.wallet.WalletDialogFragment;
import top.biduo.exchange.ui.wallet_detail.WalletDetailActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulDpPxUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class AssetFragment extends BaseTransFragment {
    public static final String TAG = AssetFragment.class.getSimpleName();
    private List<Coin> exchangeCoins = new ArrayList<>();
    private List<MarginAssetBean> marginCoins = new ArrayList<>();
    private List<FiatAssetBean> fiatCoins = new ArrayList<>();

    @Override
    protected String getmTag() {
        return TAG;
    }

    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvCnyAmount)
    TextView tvCnyAmount;
    @BindView(R.id.llAccount)
    LinearLayout llAccount;
    @BindView(R.id.ivSee)
    ImageView ivSee;
    double sumUsd = 0;
    double sumCny = 0;
    private List<BaseFragment> fragments;
    @BindView(R.id.vp_asset)
    ViewPager mViewPager;
    @BindView(R.id.tb_asset)
    TabLayout mTabLayout;
    AssetExchangeFragment exchangeFragment = new AssetExchangeFragment();
    AssetFiatFragment fiatFragment = new AssetFiatFragment();
    ContractFragment contractFragment = new ContractFragment();
    private List<String> lists_chong = new ArrayList<>();
    private List<String> lists_ti = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected int getLayoutId() {
        return R.layout.asset_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initViewPager();
        ivSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSee();
            }
        });
        initTopSection();

    }

    private void initTopSection() {
        collapsingToolbar.setTitle(getString(R.string.assets));
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER);
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //避免toolbar拦截点击事件
                return ivSee.dispatchTouchEvent(event);
            }
        });
    }

    private void initViewPager() {
        final List<String> listTitle = new ArrayList<>();
        listTitle.add(getResources().getString(R.string.account_exchange));
        listTitle.add(getResources().getString(R.string.account_contract));
        listTitle.add(getResources().getString(R.string.account_fiat));
        fragments = new ArrayList<BaseFragment>() {{
            add(exchangeFragment);
            add(contractFragment);
            add(fiatFragment);
        }};
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getFragmentManager(), fragments, listTitle);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void switchSee() {
        if (!"*****".equals(tvCnyAmount.getText())) {
            tvCnyAmount.setText("*****");
            tvAmount.setText("********");
            Drawable drawable = getResources().getDrawable(R.drawable.icon_eye_guan);
            ivSee.setImageDrawable(drawable);
            SharedPreferenceInstance.getInstance().saveMoneyShowtype(2);
            setHideAmount(true);
        } else {
            tvAmount.setText(WonderfulMathUtils.getRundNumber(sumUsd, 8, null));
            tvCnyAmount.setText("≈"+WonderfulMathUtils.getRundNumber(sumCny, 2, null) + "CNY");
            Drawable drawable = getResources().getDrawable(R.drawable.icon_eye_open);
            ivSee.setImageDrawable(drawable);
            SharedPreferenceInstance.getInstance().saveMoneyShowtype(1);
            setHideAmount(false);
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

    private void getData(int pagePosition) {
        switch (pagePosition) {
            case 0:
                getExchangeAsset();
                break;
            case 2:
                getFiatAsset();
                break;
            default:
        }
    }


    private void getFiatAsset() {
        RemoteDataSource.getInstance().getFiatAsset(getmActivity().getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                fiatCoins.clear();
                fiatCoins.addAll((List<FiatAssetBean>) obj);
                fiatFragment.setData(fiatCoins);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulToastUtils.showToast(toastMessage);
            }
        });
    }


    private void getExchangeAsset() {
        RemoteDataSource.getInstance().myWallet(getmActivity().getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                exchangeCoins.clear();
                exchangeCoins.addAll((List<Coin>) obj);
                lists_chong.clear();
                lists_ti.clear();
                for (int i = 0; i < exchangeCoins.size(); i++) {
                    if (exchangeCoins.get(i).getCoin().getCanRecharge() == 1) {
                        lists_chong.add(exchangeCoins.get(i).getCoin().getUnit());
                    } else if (exchangeCoins.get(i).getCoin().getCanWithdraw() == 1) {
                        lists_ti.add(exchangeCoins.get(i).getCoin().getUnit());
                    }
                }
                exchangeFragment.setData(exchangeCoins);

            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulToastUtils.showToast(toastMessage);
            }
        });
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(getActivity(), appbar);
            isSetTitle = true;
        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> baseFragments;
        private List<String> listTitle;

        public MyPagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments, List<String> listTitle) {
            super(fm);
            this.baseFragments = baseFragments;
            this.listTitle = listTitle;
        }

        @Override
        public int getCount() {
            return baseFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return baseFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
    }

    private void setHideAmount(boolean isShow) {
        exchangeFragment.hideAmount(isShow);
        fiatFragment.hideAmount(isShow);
    }


    @OnClick(R.id.tv_asset_charge)
    public void startCharge() {
        if (MyApplication.getApp().isLogin()) {
            ChooseCoinActivity.actionStart(getActivity(), ChooseCoinActivity.TYPE_CHARGE);
        } else {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
        }
    }

    @OnClick(R.id.tv_asset_withdraw)
    public void startWithdraw() {
        if (MyApplication.getApp().isLogin()) {
            ChooseCoinActivity.actionStart(getActivity(), ChooseCoinActivity.TYPE_WITHDRAW);
        } else {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
        }
    }

    @OnClick(R.id.tv_asset_transfer)
    public void startTransfer() {
        if (MyApplication.getApp().isLogin()) {
            AssetTransferActivity.actionStart(getActivity(), AssetTransferActivity.START_TRANSFER_TYPE_EXCHANGE, "BTC");
        } else {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getTotalAssets();
        getData(mViewPager.getCurrentItem());
    }

    private void getTotalAssets() {
        RemoteDataSource.getInstance().getTotalAssets(getmActivity().getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                List<Double> list = (List<Double>) obj;
                sumUsd=list.get(0);
                sumCny=list.get(1);
                if("*****".equals(tvCnyAmount.getText())){
                    return;
                }
                tvAmount.setText(WonderfulStringUtils.getLongFloatString(sumUsd, 8));
                tvCnyAmount.setText("≈"+WonderfulMathUtils.getRundNumber(sumCny, 2, null) + "CNY");
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                LogUtil.i(toastMessage);
            }
        });
    }
}
