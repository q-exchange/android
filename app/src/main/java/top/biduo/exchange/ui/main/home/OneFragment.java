package top.biduo.exchange.ui.main.home;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.customview.CircleImageView;
import top.biduo.exchange.customview.ZoomOutPageTransformer;
import top.biduo.exchange.ui.chatlist.ChatListActivity;
import top.biduo.exchange.ui.dialog.GestureDialogFragment;
import top.biduo.exchange.ui.fiat_exchange.FiatExchangeActivity;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.ui.main.MainContract;
import top.biduo.exchange.ui.main.presenter.CommonPresenter;
import top.biduo.exchange.ui.main.presenter.ICommonView;
import top.biduo.exchange.ui.kline.KlineActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.message_detail.MessageDetailActivity;
import top.biduo.exchange.ui.myinfo.MyInfoActivity;
import top.biduo.exchange.ui.setting.NoticeActivity;
import top.biduo.exchange.ui.setting.HelpActivity;
import top.biduo.exchange.adapter.BannerImageLoader;
import top.biduo.exchange.adapter.HomeAdapter;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.BannerEntity;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.entity.LunBoBean;
import top.biduo.exchange.entity.Message;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.ScreenUtils;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.view.BannerViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.biduo.exchange.app.Injection;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/1/7.
 */

