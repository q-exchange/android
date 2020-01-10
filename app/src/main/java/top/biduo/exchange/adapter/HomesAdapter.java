package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;



public class HomesAdapter extends BaseQuickAdapter<Currency, BaseViewHolder> {
    private int type;
    private boolean isLoad;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public HomesAdapter(@Nullable List<Currency> data, int type) {
        super(R.layout.adapter_layout_two, data);
        this.type = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, Currency item) {
        if (isLoad) {
            helper.setText(R.id.item_home_money, "≈" + WonderfulMathUtils.getRundNumber(item.getBaseUsdRate()*item.getClose() * MainActivity.rate,2,null)+" CNY");
        } else {
            helper.setText(R.id.item_home_money, "≈" + WonderfulMathUtils.getRundNumber(item.getBaseUsdRate()*item.getClose() * MainActivity.rate,2,null)+" CNY");
//            helper.setText(R.id.item_home_money, "$" + String.valueOf(item.getUsdRate()));
        }
        helper.setText(R.id.item_home_chg, (item.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(item.getChg() * 100, 2, "########0.") + "%");
//        helper.setTextColor(R.id.item_home_chg, item.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(),
//                R.color.white) : ContextCompat.getColor(MyApplication.getApp(), R.color.kred));
        helper.getView(R.id.item_home_chg).setEnabled(item.getChg() >= 0);
        if (type == 2) {
            helper.setText(R.id.item_home_symbol, item.getSymbol().split("/")[0]);
            helper.setText(R.id.item_home_symbolTwo, "/" + item.getSymbol().split("/")[1]);
        } else {
            helper.setText(R.id.item_home_symbol, item.getOtherCoin());
            helper.setText(R.id.item_home_symbolTwo, item.getSymbol().substring(item.getSymbol().indexOf("/"), item.getSymbol().length()));
        }
//        helper.setText(R.id.item_home_close, String.valueOf(item.getClose()));
        String format = new DecimalFormat("#0.00000000").format(item.getClose());
        BigDecimal bg = new BigDecimal(format);
        String v =  bg.setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.item_home_close,v);
        helper.setTextColor(R.id.item_home_close, item.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(),
                R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
        helper.setText(R.id.item_home_change, mContext.getResources().getString(R.string.text_24_change) + item.getVolume());
    }


}