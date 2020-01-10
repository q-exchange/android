package top.biduo.exchange.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

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


public class HomeAdapter extends BaseQuickAdapter<Currency, BaseViewHolder> {

    private boolean isLoad;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public HomeAdapter(@Nullable List<Currency> data) {
        super(R.layout.adapter_home_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Currency item) {
        helper.setText(R.id.item_home_chg, (item.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(item.getChg() * 100, 2, "########0.") + "%");
        helper.getView(R.id.item_home_chg).setEnabled(item.getChg() >= 0);
        String symbol=item.getSymbol();
        String[] coinNames=symbol.split("/");
        helper.setText(R.id.item_home_coin, coinNames[0]);
        helper.setText(R.id.item_home_coin_base, "/"+coinNames[1]);
        String format = new DecimalFormat("#0.00000000").format(item.getClose());
        BigDecimal bg = new BigDecimal(format);
        String v =  bg.setScale(8,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        helper.setText(R.id.item_home_close,v);

    }


}
