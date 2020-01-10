package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.EntrustHistory;
import top.biduo.exchange.utils.WonderfulDateUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TrustAdapterNewHistory extends BaseQuickAdapter<EntrustHistory, BaseViewHolder> {
    boolean isCurrent;

    public TrustAdapterNewHistory(@Nullable List<EntrustHistory> data, boolean isCurrent) {
        super(R.layout.adapter_trust_new, data);
        this.isCurrent = isCurrent;
    }

    @Override
    protected void convert(final BaseViewHolder helper, EntrustHistory item) {
//        WonderfulLogUtils.logi("miao",item.getSymbol()+"//////****");
        //买入/卖出
        if ("BUY".equals(item.getDirection())) {
            helper.setText(R.id.trust_direction, mContext.getResources().getString(R.string.text_buy)).setTextColor(R.id.trust_direction,
                    ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen));
        } else {
            helper.setText(R.id.trust_direction, mContext.getResources().getString(R.string.text_sale)).setTextColor(R.id.trust_direction,
                    ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
        }

        //交易对
        helper.setText(R.id.trust_symbol, item.getSymbol());
        helper.setText(R.id.trust_count_key, "数量" + "(" + item.getCoinSymbol() + ")");
        //交易状态
        if (isCurrent) {
            helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.text_repeal));
            helper.getView(R.id.trust_state).setSelected(true);
//            helper.getView(R.id.trust_state).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    cancleLitener.onCancel(helper.getAdapterPosition());
//                }
//            });
            helper.addOnClickListener(R.id.trust_state);
        } else {
            //交易中 TRADING,
            //完成 COMPLETED,
            //取消 CANCELED,
            //超时 OVERTIMED,
            //等待触发 WAITING_TRIGGER
            switch (item.getStatus()) {
                case "COMPLETED":
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.traded_trade));
                    break;
                case "TRADING":
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.trading));
                    break;
                case "OVERTIMED":
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.traded_trade));
                    break;
                case "CANCELED":
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.undone));
                    break;
                case "WAITING_TRIGGER":
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.waiting_trigger));
                    break;
                default:
                    helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.undone));
            }
           /* if ("COMPLETED".equals(item.getStatus())) {
                helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.traded_trade));
            } else {
                helper.setText(R.id.trust_state, mContext.getResources().getString(R.string.undone));
            }*/
        }
        //委托时间
        String[] times = WonderfulDateUtils.getFormatTime(null, new Date(item.getTime())).split(" ");
        helper.setText(R.id.trust_time_value, times[0].substring(5, times[0].length()) + " " + times[1].substring(0, 5));
        //价格类型
        LinkedList<String> list = new LinkedList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.text_type)));

        if ("LIMIT_PRICE".equals(item.getType())) { // 限价
            helper.setText(R.id.trust_price_type_value, list.get(0));
        } else if("CHECK_FULL_STOP".equals(item.getType())){//止盈止损
            helper.setText(R.id.trust_price_type_value, list.get(2));
        }else{ // 市价
            helper.setText(R.id.trust_price_type_value, list.get(1));
        }
        //价格
        helper.setText(R.id.trust_price_key, mContext.getResources().getString(R.string.price) + "(" + item.getBaseSymbol() + ")");
        String format3 = new DecimalFormat("#0.00000000").format(item.getPrice());
        BigDecimal bg3 = new BigDecimal(format3);
        String v3 = bg3.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.trust_price_value, v3);
        // 数量
        helper.setText(R.id.trust_count_key, mContext.getResources().getString(R.string.amount) + "(" + item.getCoinSymbol() + ")");
        double amount = 0;
        if ("LIMIT_PRICE".equals(item.getType())) { // 限价
            amount = item.getAmount();
        } else { // 市价
            amount = item.getTradedAmount();
        }
        String format = new DecimalFormat("#0.00000000").format(amount);
        BigDecimal bg = new BigDecimal(format);
        String v = bg.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.trust_count_value, v);
        // 成交量
        String format1 = new DecimalFormat("#0.00000000").format(item.getTradedAmount());
        BigDecimal bg1 = new BigDecimal(format1);
        String v1 = bg1.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.trust_sold_value, v1);
        // 委托总额
        String format2 = new DecimalFormat("#0.00000000").format(item.getTurnover());
        BigDecimal bg2 = new BigDecimal(format2);
        String v2 = bg2.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.trust_total_value, v2);
        // 触发价
        String format4 = new DecimalFormat("#0.00000000").format(item.getTriggerPrice());
        BigDecimal bg4= new BigDecimal(format4);
        String v4 = bg4.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.trust_touch_price_value, v4);

    }

    OnclickListenerCancel cancleLitener;

    public void setOnCanleListener(OnclickListenerCancel cancleLitener) {
        this.cancleLitener = cancleLitener;
    }


    public interface OnclickListenerCancel {

        void onCancel(int position);
    }


}

