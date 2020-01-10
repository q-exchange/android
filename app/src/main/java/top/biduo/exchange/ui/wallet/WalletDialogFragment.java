package top.biduo.exchange.ui.wallet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.biduo.exchange.R;
import top.biduo.exchange.ui.extract.ExtractActivity;
import top.biduo.exchange.ui.recharge.RechargeActivity;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseDialogFragment;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulDpPxUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class WalletDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_selection0)
    TextView tv_selection0;
    @BindView(R.id.tv_selection1)
    TextView tv_selection1;
    @BindView(R.id.tv_selection2)
    TextView tv_selection2;
    @BindView(R.id.tv_selection3)
    TextView tv_selection3;
    @BindView(R.id.line_0)
    View line_0;
    @BindView(R.id.line_1)
    View line_1;
    @BindView(R.id.line_2)
    View line_2;

    Unbinder unbinder;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    Unbinder unbinder1;
    OnItemSelectListener listener;
    private String text0, text1, text2;

    public static WalletDialogFragment getInstance(String text0, String text1, String text2) {
        WalletDialogFragment fragment = new WalletDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text0", text0);
        bundle.putString("text1", text1);
        bundle.putString("text2", text2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_wallet;
    }

    private int i = 0;

    @Override
    protected void initLayout() {
        text0 = getArguments().getString("text0");
        tv_selection0.setText(text0);
        if (TextUtils.isEmpty(text0)) {
            tv_selection0.setVisibility(View.GONE);
            line_0.setVisibility(View.GONE);
        }
        text1 = getArguments().getString("text1");
        tv_selection1.setText(text1);
        if (TextUtils.isEmpty(text1)) {
            tv_selection1.setVisibility(View.GONE);
            line_1.setVisibility(View.GONE);
        }
        text2 = getArguments().getString("text2");
        tv_selection2.setText(text2);
        if (TextUtils.isEmpty(text2)) {
            tv_selection2.setVisibility(View.GONE);
            line_2.setVisibility(View.GONE);
        }
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        if (TextUtils.isEmpty(text0)) {
            i = i + 51;
        }
        if (TextUtils.isEmpty(text1)) {
            i = i + 51;
        }
        if (TextUtils.isEmpty(text2)) {
            i = i + 51;
        }
        window.setLayout(MyApplication.getApp().getmWidth(), (WonderfulDpPxUtils.dip2px(getActivity(), (203 - i))) + getBottomStatusHeight(getActivity()));
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    @Override
    protected void initView() {
        tv_selection0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(0);
                dismiss();
            }
        });

        tv_selection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(1);
                dismiss();
            }
        });
        tv_selection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(2);
                dismiss();
            }
        });
        tv_selection3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
//        dismiss();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        listener = onItemSelectListener;
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }
}
