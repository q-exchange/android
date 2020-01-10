package top.biduo.exchange.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.zxing.common.StringUtils;
import top.biduo.exchange.R;
import top.biduo.exchange.entity.TransactionTypeBean;
import top.biduo.exchange.entity.WalletDetail;
import top.biduo.exchange.entity.WalletDetailNew;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class WalletDetailAdapter extends BaseQuickAdapter<WalletDetailNew.ContentBean, BaseViewHolder> {

    List<TransactionTypeBean> transactionTypeBeans;

    public WalletDetailAdapter(@LayoutRes int layoutResId, @Nullable List<WalletDetailNew.ContentBean> data, List<TransactionTypeBean> typeBeans) {
        super(layoutResId, data);
        transactionTypeBeans=typeBeans;
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletDetailNew.ContentBean contentBean) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(contentBean.getCreateTime());
            WonderfulLogUtils.logi("WalletDetailAdapter", "date  " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", date));
        String amount = WonderfulStringUtils.getLongFloatString(contentBean.getAmount(), 8);
        String fee = WonderfulStringUtils.getLongFloatString(contentBean.getFee(), 8);
        String realFee = WonderfulStringUtils.getLongFloatString( (contentBean.getFee()), 8);
        String type = "";
        for(TransactionTypeBean transactionTypeBean:transactionTypeBeans){
            if(contentBean.getType()==transactionTypeBean.getKey()){
                type=transactionTypeBean.getName();
            }
        }
        helper.setText(R.id.trade_time_value, time).setText(R.id.trade_amount_value, amount).setText(R.id.trust_symbol, contentBean.getSymbol()
        ).setText(R.id.fee_value, fee).setText(R.id.real_fee_value, realFee).setText(R.id.trade_type_value, type);
    }


}
