package top.biduo.exchange.ui.ieo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.CircleImageView;
import top.biduo.exchange.entity.IeoOrderRecordBean;

public class IeoOrderDetailActivity extends BaseActivity {
    private IeoOrderRecordBean bean;
    @BindView(R.id.iv_pic_circle)
    CircleImageView iv_pic_circle;
    @BindView(R.id.tv_ieo_sale_coin)
    TextView tv_ieo_sale_coin;
    @BindView(R.id.tv_ieo_name)
    TextView tv_ieo_name;
    @BindView(R.id.tv_ieo_order_status)
    TextView tv_ieo_order_status;
    @BindView(R.id.tv_ieo_count)
    TextView tv_ieo_count;
    @BindView(R.id.tv_ieo_raise_coin)
    TextView tv_ieo_raise_coin;
    @BindView(R.id.tv_ieo_time)
    TextView tv_ieo_time;
    @BindView(R.id.tv_ieo_order_count)
    TextView tv_ieo_order_count;
    @BindView(R.id.tv_ieo_count_pay)
    TextView tv_ieo_count_pay;
    @BindView(R.id.tv_ieo_order_pay_time)
    TextView tv_ieo_order_pay_time;
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_ieo_order_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bean = (IeoOrderRecordBean) getIntent().getSerializableExtra("data");
        tv_ieo_sale_coin.setText(bean.getSaleCoin());
        tv_ieo_name.setText(bean.getIeoName());
        String [] orderStatus={ getResources().getString(R.string.ieo_fail), getResources().getString(R.string.ieo_success)};
        tv_ieo_order_status.setText(orderStatus[Integer.parseInt(bean.getStatus())]);
        tv_ieo_count.setText(bean.getSaleAmount()+bean.getSaleCoin());
        tv_ieo_raise_coin.setText(bean.getRaiseCoin());
        tv_ieo_time.setText(bean.getStartTime()+"-"+bean.getEndTime());
        tv_ieo_order_count.setText(bean.getReceiveAmount()+bean.getSaleCoin());
        tv_ieo_count_pay.setText(bean.getPayAmount()+bean.getRaiseCoin());
        tv_ieo_order_pay_time.setText(bean.getExpectTime());
        Glide.with(MyApplication.getApp()).load(bean.getPicView()).into(iv_pic_circle);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    public static void actionStart(Activity activity, IeoOrderRecordBean ieoOrderRecordBean) {
        Intent intent = new Intent(activity, IeoOrderDetailActivity.class);
        intent.putExtra("data", ieoOrderRecordBean);
        activity.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

}
