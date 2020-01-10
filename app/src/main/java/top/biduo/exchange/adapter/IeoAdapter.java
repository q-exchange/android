package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.Address;
import top.biduo.exchange.entity.IeoBean;
import top.biduo.exchange.utils.DateUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

/**
 * Created by Administrator on 2018/3/8.
 */

public class IeoAdapter extends BaseQuickAdapter<IeoBean, BaseViewHolder> {

    private String[] ieoStatus;
    private int[] ids={R.drawable.shape_ieo_list_statu_red,R.drawable.shape_ieo_list_statu_green,R.drawable.shape_ieo_list_statu_gray};

    public IeoAdapter(int layoutResId, @Nullable List<IeoBean> data) {
        super(layoutResId, data);
        ieoStatus = WonderfulToastUtils.getStringArray(R.array.array_ieo_statu);
    }

    @Override
    protected void convert(BaseViewHolder helper, IeoBean item) {
        helper.setText(R.id.tv_ieo_name, item.getSaleCoin());
        helper.setText(R.id.tv_ieo_des, item.getIeoName());
        helper.setText(R.id.tv_ieo_count, item.getSaleAmount() + item.getSaleCoin());
        helper.setText(R.id.tv_ieo_time, item.getStartTime() + "-" + item.getEndTime());
        helper.setText(R.id.tv_ieo_coin, item.getRaiseCoin());
        Glide.with(MyApplication.getApp()).load(item.getPicView()).into((ImageView) helper.getView(R.id.iv_ieo_pic_circle));
        Glide.with(MyApplication.getApp()).load(item.getPic()).into((ImageView) helper.getView(R.id.iv_ieo_pic));
        ((TextView) helper.getView(R.id.tv_ieo_statu)).setBackgroundResource(getStatusColorByTime(item));
        helper.setText(R.id.tv_ieo_statu, getStatusTextByTime(item));
    }

    public String getStatusTextByTime(IeoBean item) {
        long startTime = DateUtils.getTimeMillis("", item.getStartTime());
        long endTime = DateUtils.getTimeMillis("", item.getEndTime());
        long now = System.currentTimeMillis();
        if (now < startTime) {
            return ieoStatus[0];
        } else if (now > startTime && now < endTime) {
            return ieoStatus[1];
        } else {
            return ieoStatus[2];
        }
    }

    public int getStatusColorByTime(IeoBean item) {
        long startTime = DateUtils.getTimeMillis("", item.getStartTime());
        long endTime = DateUtils.getTimeMillis("", item.getEndTime());
        long now = System.currentTimeMillis();
        if (now < startTime) {
            return ids[0];
        } else if (now > startTime && now < endTime) {
            return ids[1];
        } else {
            return ids[2];
        }
    }


    public static int getStatusByTime(IeoBean item) {
        long startTime = DateUtils.getTimeMillis("", item.getStartTime());
        long endTime = DateUtils.getTimeMillis("", item.getEndTime());
        long now = System.currentTimeMillis();
        if (now < startTime) {
            return 0;
        } else if (now > startTime && now < endTime) {
            return 1;
        } else {
            return 2;
        }
    }

}
