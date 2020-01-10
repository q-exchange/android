package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.entity.CandyRecordBean;
import top.biduo.exchange.entity.ScoreRecordBean;


public class CandyRecordAdapter extends BaseQuickAdapter<CandyRecordBean, BaseViewHolder> {
    public CandyRecordAdapter(int layoutResId, @Nullable List<CandyRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CandyRecordBean item) {
        helper.setText(R.id.tv_score_record_type, item.getGiftName());
        helper.setText(R.id.tv_score_record_amount, item.getGiftAmount()+item.getGiftCoin());
        helper.setText(R.id.tv_score_record_time, item.getCreateTime());

    }

}

