package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.BorrowRecordBean;
import top.biduo.exchange.entity.IeoOrderRecordBean;


public class IeoOrderRecordAdapter extends BaseQuickAdapter<IeoOrderRecordBean , BaseViewHolder> {
    public IeoOrderRecordAdapter(int layoutResId, @Nullable List<IeoOrderRecordBean > data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IeoOrderRecordBean item) {
        helper.setText(R.id.tv_ieo_coin, item.getSaleCoin())
                .setText(R.id.tv_ieo_name, item.getIeoName())
                .setText(R.id.tv_ieo_order_status, getOrderStatusText(Integer.parseInt(item.getStatus())));
        Glide.with(MyApplication.getApp()).load(item.getPicView()).into((ImageView) helper.getView(R.id.iv_pic_circle));
    }

    private String getOrderStatusText(int status){
        String [] orderStatus={mContext.getResources().getString(R.string.ieo_fail),mContext.getResources().getString(R.string.ieo_success)};
        return orderStatus[status];
    }


}
