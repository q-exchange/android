package top.biduo.exchange.ui.main.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import okhttp3.Request;
import top.biduo.exchange.R;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.ui.aboutus.AboutUsActivity;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.bind_account.BindAccountActivity;
import top.biduo.exchange.ui.common.ChooseCoinActivity;
import top.biduo.exchange.ui.credit.CreditInfoActivity;
import top.biduo.exchange.ui.credit.VideoCreditActivity;
import top.biduo.exchange.ui.dialog.CommonDialog;
import top.biduo.exchange.ui.dialog.CommonDialogFragment;
import top.biduo.exchange.ui.entrust.TrustListActivity;
import top.biduo.exchange.ui.ieo.IeoActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.main.MainContract;
import top.biduo.exchange.ui.my_order.MyOrderActivity;
import top.biduo.exchange.ui.my_promotion.PromotionActivity;
import top.biduo.exchange.ui.myinfo.MyInfoActivity;
import top.biduo.exchange.ui.recharge.RechargeActivity;
import top.biduo.exchange.ui.score_record.CandyRecordActivity;
import top.biduo.exchange.ui.seller.SellerApplyActivity;
import top.biduo.exchange.ui.servicefee.ServiceFeeActivity;
import top.biduo.exchange.ui.setting.FeeSettingActivity;
import top.biduo.exchange.ui.setting.HelpActivity;
import top.biduo.exchange.ui.setting.NoticeActivity;
import top.biduo.exchange.ui.setting.SettingActivity;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.SafeSetting;
import top.biduo.exchange.entity.User;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.customview.intercept.WonderfulScrollView;
import top.biduo.exchange.utils.WonderfulCommonUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class UserFragment extends BaseTransFragment {
    public static final String TAG = UserFragment.class.getSimpleName();
    @BindView(R.id.llTop)
    LinearLayout llTop;
    @BindView(R.id.ll_account_center)
    LinearLayout ll_account_center;
    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    @BindView(R.id.tv_user_name)
    TextView tvNickName;
    @BindView(R.id.tv_user_id)
    TextView tv_user_id;
    @BindView(R.id.ll_credit)
    LinearLayout ll_credit;


    Unbinder unbinder;
    private SafeSetting safeSetting;
    private PopupWindow loadingPopup;
    private AlertDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    /**
     * 初始化加载dialog
     */
    private void initLoadingPopup() {
        View loadingView = getLayoutInflater().inflate(R.layout.pop_loading, null);
        loadingPopup = new PopupWindow(loadingView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingPopup.setFocusable(true);
        loadingPopup.setClippingEnabled(false);
        loadingPopup.setBackgroundDrawable(new ColorDrawable());
    }

    /**
     * 显示加载框
     */
    @Override
    public void displayLoadingPopup() {
        loadingPopup.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideLoadingPopup() {
        if (loadingPopup != null) {
            loadingPopup.dismiss();
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initLoadingPopup();
        llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyApplication.getApp().isLogin()) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
                }
            }
        });

        ll_account_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginOrCenter();
            }
        });
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.actionStart(getActivity());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoadingPopup();

    }

    @Override
    public void onStart() {
        super.onStart();
        hideLoadingPopup();
    }


    private void toLoginOrCenter() {
        if (MyApplication.getApp().isLogin()) {
            MyInfoActivity.actionStart(getActivity());
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
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
    public void onResume() {
        super.onResume();
        refreshLoginStatus();
    }

    public void refreshLoginStatus() {
        if (MyApplication.getApp().isLogin()) {
            loginingViewText();
        } else {
            notLoginViewText();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LoginActivity.RETURN_LOGIN:
                if (resultCode == Activity.RESULT_OK && getUserVisibleHint() && MyApplication.getApp().isLogin()) {
                    loginingViewText();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    notLoginViewText();
                }
                break;
            default:
        }
    }

    private void notLoginViewText() {
        tvNickName.setText(getResources().getString(R.string.not_logged_in));
        tv_user_id.setText(getResources().getString(R.string.welcome_to));
        ll_credit.setVisibility(View.GONE);

    }

    private void loginingViewText() {
        getSafeSetting();
        User user = MyApplication.getApp().getCurrentUser();
        tvNickName.setText("Hi," + WonderfulStringUtils.getHideString(user.getMobile()));
        tv_user_id.setText("UID:" + user.getId());

    }

    private void getSafeSetting() {
        RemoteDataSource.getInstance().safeSetting(getmActivity().getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                if (obj == null) {
                    return;
                }
                safeSetting = (SafeSetting) obj;
                MyApplication.realVerified = safeSetting.getRealVerified();
                ll_credit.setVisibility(safeSetting.getKycStatus()!=4?View.VISIBLE:View.GONE);

            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                if (code == 4000) {
                    MyApplication.getApp().setCurrentUser(null);
                    SharedPreferenceInstance.getInstance().saveaToken("");
                    SharedPreferenceInstance.getInstance().saveTOKEN("");
                    notLoginViewText();
                }
            }
        });
    }

    private void accountClick() {
        if (safeSetting == null) {
            return;
        }
        hideLoadingPopup();
        if (safeSetting.getRealVerified() == 1 && safeSetting.getFundsVerified() == 1) {
            BindAccountActivity.actionStart(getmActivity());
        } else {
            WonderfulToastUtils.showToast(getResources().getString(R.string.password_realname));
        }
    }


    @Override
    public String getmTag() {
        return TAG;
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

    @OnClick(R.id.ll_credit)
    public void startCredit() {
        if (MyApplication.getApp().isLogin()) {
            if (safeSetting == null) {
                return;
            }
            if (safeSetting.getRealVerified() == 0) {
                if (safeSetting.getRealAuditing() == 1) {//审核中
                    WonderfulToastUtils.showToast(getResources().getString(R.string.creditting));
                } else {
                    if (safeSetting.getRealNameRejectReason() != null) {//失败
                        CreditInfoActivity.actionStart(getActivity(), CreditInfoActivity.AUDITING_FILED, safeSetting.getRealNameRejectReason());
                    } else {//未认证
                        CreditInfoActivity.actionStart(getActivity(), CreditInfoActivity.UNAUDITING, safeSetting.getRealNameRejectReason());
                    }
                }
            } else {
                //身份证已通过
                int kycStatu = safeSetting.getKycStatus();
                switch (kycStatu) {
                    //0-未实名,5-待实名审核，2-实名审核失败、1-视频审核,6-待视频审核 ，3-视频审核失败,4-实名成功
                    case 1:
                        //实名通过，进行视频认证
                        VideoCreditActivity.actionStart(getActivity(), "");
                        break;
                    case 3:
                        //视频认证失败，进行视频认证，显示失败原因
                        VideoCreditActivity.actionStart(getActivity(), safeSetting.getRealNameRejectReason());
                        break;
                    case 4:
                        WonderfulToastUtils.showToast(getString(R.string.verification));
                        break;
                    case 6:
                        WonderfulToastUtils.showToast(getString(R.string.video_credit_auditing));
                        break;
                    default:
                }
            }
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.llPromotion)
    public void startPromotion() {
        if (MyApplication.getApp().isLogin()) {
            PromotionActivity.actionStart(getActivity());
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.llOrder)
    public void startOrder() {
        if (MyApplication.getApp().isLogin()) {
            TrustListActivity.show(getActivity(),false);
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.ll_about_us)
    public void startAbout() {
        AboutUsActivity.actionStart(getActivity());
    }

    @OnClick(R.id.llCharge)
    public void startCharge() {
        if (MyApplication.getApp().isLogin()) {
            ChooseCoinActivity.actionStart(getActivity(), ChooseCoinActivity.TYPE_CHARGE);
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.llWithdraw)
    public void startWithdraw() {
        if (MyApplication.getApp().isLogin()) {
            ChooseCoinActivity.actionStart(getActivity(), ChooseCoinActivity.TYPE_WITHDRAW);
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }


    @OnClick(R.id.llTransfer)
    public void startTransfer() {
        if (MyApplication.getApp().isLogin()) {
            AssetTransferActivity.actionStart(getActivity(),AssetTransferActivity.START_TRANSFER_TYPE_EXCHANGE,"BTC");
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.ll_help_center)
    public void startHelpCenter(){
        HelpActivity.actionStart(getActivity());
    }

    @OnClick(R.id.llFee)
    public void startFeeSetting(){
        if (MyApplication.getApp().isLogin()) {
            startActivity(new Intent(getActivity(),FeeSettingActivity.class));
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }

    @OnClick(R.id.ll_seller_credit)
    public void startSellerCredit(){
        if (MyApplication.getApp().isLogin()) {
            if (MyApplication.realVerified == 1) {
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
                                if (certifiedBusinessStatus == 5) {
                                    WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_auditing));
                                } else if (certifiedBusinessStatus == 6) {
                                    WonderfulToastUtils.showToast(getResources().getString(R.string.refund_deposit_failed));
                                } else {
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
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
        }
    }




}