public class OneFragment extends BaseTransFragment implements top.biduo.exchange.ui.main.MainContract.OneView, ICommonView {
    public static final String TAG = OneFragment.class.getSimpleName();
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.line_fabi)
    LinearLayout line_fabi;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_linear)
    LinearLayout main_linear;
    @BindView(R.id.line_help)
    LinearLayout line_help;
    @BindView(R.id.line_contract)
    LinearLayout line_contract;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView; // 涨幅榜
    @BindView(R.id.iv_user_info)
    CircleImageView iv_user_info;
    private HomeAdapter mHomeAdapter; // 首页适配器
    Unbinder unbinder;
    private List<String> imageUrls = new ArrayList<>();
    private List<LunBoBean> banners = new ArrayList<>();
    private List<Integer> localImageUrls = new ArrayList<Integer>() {{
        add(R.drawable.icon_banner);
    }};
    private List<Currency> currencies = new ArrayList<>();
    private List<Currency> currenciesTwo = new ArrayList<>();
    private MainContract.OnePresenter presenter;

    private CommonPresenter commonPresenter;
    private String sysAdvertiseLocation = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        /*if (MyApplication.getApp().isLogin() && !TextUtils.isEmpty(MyApplication.getApp().getCurrentUser().getAvatar())) {
            Glide.with(getActivity()).load(MyApplication.getApp().getCurrentUser().getAvatar()).into(iv_user_info);
        } else {
            Glide.with(getActivity()).load(R.mipmap.icon_default_header).into(iv_user_info);
        }*/
    }

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    private void dialogShow2() {
        GestureDialogFragment dialogFragment = new GestureDialogFragment();
        dialogFragment.setCallBack(new GestureDialogFragment.CallBack() {
            @Override
            public void callSure(boolean isCheck) {
                if (MyApplication.getApp().isLogin()) {
                    if (isCheck) {
                        SharedPreferenceInstance.getInstance().saveTishi(isCheck);
                    }
                    MyInfoActivity.actionStart(getActivity());
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
                }
            }
        });
        dialogFragment.show(getmActivity().getSupportFragmentManager(), "");

    }

    @OnClick(R.id.iv_user_info)
    public void showDrawerLayout() {
        ((MainActivity) getActivity()).getDlRoot(MainActivity.MENU_TYPE_USER).openDrawer(Gravity.LEFT);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new CommonPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
        line_fabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FiatExchangeActivity.class));
            }
        });
        line_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpActivity.actionStart(getmActivity());
            }
        });

        line_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WonderfulToastUtils.showToast(getString(R.string.not_yet_open));
            }
        });
        if (WonderfulStringUtils.isEmpty(SharedPreferenceInstance.getInstance().getLockPwd())) {
            if (SharedPreferenceInstance.getInstance().getTishi()) {
                return;
            }
            dialogShow2();
        }

    }

    @Override
    protected void obtainData() {
    }

    @Override
    protected void fillWidget() {
        initRvContent();

    }

    class MyAdapter extends PagerAdapter {
        private List<Currency> lists = new ArrayList<>();

        @Override
        public int getCount() {
            int size = currenciesTwo.size();
            if (size == 0) {
                return 0;
            }
            int i = size % 3;
            int a = size / 3;
            if (i > 0) {
                a = a + 1;
            }
            return a;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_home_viewpage, null, false);
            //第一组
            LinearLayout line_one = inflate.findViewById(R.id.line_one);
            TextView tvCurrencyName = inflate.findViewById(R.id.tvCurrencyName);
            ImageView ivCollect = inflate.findViewById(R.id.ivCollect);
            TextView tvClose = inflate.findViewById(R.id.tvClose);
            TextView tvAddPercent = inflate.findViewById(R.id.tvAddPercent);
            TextView tvVol = inflate.findViewById(R.id.tvVol);

            //第二组
            LinearLayout line_two = inflate.findViewById(R.id.line_two);
            TextView tvCurrencyName1 = inflate.findViewById(R.id.tvCurrencyName1);
            ImageView ivCollect1 = inflate.findViewById(R.id.ivCollect1);
            TextView tvClose1 = inflate.findViewById(R.id.tvClose1);
            TextView tvAddPercent1 = inflate.findViewById(R.id.tvAddPercent1);
            TextView tvVol1 = inflate.findViewById(R.id.tvVol1);
            //第三组
            LinearLayout line_three = inflate.findViewById(R.id.line_three);
            TextView tvCurrencyName2 = inflate.findViewById(R.id.tvCurrencyName2);
            ImageView ivCollect2 = inflate.findViewById(R.id.ivCollect2);
            TextView tvClose2 = inflate.findViewById(R.id.tvClose2);
            TextView tvAddPercent2 = inflate.findViewById(R.id.tvAddPercent2);
            TextView tvVol2 = inflate.findViewById(R.id.tvVol2);
            int star = position * 3;
            int end = (position + 1) * 3;
            lists.clear();
            for (int i = 0; i <= currenciesTwo.size(); i++) {
                if (i >= star && i < end && i < currenciesTwo.size()) {
                    lists.add(currenciesTwo.get(i));
                }
            }
            for (int i = 0; i < lists.size(); i++) {
                if (i == 0) {
                    final Currency currency = lists.get(i);
                    line_one.setVisibility(View.VISIBLE);
                    ivCollect.setImageResource(currency.isCollect() ? R.mipmap.icon_collect_yes : R.mipmap.icon_collect_no);
                    tvCurrencyName.setText(currency.getSymbol());
                    tvClose.setText(WonderfulMathUtils.getRundNumber(currency.getClose(), 2, null));
                    tvAddPercent.setText((currency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(currency.getChg() * 100, 2, "########0.") + "%");
                    tvVol.setText("≈" + WonderfulMathUtils.getRundNumber(currency.getClose() * currency.getBaseUsdRate() * MainActivity.rate, 2, null) + "CNY");
                    tvClose.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    tvAddPercent.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    line_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KlineActivity.actionStart(getActivity(), currency.getSymbol());
                        }
                    });
                }
                if (i == 1) {
                    final Currency currency = lists.get(i);
                    line_two.setVisibility(View.VISIBLE);
                    ivCollect1.setImageResource(currency.isCollect() ? R.mipmap.icon_collect_yes : R.mipmap.icon_collect_no);
                    tvCurrencyName1.setText(currency.getSymbol());
                    tvClose1.setText(WonderfulMathUtils.getRundNumber(currency.getClose(), 2, null));
                    tvAddPercent1.setText((currency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(currency.getChg() * 100, 2, "########0.") + "%");
                    tvVol1.setText("≈" + WonderfulMathUtils.getRundNumber(currency.getClose() * currency.getBaseUsdRate() * MainActivity.rate, 2, null) + "CNY");
                    tvClose1.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    tvAddPercent1.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    line_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KlineActivity.actionStart(getActivity(), currency.getSymbol());
                        }
                    });
                }
                if (i == 2) {
                    final Currency currency = lists.get(i);
                    line_three.setVisibility(View.VISIBLE);
                    ivCollect2.setImageResource(currency.isCollect() ? R.mipmap.icon_collect_yes : R.mipmap.icon_collect_no);
                    tvCurrencyName2.setText(currency.getSymbol());
                    tvClose2.setText(WonderfulMathUtils.getRundNumber(currency.getClose(), 2, null));
                    tvAddPercent2.setText((currency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(currency.getChg() * 100, 2, "########0.") + "%");
                    tvVol2.setText("≈" + WonderfulMathUtils.getRundNumber(currency.getClose() * currency.getBaseUsdRate() * MainActivity.rate, 2, null) + "CNY");
                    tvClose2.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    tvAddPercent2.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) : ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
                    line_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            KlineActivity.actionStart(getActivity(), currency.getSymbol());
                        }
                    });
                }
            }
            container.addView(inflate);
            return inflate;
        }

    }

    private void initRvContent() {
        // 涨幅榜的适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(currencies);
        View head = View.inflate(getActivity(), R.layout.adapter_home_head, null);
        mHomeAdapter.addHeaderView(head);
        mHomeAdapter.isFirstOnly(true);
        mHomeAdapter.setLoad(true);
        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KlineActivity.actionStart(getActivity(), OneFragment.this.mHomeAdapter.getData().get(position).getSymbol());
            }
        });
    }

    @Override
    protected void loadData() {
        notifyData();
        presenter.banners(sysAdvertiseLocation);
        getMessage();

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(getActivity(), llTitle);
            isSetTitle = true;
        }
    }

    @Override
    public void setPresenter(MainContract.OnePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPresenter(CommonPresenter presenter) {
        this.commonPresenter = presenter;
    }

    private MyAdapter adapter2;

    public void dataLoaded(List<Currency> currencies, List<Currency> tow) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
        this.currenciesTwo.clear();
        this.currenciesTwo.addAll(tow);
        adapter2 = new MyAdapter();
        viewPager.setAdapter(adapter2);
        int size = currenciesTwo.size();
        int i1 = size % 3;
        int pageCount = size / 3;
        if (i1 > 0) {
            pageCount++;
        }
        main_linear.removeAllViews();
        main_linear.setVisibility(pageCount > 1 ? View.VISIBLE : View.GONE);
        WonderfulLogUtils.logi("pageCount", "" + pageCount);
        for (int c = 0; c < pageCount; c++) {
            View view = new View(getActivity());
            view.setBackgroundResource(R.drawable.zhishiqi);
            if (c == 0) {
                view.setEnabled(true);
            } else {
                view.setEnabled(false);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35, 3);
            layoutParams.leftMargin = 10;
            main_linear.addView(view, layoutParams);
        }
        mHomeAdapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                main_linear.getChildAt(mNum).setEnabled(false);
                main_linear.getChildAt(position).setEnabled(true);
                mNum = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int mNum = 0;


    @Override
    public void deleteFail(Integer code, String toastMessage) {
        if (code == GlobalConstant.TOKEN_DISABLE1) {
            LoginActivity.actionStart(getActivity());
        } else {
            WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
        }
    }

    @Override
    public void deleteSuccess(String obj, int position) {
        this.currencies.get(position).setCollect(false);
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void addFail(Integer code, String toastMessage) {
        if (code == GlobalConstant.TOKEN_DISABLE1) {
            LoginActivity.actionStart(getActivity());
        } else {
            WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
        }
    }

    @Override
    public void addSuccess(String obj, int position) {
        this.currencies.get(position).setCollect(true);
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
    }

    public void notifyData() {
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void bannersSuccess(final List<BannerEntity> obj) {
        if (obj == null) {
            return;
        }
        imageUrls.clear();
        for (BannerEntity bannerEntity : obj) {
            imageUrls.add(bannerEntity.getUrl());
        }
        if (imageUrls.size() == 0) {
            banner = banner.setImages(localImageUrls);
        } else {
            if (banner == null) {
                return;
            }
            banner.setImages(imageUrls);
        }
        if (imageUrls.size() > 0) {
            // 设置图片集合
            banner.setImages(imageUrls);
        }
        // 设置样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setIndicatorGravity(BannerConfig.CENTER)
                .setImageLoader(new BannerImageLoader(banner.getWidth(), banner.getHeight()));
        banner.setPageTransformer(true, new ZoomOutPageTransformer());
        BannerViewPager bannerViewPager= banner.findViewById(R.id.bannerViewPager);
        bannerViewPager.setPageMargin(ScreenUtils.dip2px(getActivity(),-15));
        bannerViewPager.setOffscreenPageLimit(5);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (obj.size() == 0) {
                    return;
                }
                if (!TextUtils.isEmpty(obj.get(position).getLinkUrl())) {
                    if (obj.get(position).getLinkUrl().startsWith("http")) {
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(obj.get(position).getLinkUrl()));
                        intent.setAction(Intent.ACTION_VIEW);
                        getmActivity().startActivity(intent);
                    }
                }
            }
        });
        //设置轮播时间
        banner.setDelayTime(3000);
        banner.start();
    }

    @Override
    public void bannersFail(Integer code, String toastMessage) {
        //do nothing
    }

    public void tcpNotify() {
        if (mHomeAdapter != null) {
            mHomeAdapter.notifyDataSetChanged();
        }
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (marqueeView != null) {
                marqueeView.stopFlipping();
            }
        } else {
            if (marqueeView != null) {
                if (!marqueeView.isFlipping()) {
                    marqueeView.startFlipping();
                }
            }
        }
    }

    private void getMessage() {
        WonderfulOkhttpUtils.post().url(UrlFactory.getMessageUrl())
                .addParams("pageNo", 1 + "").addParams("pageSize", "100")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        List<Message> messages = new Gson().fromJson(object.getJSONObject("data").getJSONArray("content").toString(), new TypeToken<List<Message>>() {
                        }.getType());
                        messageList.clear();
                        messageList.addAll(messages);
                        setMarqueeView(messageList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                MessageDetailActivity.actionStart(getActivity(), messageList.get(infoss.get(position)).getId());
            }
        });
    }

    private List<Message> messageList = new ArrayList<>();
    private List<String> info = new ArrayList<>();
    private List<Integer> infoss = new ArrayList<>();

    private void setMarqueeView(List<Message> messageList) {
        info.clear();
        int code = SharedPreferenceInstance.getInstance().getLanguageCode();
        if (code == 1) {
            //中文
            for (int i = 0; i < messageList.size(); i++) {
                Message message = messageList.get(i);
                /* if (isContainChinese(message.getTitle())) {*/
                String str = "";
                if (message.getTitle().length() > 15) {
                    str = message.getTitle();
                    str = str.substring(0, 15);
                    info.add(str + "...");
                } else {
                    info.add(message.getTitle());
                }

                infoss.add(i);
                /*}*/
            }

        } else {
            for (int i = 0; i < messageList.size(); i++) {
                Message message = messageList.get(i);
                if (!isContainChinese(message.getTitle())) {
                    info.add(message.getTitle());
                    infoss.add(i);
                }
            }
        }
        if (info.size() > 0) {
            marqueeView.startWithList(info);
        }
    }

    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        return m.find();
    }


}
