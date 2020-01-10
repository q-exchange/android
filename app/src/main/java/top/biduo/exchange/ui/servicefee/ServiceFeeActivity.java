package top.biduo.exchange.ui.servicefee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.FeeBean;
import top.biduo.exchange.entity.User;
import top.biduo.exchange.entity.WithdrawLimitBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;


public class ServiceFeeActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tv_fee_level)
    TextView tv_fee_level;
    @BindView(R.id.tv_fee0)
    TextView tv_fee0;
    @BindView(R.id.tv_fee1)
    TextView tv_fee1;
    @BindView(R.id.tv_fee2)
    TextView tv_fee2;
    @BindView(R.id.tv_fee3)
    TextView tv_fee3;
    @BindView(R.id.tv_fee4)
    TextView tv_fee4;
    @BindView(R.id.tb_fee)
    TabLayout mTabLayout;
    @BindView(R.id.vp_fee)
    ViewPager mViewPager;
    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }
    private List<String> lists = new ArrayList<>();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_service_fee;
    }


    public static void actionStart(Context context, List<String> list) {
        Intent intent = new Intent(context, ServiceFeeActivity.class);
        intent.putStringArrayListExtra("list", (ArrayList<String>) list);
        context.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        RemoteDataSource.getInstance().getServiceFeeAll(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                List<FeeBean> feeBeans = new Gson().fromJson((String) obj, new TypeToken<List<FeeBean>>() {
                }.getType());
                setViewPagerData(feeBeans);
                setUserFeeData(feeBeans);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(ServiceFeeActivity.this,code,toastMessage);
            }
        });

       /* 此接口返回的是已经消耗的提币数量和次数，暂时不用
       RemoteDataSource.getInstance().getUserWithdrawLimit(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
               WithdrawLimitBean limitBean = new Gson().fromJson((String) obj,WithdrawLimitBean.class);
                tv_fee3.setText(amount);
                tv_fee4.setText(count);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulToastUtils.showToast(toastMessage);
            }
        });*/

    }

    private void setUserFeeData(List<FeeBean> feeBeans) {
        User user = MyApplication.getApp().getCurrentUser();
        tv_fee_level.setText("LV" + user.getMemberGradeId());
        for (FeeBean bean : feeBeans) {
            if(user.getMemberGradeId().equals(bean.getId())){
                tv_fee0.setText(WonderfulMathUtils.getRundNumber(bean.getExchangeFeeRate(),6,null));
                tv_fee1.setText(WonderfulMathUtils.getRundNumber(bean.getExchangeFeeRate(),6,null));
                tv_fee2.setText(WonderfulMathUtils.getRundNumber(bean.getExchangeFeeRate(),6,null));
                tv_fee3.setText(bean.getWithdrawCoinAmount());
                tv_fee4.setText(bean.getDayWithdrawCount());
            }
        }
    }

    private void setViewPagerData(List<FeeBean> feeBeans) {
        mViewPager.setAdapter(new MyPagerAdapter(feeBeans, this));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public class MyPagerAdapter extends android.support.v4.view.PagerAdapter {
        private List<FeeBean> feeBeans;
        private Context context;

        public MyPagerAdapter(List<FeeBean> feeBeans, Context context) {
            this.feeBeans = feeBeans;
            this.context = context;
        }

        @Override
        public int getCount() {
            return feeBeans.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.layout_fee, null);
            FeeBean feeBean = feeBeans.get(position);
            ((TextView) view.findViewById(R.id.tv_fee_level_title)).setText("LV" + feeBean.getId() + getResources().getString(R.string.level_privilege));
            ((TextView) view.findViewById(R.id.tv_fee_0)).setText(WonderfulMathUtils.getRundNumber(feeBean.getExchangeFeeRate(),6,null));
            ((TextView) view.findViewById(R.id.tv_fee_1)).setText(WonderfulMathUtils.getRundNumber(feeBean.getExchangeFeeRate(),6,null));
            ((TextView) view.findViewById(R.id.tv_fee_2)).setText(WonderfulMathUtils.getRundNumber(feeBean.getExchangeFeeRate(),6,null));
            ((TextView) view.findViewById(R.id.tv_fee_3)).setText(feeBean.getWithdrawCoinAmount());
            ((TextView) view.findViewById(R.id.tv_fee_4)).setText(feeBean.getDayWithdrawCount());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "LV" + feeBeans.get(position).getId();
        }
    }


}
