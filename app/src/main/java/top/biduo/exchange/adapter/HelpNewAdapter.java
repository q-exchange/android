package top.biduo.exchange.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.base.LinAdapter;
import top.biduo.exchange.entity.HelpBean;
import top.biduo.exchange.entity.Message;

/**
 * Created by Administrator on 2018/7/3.
 */
public class HelpNewAdapter extends LinAdapter<HelpBean> {
    private List<HelpBean> beanss;
    /**
     * LinAdapter通用的构造方法
     *
     * @param context 传入的上下文
     * @param beans   要显示的数据源封装好的列表
     */
    public HelpNewAdapter(Activity context, List<HelpBean> beans) {
        super(context, beans);
        beanss=beans;
    }

    @Override
    protected View LgetView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_gonggao, parent, false);
        }
        HelpBean bean = beanss.get(position);

        TextView text_biaoti=ViewHolders.get(convertView,R.id.text_biaoti);
        if (bean.getTitle().length()>15){
            String str;
            str=bean.getTitle();
            str=str.substring(0,15);
            text_biaoti.setText(str+"...");
        }else {
            text_biaoti.setText(bean.getTitle());
        }


        TextView text_time=ViewHolders.get(convertView,R.id.text_time);
        text_time.setText(bean.getCreateTime());
        ImageView img_ding=ViewHolders.get(convertView,R.id.img_ding);
        if ("0".equals(bean.getIsTop())){
            img_ding.setVisibility(View.VISIBLE);
        }else {
            img_ding.setVisibility(View.GONE);
        }


        return convertView;
    }
}
