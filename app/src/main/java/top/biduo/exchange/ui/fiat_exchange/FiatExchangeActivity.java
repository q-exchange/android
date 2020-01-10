package top.biduo.exchange.ui.fiat_exchange;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.PagerAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.base.BaseFragment;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.CoinInfo;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.bind_account.BindAccountActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.main.trade.C2CFragment;
import top.biduo.exchange.ui.my_ads.AdsActivity;
import top.biduo.exchange.ui.my_order.MyOrderActivity;
import top.biduo.exchange.ui.releaseAd.ReleaseAdsActivity;
import top.biduo.exchange.ui.seller.SellerApplyActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulDpPxUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

public class FiatExchangeActivity extends BaseActivity {

    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.tb_fiat_title)
    TabLayout tb_fiat_title;
    @BindView(R.id.vp_fiat_list)
    ViewPager vp_fiat_list;
    @BindView(R.id.tb_fiat_coin_name)
    TabLayout tb_fiat_coin_name;

    @OnClick(R.id.iv_back)
    public void onBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.iv_filter_fiat_exchange)
    public void showFilterView() {

    }

    @OnClick(R.id.iv_filter_fiat_order)
    public void startFiatOrder() {
        if (MyApplication.getApp().isLogin()) {
            MyOrderActivity.actionStart(this, 0);
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }

    }

    @BindView(R.id.iv_menu_fiat_order)
    ImageView ivMenu;

    @OnClick(R.id.iv_menu_fiat_order)
    public void showFiatMenu(ImageView ivMenu) {
        if (MyApplication.getApp().isLogin()) {
            showBottomDialog();
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }


    private List<CoinInfo> coinInfos = new ArrayList<>();
    private PagerAdapter adapter;
    private ArrayList<String> tabs = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_fiat_exchange;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initTableLayout();

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getAllCoin();
    }

    private void getAllCoin() {
        RemoteDataSource.getInstance().all(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                coinInfos = (List<CoinInfo>) obj;
                setData();

            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

            }
        });
    }

    private void setData() {
        tabs.clear();
        fragments.clear();
        for (CoinInfo coinInfo : coinInfos) {
            tabs.add(coinInfo.getUnit());
            fragments.add(C2CFragment.getInstance(coinInfo));
        }
        if (fragments.size() > 4) {
            tb_fiat_coin_name.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tb_fiat_coin_name.setTabMode(TabLayout.MODE_FIXED);
        }
        if (adapter == null) {
            adapter = new PagerAdapter(getSupportFragmentManager(), fragments, tabs);
            vp_fiat_list.setAdapter(adapter);
            tb_fiat_coin_name.setupWithViewPager(vp_fiat_list);
            vp_fiat_list.setOffscreenPageLimit(fragments.size() - 1);
        } else {
            adapter.notifyDataSetChanged();
        }

    }


    public void selectType(int position) {
        switch (position) {
            case 0:
                showBuyPage();
                break;
            case 1:
                showSellPage();
                break;
            default:
        }
    }

    private void showSellPage() {
        for (BaseFragment fragment : fragments) {
            ((C2CFragment) fragment).setAdvertiseType("BUY");
        }
        if (fragments.size() != 0) {
            ((C2CFragment) fragments.get(vp_fiat_list.getCurrentItem())).loadData();
        }
    }

    private void showBuyPage() {
        for (BaseFragment fragment : fragments) {
            ((C2CFragment) fragment).setAdvertiseType("SELL");
        }
        if (fragments.size() != 0) {
            ((C2CFragment) fragments.get(vp_fiat_list.getCurrentItem())).loadData();
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

    private void initTableLayout() {
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.fiat_title)));
        setupTabViews(linkedList);
        tb_fiat_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectType(tab.getPosition());
                changeTabSelect(tab);   //Tab获取焦点
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);   //Tab失去焦点
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tb_fiat_title.getTabAt(0).select();
    }


    /**
     * 设置每个TabLayout的View
     */
    private void setupTabViews(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            tb_fiat_title.addTab(tb_fiat_title.newTab().setCustomView(getTabView(titles, i)));
        }
    }

    /**
     * 提供TabLayout的View
     * 根据index返回不同的View
     * 主意：默认选中的View要返回选中状态的样式
     */
    private View getTabView(List<String> titles, int index) {
        //自定义View布局
        View view = LayoutInflater.from(this).inflate(R.layout.textview_pop, null);
        TextView title = (TextView) view.findViewById(R.id.tv_text);
        title.setText(titles.get(index));
        if (index == 0) {
            view.setAlpha(1f);
            view.setScaleX(1.3f);
            view.setScaleY(1.3f);
        } else {
            view.setScaleX(1f);
            view.setScaleY(1f);
            view.setAlpha(0.5f);
        }
        return view;
    }


    /**
     * 改变TabLayout的View到选中状态
     * 使用属性动画改编Tab中View的状态
     */
    private void changeTabSelect(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(view, "scaleX", 1.0F, 1.3F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
                view.setAlpha(1f);
            }
        });
    }

    /**
     * 改变TabLayout的View到未选中状态
     */
    private void changeTabNormal(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(view, "scaleX", 1.3F, 1.0F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
                view.setAlpha(0.5f);
            }
        });
    }


    private void showBottomDialog() {
//        if (buyOrSellDialogFragment == null)
//            buyOrSellDialogFragment = new BuyOrSellDialogFragment();
//        buyOrSellDialogFragment.show(getActivity().getSupportFragmentManager(), "bottom");
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow_fabi, null);
        // ReleaseAdsActivity.actionStart(getActivity(), "SELL", null);
        LinearLayout my_buy = popupView.findViewById(R.id.my_buy);
        my_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserVerified(0);
            }
        });
        LinearLayout my_sell = popupView.findViewById(R.id.my_sell);
        my_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserVerified(1);
            }
        });
        popupView.findViewById(R.id.my_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserVerified(2);
            }
        });
        popupView.findViewById(R.id.my_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserVerified(3);
            }
        });
        popupView.findViewById(R.id.text_ad_shou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindAccountActivity.actionStart(FiatExchangeActivity.this);
            }
        });
        popupView.findViewById(R.id.ll_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetTransferActivity.actionStart(FiatExchangeActivity.this, AssetTransferActivity.START_TRANSFER_TYPE_FIAT, "USDT");
            }
        });
        PopupWindow window = new PopupWindow(popupView, WonderfulDpPxUtils.dip2px(FiatExchangeActivity.this, 120), WonderfulDpPxUtils.dip2px(FiatExchangeActivity.this, 244));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_bg));
        window.update();
        window.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int i = window.getWidth();
        int width = ivMenu.getWidth();
        window.showAsDropDown(ivMenu, -(i - width), 0);
    }

    public void checkUserVerified(final int position) {
        if (MyApplication.realVerified == 1) {
            if (position == 3) {
                MyOrderActivity.actionStart(this, 0);
                return;
            }
            if (position == 2) {
                AdsActivity.actionStart(this, MyApplication.getApp().getCurrentUser().getUsername(), MyApplication.getApp().getCurrentUser().getAvatar());
                return;
            }
            WonderfulOkhttpUtils.get().url(UrlFactory.getShangjia())
                    .addHeader("x-auth-token", SharedPreferenceInstance.getInstance().getTOKEN())
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.optInt("code");
                        if (code == 0) {
                            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                            int certifiedBusinessStatus = jsonObject1.optInt("certifiedBusinessStatus");
                            if (certifiedBusinessStatus == 2) {
                                switch (position) {
                                    case 0:
                                        ReleaseAdsActivity.actionStart(FiatExchangeActivity.this, "BUY", null);
                                        break;
                                    case 1:
                                        ReleaseAdsActivity.actionStart(FiatExchangeActivity.this, "SELL", null);
                                        break;
                                    default:
                                }
                            } else if (certifiedBusinessStatus == 5) {
                                WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_auditing));
                            } else if (certifiedBusinessStatus == 6) {
                                WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_failed));
                            } else {
                                JSONObject data = jsonObject.optJSONObject("data");
                                String reason = data.getString("detail");
                                SellerApplyActivity.actionStart(FiatExchangeActivity.this, certifiedBusinessStatus + "", reason);
                            }
                        } else {
                            WonderfulToastUtils.showToast(jsonObject.optString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            WonderfulToastUtils.showToast(getResources().getString(R.string.realname));
        }
    }

}
