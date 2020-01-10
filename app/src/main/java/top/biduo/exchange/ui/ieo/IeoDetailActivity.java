package top.biduo.exchange.ui.ieo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.IeoAdapter;
import top.biduo.exchange.adapter.TextWatcher;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.CircleImageView;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.IeoBean;
import top.biduo.exchange.utils.DateUtils;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import static top.biduo.exchange.utils.WonderfulToastUtils.getStringArray;

public class IeoDetailActivity extends BaseActivity {
    @BindView(R.id.iv_ieo_pic_circle)
    CircleImageView iv_ieo_pic_circle;
    @BindView(R.id.tv_ieo_name)
    TextView tv_ieo_name;
    @BindView(R.id.tv_ieo_des)
    TextView tv_ieo_des;
    @BindView(R.id.tv_ieo_statu)
    TextView tv_ieo_statu;
    @BindView(R.id.iv_ieo_pic)
    ImageView iv_ieo_pic;
    @BindView(R.id.tv_ieo_count)
    TextView tv_ieo_count;
    @BindView(R.id.tv_ieo_time)
    TextView tv_ieo_time;
    @BindView(R.id.tv_ieo_coin)
    TextView tv_ieo_coin;
    @BindView(R.id.tv_ieo_release_time)
    TextView tv_ieo_release_time;
    @BindView(R.id.tv_ieo_rate)
    TextView tv_ieo_rate;
    @BindView(R.id.tab_ieo)
    TabLayout tab_ieo;
    @BindView(R.id.et_ieo_count_raise)
    EditText et_ieo_count_raise;
    @BindView(R.id.tv_ieo_sale_coin)
    TextView tv_ieo_sale_coin;
    @BindView(R.id.tv_ieo_sale_coin1)
    TextView tv_ieo_sale_coin1;
    @BindView(R.id.et_ieo_pass)
    EditText et_ieo_pass;
    @BindView(R.id.tv_ieo_detail_content)
    TextView tv_ieo_detail_content;
    @BindView(R.id.tv_ieo_count_sale)
    TextView tv_ieo_count_sale;
    @BindView(R.id.tv_ieo_raise_coin)
    TextView tv_ieo_raise_coin;
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    private int status;

    @OnClick(R.id.ibBack)
    public void onBackClick() {
        finish();
    }

    @BindView(R.id.tv_ieo_commit)
    TextView tv_ieo_commit;

    @OnClick(R.id.tv_ieo_commit)
    public void commitIeo() {
        if (status == 0) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.ieo_preheating));
            return;
        }
        if (status == 2) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.ieo_finish));
            return;
        }
        String amount = et_ieo_count_raise.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.ieo_count_input));
            return;
        }
        String pass = et_ieo_pass.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            WonderfulToastUtils.showToast(getString(R.string.paymentTip6));
            return;
        }
        RemoteDataSource.getInstance().takeOrderIeo(ieoBean.getId() + "", amount, pass, getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                WonderfulToastUtils.showToast((String) obj);
                finish();
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(IeoDetailActivity.this,code,toastMessage);
            }
        });
    }

    private IeoBean ieoBean;

    private String[] ieoStatus;
    private int[] ids={R.drawable.shape_ieo_list_statu_red,R.drawable.shape_ieo_list_statu_green,R.drawable.shape_ieo_list_statu_gray};
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_ieo_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ieoStatus=getStringArray(R.array.array_ieo_statu);
        ieoBean = (IeoBean) getIntent().getSerializableExtra("data");
        Glide.with(this).load(ieoBean.getPicView()).into(iv_ieo_pic_circle);
        Glide.with(this).load(ieoBean.getPic()).into(iv_ieo_pic);
        tv_ieo_name.setText(ieoBean.getSaleCoin());
        tv_ieo_des.setText(ieoBean.getIeoName());
        getStatusTextByTime(ieoBean);
        tv_ieo_count.setText(ieoBean.getSaleAmount() + ieoBean.getSaleCoin());
        tv_ieo_time.setText(ieoBean.getStartTime() + "-" + ieoBean.getEndTime());
        tv_ieo_coin.setText(ieoBean.getRaiseCoin());
        tv_ieo_release_time.setText(ieoBean.getExpectTime());
        tv_ieo_rate.setText("1" + ieoBean.getRaiseCoin() + "=" + ieoBean.getRatio() + ieoBean.getSaleCoin());
        tv_ieo_sale_coin.setText(ieoBean.getSaleCoin());
        tv_ieo_raise_coin.setText(ieoBean.getRaiseCoin());
        tv_ieo_detail_content.setText(ieoBean.getSellMode());
        tab_ieo.addTab(tab_ieo.newTab().setText(getString(R.string.ieo_sell_type)));
        tab_ieo.addTab(tab_ieo.newTab().setText(getString(R.string.ieo_detail)));
        setCommitButtonState();
        tab_ieo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tv_ieo_detail_content.setText(ieoBean.getSellMode());
                } else {
                    tv_ieo_detail_content.setText(ieoBean.getSellDetail());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });
        et_ieo_count_raise.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    tv_ieo_count_sale.setText("0");
                } else {
                    try {
                        Double count = Double.parseDouble(s.toString());
                        tv_ieo_count_sale.setText((count * ieoBean.getRatio()) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getStatusTextByTime(IeoBean item) {
        long startTime = DateUtils.getTimeMillis("", item.getStartTime());
        long endTime = DateUtils.getTimeMillis("", item.getEndTime());
        long now = System.currentTimeMillis();
        if (now < startTime) {
            tv_ieo_statu.setText(ieoStatus[0]);
            tv_ieo_statu.setBackgroundResource(ids[0]);
        } else if (now > startTime && now < endTime) {
            tv_ieo_statu.setText(ieoStatus[1]);
            tv_ieo_statu.setBackgroundResource(ids[1]);
        } else {
            tv_ieo_statu.setText(ieoStatus[2]);
            tv_ieo_statu.setBackgroundResource(ids[2]);
        }
    }

    private void setCommitButtonState() {
        status = IeoAdapter.getStatusByTime(ieoBean);
        String[] buttonTexts = {getResources().getString(R.string.ieo_status_preheating),getResources().getString(R.string.ieo_confirm), getResources().getString(R.string.ieo_status_finish)};
        tv_ieo_commit.setText(buttonTexts[status]);
        tv_ieo_commit.setSelected(status == 1);

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        RemoteDataSource.getInstance().wallet(getToken(), ieoBean.getRaiseCoin(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                Coin coin = (Coin) obj;
                tv_ieo_sale_coin1.setText(coin.getBalance() + ieoBean.getRaiseCoin());
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(IeoDetailActivity.this,code,toastMessage);
            }
        });
    }

    public static void actionStart(Activity activity, IeoBean ieoBean) {
        Intent intent = new Intent(activity, IeoDetailActivity.class);
        intent.putExtra("data", ieoBean);
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
