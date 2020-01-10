package top.biduo.exchange.ui.my_ads;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseDialogFragment;
import top.biduo.exchange.entity.Ads;
import top.biduo.exchange.utils.WonderfulDpPxUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/6.
 */

public class AdsDialogFragment extends BaseDialogFragment {
//    @BindView(R.id.tvLimit)
//    TextView tvLimit;
//    @BindView(R.id.tvTotal)
//    TextView tvTotal;
    @BindView(R.id.tvEdit)
LinearLayout tvEdit;
    @BindView(R.id.tvReleaseAgain)
    LinearLayout tvReleaseAgain;
    @BindView(R.id.tvDelete)
    LinearLayout tvDelete;
    @BindView(R.id.text_shangjia)
    TextView text_shangjia;
    @BindView(R.id.image)
    ImageView image;
//    @BindView(R.id.llContent)
//    LinearLayout llContent;
//    @BindView(R.id.ibClose)
//    ImageButton ibClose;
    private Ads ads;

    public static AdsDialogFragment getInstance(Ads ads) {
        AdsDialogFragment fragment = new AdsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ads", ads);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof OperateCallback)) {
            throw new RuntimeException("The Activity which fragment is located must implement the OperateCallback interface!");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_ads;
    }
    
    @Override
    protected void initLayout() {
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        window.setLayout(MyApplication.getApp().getmWidth(), WonderfulDpPxUtils.dip2px(getActivity(), 150));
    }

    @Override
    protected void initView() {
        ads = (Ads) getArguments().getSerializable("ads");
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OperateCallback) getActivity()).edit(ads);
                dismiss();
            }
        });
        tvReleaseAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseOrOffShelf(ads);
                dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OperateCallback) getActivity()).delete(ads);
                dismiss();
            }
        });
//        ibClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
    }

    private void releaseOrOffShelf(Ads ads) {
        if (this.ads.getStatus() == 0) ((OperateCallback) getActivity()).offShelf(ads);
        else ((OperateCallback) getActivity()).releaseAgain(ads);
    }

    @Override
    protected void fillWidget() {
        Drawable shangjia =getResources().getDrawable(R.drawable.icon_up_ads);
        Drawable xiajia =getResources().getDrawable(R.drawable.icon_down_ads);
        image.setBackground(ads.getStatus() == 0 ?xiajia:shangjia);
        text_shangjia.setText(ads.getStatus() == 0 ? getResources().getString(R.string.to_shelve) : getResources().getString(R.string.to_grounding));
        tvEdit.setVisibility(ads.getStatus() == 0 ? View.INVISIBLE : View.VISIBLE);
        tvDelete.setVisibility(ads.getStatus() == 0 ? View.INVISIBLE : View.VISIBLE);

    }

    @Override
    protected void loadData() {

    }

    public void setAds(Ads ads) {
        this.ads = ads;
        fillWidget();
    }

    interface OperateCallback {
        void edit(Ads ads);

        void releaseAgain(Ads ads);

        void offShelf(Ads ads);

        void delete(Ads ads);
    }
}
