package top.biduo.exchange.ui.main.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.entity.C2C;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.bind_account.BindAccountActivity;
import top.biduo.exchange.ui.dialog.BuyTypeDialogFragment;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.main.MainContract;
import top.biduo.exchange.ui.my_ads.AdsActivity;
import top.biduo.exchange.ui.my_order.MyOrderActivity;
import top.biduo.exchange.ui.releaseAd.ReleaseAdsActivity;
import top.biduo.exchange.ui.seller.SellerApplyActivity;
import top.biduo.exchange.adapter.PagerAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseFragment;
import top.biduo.exchange.base.BaseNestingTransFragment;
import top.biduo.exchange.entity.CoinInfo;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulDpPxUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;


public class BaseCoinFragment extends BaseNestingTransFragment implements MainContract.BaseCoinView, BuyTypeDialogFragment.OperateCallback {
    public static final String TAG = BaseCoinFragment.class.getSimpleName();

    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_type)
    TextView tv_type;
    Unbinder unbinder;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;


    private MainContract.BaseCoinPresenter presenter;
    private List<CoinInfo> coinInfos = new ArrayList<>();
    private PagerAdapter adapter;
    private ArrayList<String> tabs = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<CoinInfo> saveCoinInfos;
    private BuyTypeDialogFragment buyTypeDialogFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getApp().isLogin()) {
                    showBottomDialog();
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
                }
            }
        });
        if (savedInstanceState == null) {
            presenter.all();
        } else {
            saveCoinInfos = (List<CoinInfo>) savedInstanceState.getSerializable("coinInfos");
            recoveryFragments();
        }
    }

    public void checkUserVerified(final int position) {
        if (MyApplication.realVerified == 1) {
            if (position == 3) {
                MyOrderActivity.actionStart(getActivity(), 0);
                return;
            }
            if(position==2){
                AdsActivity.actionStart(getActivity(), MyApplication.getApp().getCurrentUser().getUsername(), MyApplication.getApp().getCurrentUser().getAvatar());
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
                                        ReleaseAdsActivity.actionStart(getActivity(), "BUY", null);
                                        break;
                                    case 1:
                                        ReleaseAdsActivity.actionStart(getActivity(), "SELL", null);
                                        break;
                                    default:
                                }
                            } else if(certifiedBusinessStatus == 5){
                                WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_auditing));
                            } else if(certifiedBusinessStatus == 6){
                                WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_failed));
                            }else {
                                JSONObject data = jsonObject.optJSONObject("data");
                                String reason = data.getString("detail");
                                SellerApplyActivity.actionStart(getActivity(), certifiedBusinessStatus + "", reason);
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

    private void showBottomDialog() {
//        if (buyOrSellDialogFragment == null)
//            buyOrSellDialogFragment = new BuyOrSellDialogFragment();
//        buyOrSellDialogFragment.show(getActivity().getSupportFragmentManager(), "bottom");
        View popupView = getmActivity().getLayoutInflater().inflate(R.layout.popupwindow_fabi, null);
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
                BindAccountActivity.actionStart(getActivity());
            }
        });
        popupView.findViewById(R.id.ll_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetTransferActivity.actionStart(getActivity(),AssetTransferActivity.START_TRANSFER_TYPE_FIAT,"USDT");
            }
        });
        PopupWindow window = new PopupWindow(popupView, WonderfulDpPxUtils.dip2px(getmActivity(), 120), WonderfulDpPxUtils.dip2px(getmActivity(), 244));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(getmActivity().getResources().getDrawable(R.drawable.my_bg));
        window.update();
        window.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int i = window.getWidth();
        int width = ivMenu.getWidth();
        window.showAsDropDown(ivMenu, -(i - width), 0);
    }

    private void showSellPage() {
        tv_type.setText(getString(R.string.to_sell));
        for (BaseFragment fragment : fragments) {
            ((C2CFragment) fragment).setAdvertiseType("BUY");
        }
        if (fragments.size() != 0) {
            ((C2CFragment) fragments.get(vp.getCurrentItem())).loadData();
        }
    }

    private void showBuyPage() {
        tv_type.setText(getString(R.string.to_buy));
        for (BaseFragment fragment : fragments) {
            ((C2CFragment) fragment).setAdvertiseType("SELL");
        }
        if (fragments.size() != 0) {
            ((C2CFragment) fragments.get(vp.getCurrentItem())).loadData();
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

    @Override
    public void setPresenter(MainContract.BaseCoinPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void allSuccess(List<CoinInfo> obj) {
        if (tabLayout == null) {
            return;
        }
        if (obj == null || obj.size() == 0) {
            return;
        }
        coinInfos.clear();
        coinInfos.addAll(obj);
        tabs.clear();
        fragments.clear();
        for (CoinInfo coinInfo : coinInfos) {
            tabs.add(coinInfo.getUnit());
            fragments.add(C2CFragment.getInstance(coinInfo));
        }
        if (fragments.size() > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        if (adapter == null) {
            adapter = new PagerAdapter(getChildFragmentManager(), fragments, tabs);
            vp.setAdapter(adapter);
            tabLayout.setupWithViewPager(vp);
            vp.setOffscreenPageLimit(fragments.size() - 1);
        } else {
            adapter.notifyDataSetChanged();
        }
        isNeedLoad = false;
    }

    @Override
    public void allFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("coinInfos", (Serializable) coinInfos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void addFragments() {
        //do  nothing
    }

    @Override
    protected void recoveryFragments() {
        if (saveCoinInfos == null || saveCoinInfos.size() == 0) {
            return;
        }
        coinInfos.clear();
        coinInfos.addAll(saveCoinInfos);
        tabs.clear();
        fragments.clear();
        for (int i = 0; i < coinInfos.size(); i++) {
            tabs.add(coinInfos.get(i).getUnit());
            fragments.add((C2CFragment) getChildFragmentManager().findFragmentByTag(makeFragmentName(vp.getId(), i)));
        }
        if (fragments.size() > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        if (adapter == null) {
            vp.setAdapter(adapter = new PagerAdapter(getChildFragmentManager(), fragments, tabs));
            tabLayout.setupWithViewPager(vp);
            vp.setOffscreenPageLimit(fragments.size() - 1);
        } else {
            adapter.notifyDataSetChanged();
        }
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

    @OnClick(R.id.ll_type)
    public void selectType() {
        if (buyTypeDialogFragment == null) {
            buyTypeDialogFragment = BuyTypeDialogFragment.getInstance(getActivity(), this);
        }
        buyTypeDialogFragment.show(getActivity().getSupportFragmentManager(), "buy_type");
    }
}
