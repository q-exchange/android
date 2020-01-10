package top.biduo.exchange.ui.main;

import android.content.Context;

import top.biduo.exchange.base.BaseLazyFragment;
import top.biduo.exchange.entity.Currency;

/**
 * Created by Administrator on 2018/2/26.
 */

public abstract class MarketBaseFragment extends BaseLazyFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof MarketOperateCallback)) {
            throw new RuntimeException("The Activity which this fragment is located must implement the MarketOperateCallback interface!");
        }
    }


    public interface MarketOperateCallback {

        void itemClick(Currency currency );
    }

}
