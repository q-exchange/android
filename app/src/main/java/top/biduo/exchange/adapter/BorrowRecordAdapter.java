package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.entity.Address;
import top.biduo.exchange.entity.BorrowRecordBean;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;


public class BorrowRecordAdapter extends BaseQuickAdapter<BorrowRecordBean.ContentBean, BaseViewHolder> {
    public BorrowRecordAdapter(int layoutResId, @Nullable List<BorrowRecordBean.ContentBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BorrowRecordBean.ContentBean item) {
        helper.setText(R.id.tv_borrow_record_symbol, item.getLeverCoin().getSymbol())
                .setText(R.id.tv_borrow_record_coin, item.getCoin().getUnit())
                .setText(R.id.tv_borrow_record_amount, item.getLoanBalance() + "")
                .setText(R.id.tv_borrow_record_time, item.getCreateTime())
                .setText(R.id.tv_borrow_record_rate, item.getInterestRate()+"")
                .setText(R.id.tv_borrow_record_interest, WonderfulMathUtils.getRundNumber(item.getAccumulative() ,8,null))
                .setText(R.id.tv_borrow_record_amount_to_give_back, item.getAmount() + "")
                .addOnClickListener(R.id.tv_borrow_record_state);
        helper.setText(R.id.tv_borrow_record_state, getStatusText(item.getRepayment()));
        helper.getView(R.id.tv_borrow_record_state).setSelected(item.getRepayment() == 0);
    }

    private String getStatusText(int status){
        String[] statusText = {mContext.getResources().getString(R.string.revert_coin), mContext.getResources().getString(R.string.already_give_back_coin)};
        return  statusText[status];
    }

}
