package top.biduo.exchange.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.entity.FiatAssetBean;
import top.biduo.exchange.entity.MarginAssetBean;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;


public class MarginAssetAdapter extends BaseQuickAdapter<MarginAssetBean, BaseViewHolder> {

    boolean hideZero = false;
    String searchKey = "";
    int itemHeight=-100;
    boolean hideAmount=false;
    public MarginAssetAdapter(@LayoutRes int layoutResId, @Nullable List<MarginAssetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MarginAssetBean item) {

        if(itemHeight==-100){
            itemHeight=  helper.getView(R.id.ll_asset_container).getLayoutParams().height;
        }
        boolean show = true;
        if (hideZero && item.getLeverWalletList().get(0).getBalance() == 0 && item.getLeverWalletList().get(1).getBalance() == 0) {
            //如果选择了隐藏余额为0的币种，且此币种余额为0，就隐藏此item
            show = false;
        } else if (!TextUtils.isEmpty(searchKey) && !item.getSymbol().contains(searchKey)) {
            //如果有筛选条件key，且此币种名不包含key，就隐藏此item
            show=false;
        }
        ViewGroup.LayoutParams lp = helper.getView(R.id.ll_asset_container).getLayoutParams();
        lp.height= show?itemHeight:0;
        helper.getView(R.id.ll_asset_container).setLayoutParams(lp);

        helper.setText(R.id.tv_asset_symbol, item.getSymbol())
                .setText(R.id.tv_coin_name1, item.getLeverWalletList().get(0).getCoin().getUnit())//基础币
                .setText(R.id.tv_coin_name0, item.getLeverWalletList().get(1).getCoin().getUnit())//交易币
                .setText(R.id.tv_coin_available1, hideAmount? WonderfulStringUtils.getString(R.string.text_hide_amount):WonderfulMathUtils.getRundNumber(Double.valueOf(new BigDecimal
                        (item.getLeverWalletList().get(0).getBalance()).toPlainString()), 8, null) + "")//基础币余额
                .setText(R.id.tv_coin_available0, hideAmount?WonderfulStringUtils.getString(R.string.text_hide_amount):WonderfulMathUtils.getRundNumber(Double.valueOf(new BigDecimal
                        (item.getLeverWalletList().get(1).getBalance()).toPlainString()), 8, null) + "")//交易币余额
                .setText(R.id.tv_coin_borrowed1, hideAmount?WonderfulStringUtils.getString(R.string.text_hide_amount):WonderfulMathUtils.getRundNumber(Double.valueOf(new BigDecimal
                        (item.getBaseLoanCount()).toPlainString()), 8, null) + "")//基础币已借
                .setText(R.id.tv_coin_borrowed0, hideAmount?WonderfulStringUtils.getString(R.string.text_hide_amount):WonderfulMathUtils.getRundNumber(Double.valueOf(new BigDecimal
                        (item.getCoinLoanCount()).toPlainString()), 8, null) + "")//交易币已借
                ;
    }

    public void setHideZero(boolean hide) {
        hideZero = hide;
        notifyDataSetChanged();
    }

    public void setSearchKey(String key) {
        searchKey = key;
        notifyDataSetChanged();
    }

    public void setHideAmount(boolean hideAmount) {
        this.hideAmount = hideAmount;
    }
}
