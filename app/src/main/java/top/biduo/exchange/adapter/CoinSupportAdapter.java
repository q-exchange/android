package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;

public class CoinSupportAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CoinSupportAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_coin_name_support, item);
    }
}
