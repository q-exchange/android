package top.biduo.exchange.ui.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import top.biduo.exchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseDialogFragment;
import top.biduo.exchange.utils.WonderfulCommonUtils;
import top.biduo.exchange.utils.WonderfulDpPxUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

/**
 * Created by Administrator on 2018/3/14.
 */

public class BBConfirmDialogFragment extends BaseDialogFragment {
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.tvCancle)
    TextView tvCancle;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    Unbinder unbinder;
    @BindView(R.id.ll_touch_price)
    LinearLayout ll_touch_price;
    private String price;
    private String amount;
    private String total;
    private String type;

    public static BBConfirmDialogFragment getInstance(String price, String amount, String total, String direction,String exchangeType,String touchPrice) {
        BBConfirmDialogFragment fragment = new BBConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("price", price);
        bundle.putString("amount", amount);
        bundle.putString("total", total);
        bundle.putString("direction", direction);
        bundle.putString("exchangeType",exchangeType);
        bundle.putString("touchPrice",touchPrice);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_exchange_bb;
    }

    @Override
    protected void initLayout() {
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        // 一不小心进入深坑  满身荆棘的爬出坑  佛曰： 别学Android了，适配能整死你，看那个学Android的已经上吊了！
        //window.setLayout(MyApplication.getApp().getmWidth(), WonderfulDpPxUtils.dip2px(getActivity(), 310));
//        window.setLayout(-1,-2);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                int height = 0;
                if (ImmersionBar.hasNavigationBar(getActivity()))
                    height = WonderfulCommonUtils.getStatusBarHeight(getActivity());
                window.setLayout(llContent.getWidth(), llContent.getHeight() + WonderfulDpPxUtils.dip2px(getActivity(), height));
            }
        });
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void initView() {
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((OperateCallback) getTargetFragment()).confirm();
                } catch (Exception e) {
                    if (callback != null) {
                        callback.confirm();
                    }
                }
                dismiss();
            }
        });
    }

    @Override
    protected void fillWidget() {
        tvPrice.setText(getArguments().getString("price"));
        tvAmount.setText(getArguments().getString("amount"));
        tvTotal.setText(getArguments().getString("total"));
        if (getArguments().getString("direction").equals("BUY")) {
            tvTitle.setText(getResources().getString(R.string.dialog_two_title_buy));
        }
        else {
            tvTitle.setText(getResources().getString(R.string.dialog_two_title_sell));
        }
        if(getArguments().getString("exchangeType").equals("CHECK_FULL_STOP")){
            ll_touch_price.setVisibility(View.VISIBLE);
            tvType.setText(getArguments().getString("touchPrice"));
        }else{
            ll_touch_price.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData() {

    }


    public interface OperateCallback {
        void confirm();
    }

}
