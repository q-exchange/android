package top.biduo.exchange.customview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.BottomSelectorAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseDialogFragment;
import top.biduo.exchange.utils.WonderfulDpPxUtils;

public class BottomSelectionFragment extends BaseDialogFragment {

    Unbinder unbinder;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.rc_bottom_selector)
    RecyclerView rc_bottom_selector;
    Unbinder unbinder1;
    OnItemSelectListener listener;
    ArrayList<String> list;

    public static BottomSelectionFragment getInstance(ArrayList<String> list) {
        BottomSelectionFragment fragment = new BottomSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_bottom_selector;
    }

    private int height = 0;

    @Override
    protected void initLayout() {
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        int maxDisplaySize = 8;
        int displaySize = list.size() > 8 ? 8 : list.size();
        height = 45 * (displaySize + 1);
//        window.setLayout(MyApplication.getApp().getmWidth(), (WonderfulDpPxUtils.dip2px(getActivity(), (height))) + getBottomStatusHeight(getActivity()));
        window.setLayout(-1,-2);
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
        list = (ArrayList<String>) getArguments().getSerializable("data");
        rc_bottom_selector.setLayoutManager(new LinearLayoutManager(getActivity()));
        BottomSelectorAdapter adapter = new BottomSelectorAdapter(R.layout.adapter_bottom_selector, list);
        rc_bottom_selector.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                listener.onItemSelected(position);
                dismiss();
            }
        });
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