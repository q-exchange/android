package top.biduo.exchange.ui.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

import butterknife.BindView;
import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseDialogFragment;
import top.biduo.exchange.utils.WonderfulDpPxUtils;

/**
 * Created by Administrator on 2018/3/9.
 */

public class BuyTypeDialogFragment extends BaseDialogFragment {
    @BindView(R.id.llMenu0)
    LinearLayout llMenu0;
    @BindView(R.id.llMenu1)
    LinearLayout llMenu1;
    @BindView(R.id.line_quxiao)
    LinearLayout line_quxiao;
    private static Context context1;
    private static OperateCallback callback;

    public static BuyTypeDialogFragment getInstance(Context context, OperateCallback operateCallback) {
        BuyTypeDialogFragment fragment = new BuyTypeDialogFragment();
        context1 = context;
        callback = operateCallback;
        return fragment;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_buy_type;
    }

    @Override
    protected void initLayout() {
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        getDialog().getWindow().setLayout(-1,-2);
    }

    @Override
    protected void initView() {
        llMenu0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.selectType(0);
                dismiss();
            }
        });
        llMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.selectType(1);
                dismiss();
            }
        });
        line_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    public interface OperateCallback {
        void selectType(int position);
    }
}
