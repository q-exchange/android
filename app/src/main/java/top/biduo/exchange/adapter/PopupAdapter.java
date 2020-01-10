package top.biduo.exchange.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.biduo.exchange.R;


public class PopupAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    int itemWidth;
    public PopupAdapter(int layoutResId, @Nullable List<T> data, int width) {
        super(layoutResId, data);
        itemWidth=width;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

        if(item instanceof String){
            String str= (String) item;
            helper.setText(R.id.tv_popup_text,str);
        }
        if(itemWidth!=0){
            ViewGroup.LayoutParams lp = helper.getConvertView().getLayoutParams();
            lp.width=itemWidth;
            helper.getConvertView().setLayoutParams(lp);
        }
    }
}
