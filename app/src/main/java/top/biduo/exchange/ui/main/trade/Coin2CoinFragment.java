package top.biduo.exchange.ui.main.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.customview.BottomSelectionFragment;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.common.ChooseCoinActivity;
import top.biduo.exchange.ui.entrust.TrustListActivity;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.ui.kline.KlineActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.adapter.SellAdapter;
import top.biduo.exchange.adapter.TrustCurrentAdapter;
import top.biduo.exchange.adapter.TrustHistoryAdapter;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.ui.dialog.BBConfirmDialogFragment;
import top.biduo.exchange.ui.dialog.EntrustOperateDialogFragment;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.entity.EntrustHistory;
import top.biduo.exchange.entity.Exchange;
import top.biduo.exchange.entity.Favorite;
import top.biduo.exchange.entity.Money;
import top.biduo.exchange.entity.TextItems;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.socket.ISocket;
import top.biduo.exchange.utils.PopupUtils;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.serivce.SocketMessage;
import top.biduo.exchange.serivce.SocketResponse;
import top.biduo.exchange.utils.IMyTextChange;
import top.biduo.exchange.utils.LoadDialog;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import com.xw.repo.BubbleSeekBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Request;


public class Coin2CoinFragment extends BaseTransFragment implements RadioGroup.OnCheckedChangeListener, Coin2CoinContract.View, View.OnClickListener {
    public static final String TAG = Coin2CoinFragment.class.getSimpleName();
    @BindView(R.id.ibOpen)
    ImageButton ibOpen;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivKline)
    ImageView ivKline;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.ll_price_type_selector)
    LinearLayout ll_price_type_selector; // 下拉
    @BindView(R.id.text_to_all)
    RelativeLayout mToAllLayout; // 全部
    @BindView(R.id.recyclerThree)
    RecyclerView mThreeRecycler; // 当前委托
    @BindView(R.id.mTvThree)
    TextView mTvThree;
    @BindView(R.id.mOneLayout)
    LinearLayout mOneLayout; // 买入
    @BindView(R.id.mTwoLayout)
    LinearLayout mTwoLayout; // 卖出
    @BindView(R.id.mOneShi)
    TextView mOneShi;
    @BindView(R.id.mOneXian)
    LinearLayout mOneXian;
    @BindView(R.id.mTwoShi)
    TextView mTwoShi;
    @BindView(R.id.mTwoXian)
    LinearLayout mTwoXian;
    @BindView(R.id.mOnePrice)
    EditText mOnePriceEdit;
    @BindView(R.id.mOneAdd)
    TextView mOneAdd; // +
    @BindView(R.id.mOneSum)
    TextView mOneSub; // -
    @BindView(R.id.mTwoPrice)
    EditText mTwoPriceEdit;
    @BindView(R.id.mTwoAdd)
    TextView mTwoAdd; // +
    @BindView(R.id.mTwoSub)
    TextView mTwoSub; // -
    @BindView(R.id.mTvPanJia)
    TextView mPanJia;
    @BindView(R.id.mTvMoney)
    TextView mPanMoney;
    @BindView(R.id.mOneYuE)
    TextView mOneYuE;
    @BindView(R.id.mTwoYuE)
    TextView mTwoYuE;
    @BindView(R.id.mTvOneBuy)
    TextView mOneBuy;
    @BindView(R.id.mOneTCP)
    EditText mOneTcpEdit;
    @BindView(R.id.mTwoTCP)
    EditText mTwoTcpEdit;
    @BindView(R.id.mTvTwoBuy)
    TextView mTwoBuy;
    @BindView(R.id.btnOneBuy)
    Button btnBuy;
    @BindView(R.id.btnTwoPost)
    Button btnSale;
    @BindView(R.id.mOneDeal)
    TextView mOneDeal; // 交易额
    @BindView(R.id.mTwoDeal)
    TextView mTwoDeal;
    @BindView(R.id.btn_toLogin)
    TextView btnLogin;
    @BindView(R.id.mOneTextType)
    TextView mOneTextType;
    @BindView(R.id.mTwoTextType)
    TextView mTwoTextType;
    @BindView(R.id.mOneJiaoYi)
    LinearLayout mOneJiaoYi;
    @BindView(R.id.mTwoJiaoYi)
    LinearLayout mTwoJiaoYi;
    @BindView(R.id.mOneSeekBar)
    BubbleSeekBar mOneSeekBar;
    @BindView(R.id.mTwoSeekBar)
    BubbleSeekBar mTwoSeekBar;
    Unbinder unbinder;
    @BindView(R.id.ivCompare)
    ImageView ivCompare;
    @BindView(R.id.text_one_kaishi)
    TextView text_one_kaishi;
    @BindView(R.id.text_one_jieshu)
    TextView text_one_jieshu;
    @BindView(R.id.text_two_kaishi)
    TextView text_two_kaishi;
    @BindView(R.id.text_two_jieshu)
    TextView text_two_jieshu;
    @BindView(R.id.mTabOne)
    RadioButton mTabOne;
    @BindView(R.id.mTabTwo)
    RadioButton mTabTwo;
    @BindView(R.id.ll_current_trust)
    LinearLayout llCurrentTrust;
    @BindView(R.id.ll_history_trust)
    LinearLayout llHistoryTrust;
    @BindView(R.id.tv_current_trust)
    TextView tvCurrentTrust;
    @BindView(R.id.tv_history_trust)
    TextView tvHistoryTrust;
    @BindView(R.id.current_trust_underline)
    View currentTrustUnderline;
    @BindView(R.id.history_trust_underline)
    View historyTrustUnderline;
    @BindView(R.id.iv_check_box_one)
    ImageView iv_check_box_one;
    @BindView(R.id.iv_check_box_two)
    ImageView iv_check_box_two;

    @BindView(R.id.ll_sell_section_touch)
    LinearLayout ll_sell_section_touch;
    @BindView(R.id.ll_buy_section_touch)
    LinearLayout ll_buy_section_touch;
    @BindView(R.id.mTwoPriceTouch)
    EditText mTwoPriceTouch;
    @BindView(R.id.mOnePriceTouch)
    EditText mOnePriceTouch;
    @BindView(R.id.mOneAddTouch)
    TextView mOneAddTouch;
    @BindView(R.id.mOneSubTouch)
    TextView mOneSubTouch;
    @BindView(R.id.mTwoAddTouch)
    TextView mTwoAddTouch;
    @BindView(R.id.mTwoSubTouch)
    TextView mTwoSubTouch;
    @BindView(R.id.mTvOneBuyTouch)
    TextView mTvOneBuyTouch;
    @BindView(R.id.mTvTwoBuyTouch)
    TextView mTvTwoBuyTouch;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;

    private String maichu;
    int pageSize = 3;
    int currentPage = 1;
    int historyPage = 1;
    int startPage = 1;
    private List<Currency> currencies = new ArrayList<>();
    private Currency mCurrency;
    private List<Exchange> mOne = new ArrayList<>();
    private List<Exchange> mTow = new ArrayList<>();
    private SellAdapter mOneAdapter; // 买入适配器
    private SellAdapter mTwoAdapter; // 卖出适配器
    private Coin2CoinContract.Presenter mPresenter;
    private int oneAccuracy = 2; // 价格
    private int twoAccuracy = 2; // 数量
    private String oldSymbol; // 上个订阅的币种
    private Boolean isSeekBar = false; // 上个订阅的币种
    private boolean isFace = false;
    @BindView(R.id.tvchange)
    TextView tvchange;
    String buttonTextBuy = "";
    String buttonTextSell = "";
    @BindView(R.id.tv_price_type)
    TextView tv_price_type;

    @Override
    public void getAccuracy(final int one, final int two) {
        twoAccuracy = one; // 数量
        oneAccuracy = two; // 价格
        if (mOneAdapter == null || mTwoAdapter == null) {
            return;
        }
        mOneAdapter.setText(new SellAdapter.myText() {
            @Override
            public int one() {
                return one;
            }

            @Override
            public int two() {
                return two;
            }
        });
        mOneAdapter.notifyDataSetChanged();
        mTwoAdapter.setText(new SellAdapter.myText() {
            @Override
            public int one() {
                return one;
            }

            @Override
            public int two() {
                return two;
            }
        });
        mTwoAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frgment_text_three;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvCurrentTrust.setSelected(true);
        mPresenter = new Coin2CoinPresenter(Injection.provideTasksRepository(getActivity()), this);
        // 打开左侧的滑动
        ibOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getDlRoot(MainActivity.MENU_TYPE_EXCHANGE).openDrawer(Gravity.LEFT);
            }
        });
        for (int i = 0; i < 5; i++) {
            mOne.add(new Exchange(5 - i, "--", "--"));
            mTow.add(new Exchange(i, "--", "--"));
        }
        ll_price_type_selector.setOnClickListener(this);
        mToAllLayout.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mOneAdd.setOnClickListener(this);
        mOneSub.setOnClickListener(this);
        mTwoAdd.setOnClickListener(this);
        mTwoSub.setOnClickListener(this);

        mOneAddTouch.setOnClickListener(this);
        mOneSubTouch.setOnClickListener(this);
        mTwoAddTouch.setOnClickListener(this);
        mTwoSubTouch.setOnClickListener(this);

        btnBuy.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        ivKline.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        llCurrentTrust.setOnClickListener(this);
        llHistoryTrust.setOnClickListener(this);
        //iv_check_box_one.setOnClickListener(this);
        //iv_check_box_two.setOnClickListener(this);
        // 买入价格的变化
        mOnePriceEdit.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mOneTextPrice = Double.valueOf(mOnePriceEdit.getText().toString());
                } catch (Exception e) {
                    mOneTextPrice = 0;
                }
                if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                    if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                        mOneMyText = mOneText * mOneTextPrice;
                        mOneBuy.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mOneTextPrice * 1,
                                2, null) + "CNY"));
                    } else {
                        mOneMyText = mOneText * mOneTextPrice * MainActivity.rate;
                        mOneBuy.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mOneTextPrice * 1 * MainActivity.rate * mCurrency.getBaseUsdRate(),
                                2, null) + "CNY"));
                    }
                    if (!TextUtils.isEmpty(mOneTcpEdit.getText())) {
                        mOneDeal.setText(String.valueOf(WonderfulMathUtils.getRundNumber(mul(mOneTextPrice * mOneText, 1), 5, null)
                                + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                    }
                }
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - (posDot + 1) > oneAccuracy) {
                    editable.delete(posDot + 1 + oneAccuracy, posDot + 2 + oneAccuracy);
                }
            }
        });
        // 触发价-买入价格的变化
        mOnePriceTouch.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mOneTextPriceTouch = Double.valueOf(editable.toString());
                } catch (Exception e) {
                    mOneTextPriceTouch = 0;
                }
                if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                    mTvOneBuyTouch.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mOneTextPriceTouch * 1,
                            2, null) + "CNY"));
                } else {
                    mTvOneBuyTouch.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mOneTextPriceTouch * 1 * MainActivity.rate * mCurrency.getBaseUsdRate(),
                            2, null) + "CNY"));
                }
            }
        });
        // 触发价-卖出价格的变化
        mTwoPriceTouch.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mTwoTextPriceTouch = Double.valueOf(editable.toString());
                } catch (Exception e) {
                    mTwoTextPriceTouch = 0;
                }
                if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                    mTvTwoBuyTouch.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mTwoTextPriceTouch * 1,
                            2, null) + "CNY"));
                } else {
                    mTvTwoBuyTouch.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mTwoTextPriceTouch * 1 * MainActivity.rate * mCurrency.getBaseUsdRate(),
                            2, null) + "CNY"));
                }
            }
        });
        // 卖出价格的变化
        mTwoPriceEdit.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mTwoTextPrice = Double.valueOf(mTwoPriceEdit.getText().toString());

                } catch (Exception e) {
                    mTwoTextPrice = 0;
                }
                if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                    if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                        mTwoBuy.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mTwoTextPrice * 1,
                                2, null) + "CNY"));
                        mTwoMyText = mTwoText * mTwoTextPrice;
                    } else {
                        mTwoBuy.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mTwoTextPrice * 1 * MainActivity.rate * mCurrency.getBaseUsdRate(),
                                2, null) + "CNY"));
                        mTwoMyText = mTwoText * mTwoTextPrice * MainActivity.rate;
                    }
                    if (!TextUtils.isEmpty(mTwoTcpEdit.getText())) {
                        mTwoDeal.setText(String.valueOf(WonderfulMathUtils.getRundNumber(mul(mTwoText * mTwoTextPrice, 1), 5, null) +
                                mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                    }
                }
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - (posDot + 1) > oneAccuracy) {
                    editable.delete(posDot + 1 + oneAccuracy, posDot + 2 + oneAccuracy);
                }
            }
        });
        // 买入数量的变化
        mOneTcpEdit.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mOneText = Double.valueOf(mOneTcpEdit.getText().toString());
                    if (mOneText <= 0) {
                        text_one_kaishi.setText("0.0");
                    } else {
                        text_one_kaishi.setText(mOneTcpEdit.getText().toString());
                    }

                } catch (Exception e) {
                    mOneText = 0;
                }
                if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                    if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                        mOneMyText = mOneText * mOneTextPrice * mCurrency.getBaseUsdRate();
                    } else {
                        mOneMyText = mOneText * mOneTextPrice * MainActivity.rate * mCurrency.getBaseUsdRate();
                    }
                    mOneDeal.setText(String.valueOf(WonderfulMathUtils.getRundNumber(mul(mOneTextPrice * mOneText, 1), 5, null)
                            + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                    if (!WonderfulStringUtils.isEmpty(mOneTcpEdit.getText().toString()) && !WonderfulStringUtils.isEmpty(mOnePriceEdit.getText().toString()) && mOneTcpEdit.isFocused()) {
                        try {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null)) / Double.valueOf(mOnePriceEdit.getText().toString());
                            Double d2 = Double.valueOf(mOneTcpEdit.getText().toString());
                            Float aFloat = Float.valueOf(WonderfulMathUtils.getRundNumber(d2 / d, 2, null)) * 100;
                            if (aFloat < 0) {
                                aFloat = 0f;
                            }
                            isSeekBar = false;
                            if (aFloat > 100) {
                                WonderfulToastUtils.showToast(getResources().getString(R.string.input_number_over_limit1));
                            }
                            mOneSeekBar.setProgress(aFloat >= 100 ? 100 : aFloat);
                            text_one_jieshu.setText(mOneSeekBar.getProgress() + "%");
                        } catch (Exception e) {

                        }

                    }
                } else {
                    mOneDeal.setText(String.valueOf("--"));
                    if (!WonderfulStringUtils.isEmpty(mOneTcpEdit.getText().toString())) {
                        try {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            Double d2 = Double.valueOf(mOneTcpEdit.getText().toString());
                            Float aFloat = Float.valueOf(WonderfulMathUtils.getRundNumber(d2 / d, 2, null)) * 100;
                            if (aFloat < 0) {
                                aFloat = 0f;
                            }
                            isSeekBar = false;
                            mOneSeekBar.setProgress(aFloat >= 100 ? 100 : aFloat);
                            text_one_jieshu.setText(mOneSeekBar.getProgress() + "%");
                        } catch (Exception e) {

                        }

                    }
                }
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - (posDot + 1) > twoAccuracy) {
                    editable.delete(posDot + 1 + twoAccuracy, posDot + 2 + twoAccuracy);
                }
            }
        });

        //mOneSeekBar.requestFocus();
        mOneSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                WonderfulLogUtils.logi("Coin2CoinFragment", "    onProgressChanged");
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                isSeekBar = true;
                if (mCurrency != null) {
                    if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                        if (!mOneTextType.getText().equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                            if (!WonderfulStringUtils.isEmpty(mOnePriceEdit.getText().toString()) && isSeekBar) {
                                try {
                                    Double aDouble = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                                    aDouble = Double.valueOf(new BigDecimal(aDouble).setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                                    Double d = aDouble / Double.valueOf(mOnePriceEdit.getText().toString());
                                    d = Double.valueOf(new BigDecimal(d).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                                    if (aDouble <= 0) {
                                        return;
                                    }


                                    String s = WonderfulMathUtils.getRundNumber(d * progress / 100.3, twoAccuracy, null);
                                    if (twoAccuracy == 0) {
                                        s = s.substring(0, s.length() - 1);
                                        mOneTcpEdit.setText(s);
                                    } else {
                                        mOneTcpEdit.setText("" + new BigDecimal(d * progress / 100.5).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                                    }
                                    mOneSeekBar.setProgress(progress);

                                    text_one_jieshu.setText(progress + "%");
                                } catch (Exception e) {

                                }

                            } else {
                                try {
                                    Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                                    d = Double.valueOf(new BigDecimal(d).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                                    mOneTcpEdit.setText(new BigDecimal(d * progress / 100.5).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");
                                    text_one_jieshu.setText(progress + "%");
                                } catch (Exception e) {

                                }

                            }
                        }
                    } else {
                        try {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            d = Double.valueOf(new BigDecimal(d).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            mOneTcpEdit.setText(new BigDecimal(d * progress / 100.5).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");
                            text_one_jieshu.setText(progress + "%");
                        } catch (Exception e) {

                        }

                    }
                }
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                WonderfulLogUtils.logi("Coin2CoinFragment", "    getProgressOnFinally");
                //super.getProgressOnFinally(bubbleSeekBar, progress, progressFloat);
                /*if (currency != null) {
                    if (!WonderfulStringUtils.isEmpty(mOnePriceEdit.getText().toString()) && mOneSeekBar.isFocused()) {
                        if (!mOneTextType.getText().equals(currency.getSymbol().substring(currency.getSymbol().indexOf("/") + 1, currency.getSymbol().length()))) {
                            Double aDouble = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            Double d = aDouble / Double.valueOf(mOnePriceEdit.getText().toString());
                            String s = WonderfulMathUtils.getRundNumber(d * progress / 100, 4, null);
                            mOneTcpEdit.setText(s);
                            WonderfulLogUtils.logi("Coin2CoinFragment", "dddd3333   " +String.valueOf(d));
                        }else {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            mOneTcpEdit.setText(WonderfulMathUtils.getRundNumber(d * progress / 100, 4, null));
                            WonderfulLogUtils.logi("Coin2CoinFragment", "dddd4444   " +String.valueOf(d));
                        }
                    }
                }*/
            }
        });

        // 卖出数量的变化
        mTwoTcpEdit.addTextChangedListener(new IMyTextChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (mCurrency == null) {
                    return;
                }
                try {
                    mTwoText = Double.valueOf(mTwoTcpEdit.getText().toString());
                    if (mTwoText <= 0) {
                        text_two_kaishi.setText("0.0");
                    } else {
                        text_two_kaishi.setText(mTwoTcpEdit.getText().toString());
                    }
                } catch (Exception e) {
                    mTwoText = 0;
                }
                if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                    if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                        mTwoMyText = mTwoText * mTwoTextPrice * mCurrency.getBaseUsdRate();
                    } else {
                        mTwoMyText = mTwoText * mTwoTextPrice * MainActivity.rate * mCurrency.getBaseUsdRate();
                    }
                    mTwoDeal.setText(String.valueOf(WonderfulMathUtils.getRundNumber(mul(mTwoText * mTwoTextPrice, 1), 5, null)
                            + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                    if (!WonderfulStringUtils.isEmpty(mTwoTcpEdit.getText().toString()) && !WonderfulStringUtils.isEmpty(mTwoPriceEdit.getText().toString()) && mTwoTcpEdit.isFocused()) {
                        try {
                            double dmai = Double.parseDouble(maichu);
                            if (dmai <= 0) {
                                mTwoTcpEdit.setText("");
                                WonderfulToastUtils.showToast(getResources().getString(R.string.input_number_over_limit2));
                                return;
                            }
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null)) / Double.valueOf(mTwoPriceEdit.getText().toString());
                            Double d2 = Double.valueOf(mTwoTcpEdit.getText().toString());
//                            Float aFloat = Float.valueOf(WonderfulMathUtils.getRundNumber(d2 / d, 2, null)) * 100;
                            double v = Double.valueOf(new BigDecimal(d2).toPlainString().toString()) / Double.valueOf(new BigDecimal(maichu).toPlainString().toString());
                            Float aFloat = Float.valueOf(new BigDecimal(v).setScale(2, BigDecimal.ROUND_DOWN).toPlainString().toString()) * 100;
                            //String.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 8, null)
                            WonderfulLogUtils.logi("miao1", "aFloat:" + new BigDecimal(maichu).toPlainString().toString());
                            WonderfulLogUtils.logi("miao1", "aFloat2:" + new BigDecimal(v).setScale(2, BigDecimal.ROUND_DOWN).toPlainString().toString());
                            if (aFloat < 0) {
                                aFloat = 0f;
                            } else if (aFloat > 100) {
                                WonderfulToastUtils.showToast(getResources().getString(R.string.input_number_over_limit2));
                                aFloat = 100f;
                            }
                            isSeekBar = false;
                            mTwoSeekBar.setProgress(aFloat);
                            text_two_jieshu.setText(mTwoSeekBar.getProgress() + "%");
                            WonderfulLogUtils.logi("miao1", "mTwoSeekBar:" + mTwoSeekBar.getProgress());
                        } catch (Exception e) {

                        }

                    }
                } else {
                    mTwoDeal.setText(String.valueOf("--"));
                    if (!WonderfulStringUtils.isEmpty(mTwoTcpEdit.getText().toString())) {
                        try {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            Double d2 = Double.valueOf(mTwoTcpEdit.getText().toString());
                            Float aFloat = Float.valueOf(WonderfulMathUtils.getRundNumber(d2 / d, 2, null)) * 100;
                            if (aFloat < 0) {
                                aFloat = 0f;
                            }
                            isSeekBar = false;
                            mTwoSeekBar.setProgress(aFloat >= 100 ? 100 : aFloat);
                        } catch (Exception e) {

                        }

                    }
                }
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - (posDot + 1) > twoAccuracy) {
                    editable.delete(posDot + 1 + twoAccuracy, posDot + 2 + twoAccuracy);
                }
            }
        });

        mTwoSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                WonderfulLogUtils.logi("Coin2CoinFragment", "    progress" + progress);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                isSeekBar = true;
                if (mCurrency != null) {
                    if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                        if (!mTwoTextType.getText().equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                            if (!WonderfulStringUtils.isEmpty(mTwoPriceEdit.getText().toString()) && isSeekBar) {
                                try {

                                    Double aDouble = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance2, 5, null));
                                    Double d = aDouble / Double.valueOf(mTwoPriceEdit.getText().toString());
                                    if (aDouble <= 0) {
                                        mTwoSeekBar.setProgress(0f);
                                        return;
                                    }


                                    String s = WonderfulMathUtils.getRundNumber(aDouble * progress / 100, twoAccuracy, null);
                                    if (twoAccuracy == 0) {
                                        s = s.substring(0, s.length() - 1);
                                    }

                                    WonderfulLogUtils.logd("miao", new BigDecimal(aDouble * progress / 100).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
//                                    WonderfulLogUtils.logd("miao",Double.parseDouble(s.substring(0,s.indexOf(".")+twoAccuracy))+"---");
                                    mTwoTcpEdit.setText(new BigDecimal(aDouble * progress / 100).setScale(twoAccuracy, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                                    mTwoSeekBar.setProgress(progress);
                                    text_two_jieshu.setText(progress + "%");
                                } catch (Exception e) {

                                }


                            } else {
                                try {
                                    Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance2, 5, null));
                                    mTwoTcpEdit.setText(WonderfulMathUtils.getRundNumber(d * progress / 100, twoAccuracy, null));
                                    mTwoSeekBar.setProgress(progress);
                                    text_two_jieshu.setText(progress + "%");
                                } catch (Exception e) {

                                }


                            }
                        }
                    } else {
                        try {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            mTwoTcpEdit.setText(WonderfulMathUtils.getRundNumber(d * progress / 100, twoAccuracy, null));
                            text_two_jieshu.setText(progress + "%");
                        } catch (Exception e) {

                        }


                    }

                }

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                WonderfulLogUtils.logi("Coin2CoinFragment", "    getProgressOnFinally");
                //super.getProgressOnFinally(bubbleSeekBar, progress, progressFloat);
                /*if (currency != null) {
                    if (!WonderfulStringUtils.isEmpty(mOnePriceEdit.getText().toString()) && mOneSeekBar.isFocused()) {
                        if (!mOneTextType.getText().equals(currency.getSymbol().substring(currency.getSymbol().indexOf("/") + 1, currency.getSymbol().length()))) {
                            Double aDouble = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            Double d = aDouble / Double.valueOf(mOnePriceEdit.getText().toString());
                            String s = WonderfulMathUtils.getRundNumber(d * progress / 100, 4, null);
                            mOneTcpEdit.setText(s);
                            WonderfulLogUtils.logi("Coin2CoinFragment", "dddd3333   " +String.valueOf(d));
                        }else {
                            Double d = Double.valueOf(WonderfulMathUtils.getRundNumber(usdeBalance, 5, null));
                            mOneTcpEdit.setText(WonderfulMathUtils.getRundNumber(d * progress / 100, 4, null));
                            WonderfulLogUtils.logi("Coin2CoinFragment", "dddd4444   " +String.valueOf(d));
                        }
                    }
                }*/
            }
        });
        mPanJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {
                    mOnePriceEdit.setText(mPanJia.getText());
                } else {
                    mTwoPriceEdit.setText(mPanJia.getText());
                }
            }
        });

        ivCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/10/16 compare
            }
        });
        ivMenu.setVisibility(View.VISIBLE);
        ivMenu.setOnClickListener(this);
        mTabOne.setSelected(true);
        initRecyclerView();
    }

    private List<EntrustHistory> mCurrentData = new ArrayList<>();
    private List<EntrustHistory> mHistoryData = new ArrayList<>();
    TrustCurrentAdapter trustCurrentAdapter = new TrustCurrentAdapter(mCurrentData);
    TrustHistoryAdapter trustHistoryAdapter = new TrustHistoryAdapter(mHistoryData);

    private void initRecyclerView() {
        mThreeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mThreeRecycler.setAdapter(trustCurrentAdapter);
        trustCurrentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showBottomFragment((EntrustHistory) adapter.getData().get(position));
            }
        });

    }

    private boolean addFace(String symbol) {
        for (Favorite favorite : MainActivity.mFavorte) {
            if (symbol.equals(favorite.getSymbol())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除收藏
     *
     * @param token
     * @param symbol
     */
    private void delete(String token, final String symbol) {
        if (!MyApplication.getApp().isLogin()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            return;
        }
        showDialog();
        WonderfulOkhttpUtils.post().url(UrlFactory.getDeleteUrl()).addHeader("x-auth-token", token)
                .addParams("symbol", symbol).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                hideDialog();
                WonderfulToastUtils.showToast(getResources().getString(R.string.text_cancel_fail));
            }

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_cancel_success));
                        isFace = false;
                        ((MainActivity) getmActivity()).Find();
                    } else {
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_cancel_fail));
                    }
                } catch (JSONException e) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_cancel_fail));
                }
            }
        });
    }

    /**
     * 添加收藏
     *
     * @param token
     * @param symbol
     */
    private void getCollect(String token, final String symbol) {
        if (!MyApplication.getApp().isLogin()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            return;
        }
        showDialog();
        WonderfulOkhttpUtils.post().url(UrlFactory.getAddUrl()).addHeader("x-auth-token", token)
                .addParams("symbol", symbol).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                hideDialog();
                WonderfulToastUtils.showToast(getResources().getString(R.string.text_add_fail));
            }

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject object = new JSONObject(response);
                    WonderfulLogUtils.logi("jiejie", "  getCollect  " + response);
                    if (object.optInt("code") == 0) {
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_add_success));
                        isFace = true;
                        ((MainActivity) getmActivity()).Find();
                    } else {
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_add_fail));
                    }
                } catch (JSONException e) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_add_fail));
                }
            }
        });
    }


    private void setPriceNull() {
        mOnePriceEdit.setText(null);
        mTwoPriceEdit.setText(null);
        mOneTcpEdit.setText(null);
        mTwoTcpEdit.setText(null);
    }

    double mOneText, mTwoText, mOneTextPrice, mTwoTextPrice, mOneMyText, mTwoMyText, mOneTextPriceTouch, mTwoTextPriceTouch;

    @Override
    protected void obtainData() {
        recyclerSell.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerBuy.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOneAdapter = new SellAdapter(new ArrayList<Exchange>(), 0);
        mTwoAdapter = new SellAdapter(new ArrayList<Exchange>(), 1);
        recyclerSell.setAdapter(mOneAdapter);
        recyclerBuy.setAdapter(mTwoAdapter);
        mOneAdapter.setOnItemClickListener(listenerOne);
        mTwoAdapter.setOnItemClickListener(listenerTwo);
    }

    // 第一个Item的点击事件
    BaseQuickAdapter.OnItemClickListener listenerOne = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Exchange e = (Exchange) adapter.getData().get(position);
            if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                if (!"--".equals(e.getPrice())) {
                    if (check == 0) {
                        mOnePriceEdit.setText(String.valueOf(WonderfulMathUtils.getRundNumber(Double.valueOf(e.getPrice()), oneAccuracy, null)));
                    } else {
                        mTwoPriceEdit.setText(String.valueOf(WonderfulMathUtils.getRundNumber(Double.valueOf(e.getPrice()), oneAccuracy, null)));
                    }
                }
            }
        }
    };
    // 第二个Item的点击事件
    BaseQuickAdapter.OnItemClickListener listenerTwo = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Exchange e = (Exchange) adapter.getData().get(position);
            if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) { // 限价或止盈
                if (!"--".equals(e.getPrice())) {
                    if (check == 0) {
                        mOnePriceEdit.setText(String.valueOf(WonderfulMathUtils.getRundNumber(Double.valueOf(e.getPrice()), oneAccuracy, null)));
                    } else {
                        mTwoPriceEdit.setText(String.valueOf(WonderfulMathUtils.getRundNumber(Double.valueOf(e.getPrice()), oneAccuracy, null)));
                    }
                }
            }
        }
    };

    private void showBottomFragment(EntrustHistory entrust) {
        EntrustOperateDialogFragment entrustOperateFragment =
                EntrustOperateDialogFragment.getInstance(entrust);
        entrustOperateFragment.show(getActivity().getSupportFragmentManager(), "bottom");
        entrustOperateFragment.setCallback(new EntrustOperateDialogFragment.OperateCallback() {
            @Override
            public void cancleOrder(String orderId) {
                // 撤消
                if (mPresenter != null) {
                    mPresenter.getCancelEntrust(SharedPreferenceInstance.getInstance().getTOKEN(), orderId);
                }
            }
        });
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        // 获取盘口的信息
        if (this.mCurrency != null && !TextUtils.isEmpty(SharedPreferenceInstance.getInstance().getTOKEN())) {
            mPresenter.getExchange(mCurrency.getSymbol());
            Log.i("getExchange", "2" + mCurrency.getSymbol());
            mPresenter.getWallet(1, SharedPreferenceInstance.getInstance().getTOKEN(), this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            mPresenter.getWallet(2, SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()));
        }
        if (!MyApplication.getApp().isLogin()) {
            btnLogin.setVisibility(View.VISIBLE);
            mThreeRecycler.setVisibility(View.GONE);
            mTvThree.setVisibility(View.GONE);
            SharedPreferenceInstance.getInstance().saveaToken("");
            SharedPreferenceInstance.getInstance().saveTOKEN("");
            mTwoYuE.setText("0.0");
            mOneYuE.setText("0.0");
        } else {
            if (MyApplication.getApp().isLogin() && this.mCurrency != null) {
                currentPage = startPage;
                historyPage = startPage;
                setCurrentSelected();
            } else {
                SharedPreferenceInstance.getInstance().saveaToken("");
                SharedPreferenceInstance.getInstance().saveTOKEN("");
            }
        }
        if (mCurrency != null) {
            isFace = addFace(this.mCurrency.getSymbol());
            String format = new DecimalFormat("#0.00000000").format(mCurrency.getClose());
            BigDecimal bg = new BigDecimal(format);
            String v = bg.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
            mOnePriceEdit.setText(v);
            mTwoPriceEdit.setText(v);
            tvTitle.setText(this.mCurrency.getSymbol());
            if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) {
                mOneTextType.setText(this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            }
            mTwoTextType.setText(this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            buttonTextBuy = String.valueOf(getResources().getString(R.string.text_buy) + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            buttonTextSell = String.valueOf(getResources().getString(R.string.text_sale) + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            btnBuy.setText(buttonTextBuy);
            btnSale.setText(buttonTextSell);
        }
        if (mCurrency != null) {
            String format = new DecimalFormat("#0.00000000").format(mCurrency.getClose());

            BigDecimal bg = new BigDecimal(format);
            String v = bg.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
            mPanJia.setText(String.valueOf(v));
            tvchange.setText((mCurrency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(mCurrency.getChg() * 100, 2, "########0.") + "%");
            tvchange.setEnabled(mCurrency.getChg() >= 0);
        }
        if (mCurrency != null) {
            mPanJia.setTextColor(mCurrency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) :
                    ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
        }
        if (mCurrency != null) {
            if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mCurrency.getClose() * 1 * mCurrency.getBaseUsdRate(),
                        2, null) + "CNY"));
            } else {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mCurrency.getClose() * MainActivity.rate * mCurrency.getBaseUsdRate(),
                        2, null) + "CNY"));
            }
        }
    }


    private double mul(double num1, double num2) {
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        return b1.multiply(b2).doubleValue();
    }

    private JSONObject buildGetBodyJson(String symbol) {
        JSONObject obj = new JSONObject();
//        Log.i("miao","TCP："+symbol);
        try {
            obj.put("symbol", symbol);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    private JSONObject buildGetBodyJson(String symbol, int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("symbol", symbol);
            obj.put("uid", id);
            return obj;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 这里我开始订阅某个币盘口的信息
     */
    private void startTCP(String symbol, int id) {
        if (id == 0) {
            // 不需要iD
            EventBus.getDefault().post(new SocketMessage(0, ISocket.CMD.SUBSCRIBE_EXCHANGE_TRADE,
                    buildGetBodyJson(symbol).toString().getBytes()));
//            Log.i("miao","startTCP："+symbol);
        } else {
            // 需要ID
            EventBus.getDefault().post(new SocketMessage(0, ISocket.CMD.SUBSCRIBE_EXCHANGE_TRADE,
                    buildGetBodyJson(symbol, id).toString().getBytes()));
//            Log.i("miao","startTCPid："+symbol);
        }
        oldSymbol = symbol;
    }

    /**
     * 这里我取消某个币盘口的信息
     */
    private void stop(String symbol, int id) {
        if (id == 0) {
            EventBus.getDefault().post(new SocketMessage(0, ISocket.CMD.UNSUBSCRIBE_EXCHANGE_TRADE,
                    buildGetBodyJson(symbol).toString().getBytes()));
        } else {
            EventBus.getDefault().post(new SocketMessage(0, ISocket.CMD.UNSUBSCRIBE_EXCHANGE_TRADE,
                    buildGetBodyJson(symbol, id).toString().getBytes()));
        }

    }

    @Override
    protected String getmTag() {
        return TAG;
    }


    public void resetSymbol(Currency currency, int position) {
        this.mCurrency = currency;
        if (mRadioGroup != null) {
            if (position == 0) { // 买入
                mRadioGroup.check(R.id.mTabOne);
            } else { //卖出
                mRadioGroup.check(R.id.mTabTwo);
            }
            resetSymbol(currency);
        }
    }

    /**
     * 这个是从主界面来的，表示选择了某个币种
     */
    public void resetSymbol(Currency currency) {
        this.mCurrency = currency;
//        Log.i("miao","执行点击以后");
//        Log.i("miao","resetSymbol:"+currency.getSymbol());
//        Log.i("miao","currency:"+this.currency.getSymbol());
        if (this.mCurrency != null) {
            setCurrentSelected();
            text_one_kaishi.setText("0");
            mOneDeal.setText("--");
            text_one_jieshu.setText("0");
            mOneSeekBar.setProgress(0f);
            mTwoSeekBar.setProgress(0f);
            text_two_kaishi.setText("0");
            text_two_jieshu.setText("0");
            mTwoDeal.setText("--");
            setPriceNull();
            String format2 = new DecimalFormat("#0.00000000").format(currency.getClose());
            BigDecimal bg2 = new BigDecimal(format2);
            String v2 = bg2.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
            mOnePriceEdit.setText(v2);
            mTwoPriceEdit.setText(v2);
            // 获取盘口信息
            mPresenter.getExchange(this.mCurrency.getSymbol());
            Log.i("getExchange", "1" + currency.getSymbol());
//            Log.i("miao","币种："+currency.getSymbol());
            // 获取当前币种精确度
            mPresenter.getSymbolInfo(this.mCurrency.getSymbol());
            // 如果是登录状态
            if (!WonderfulStringUtils.isEmpty(SharedPreferenceInstance.getInstance().getTOKEN())) {
                // 获取这个币的多少
                mPresenter.getWallet(1, SharedPreferenceInstance.getInstance().getTOKEN(), this.mCurrency.getSymbol());
                // 获取该币的委托信息
                currentPage = startPage;
                historyPage = startPage;
                mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), startPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
            }
            // 修改标题
            isFace = addFace(this.mCurrency.getSymbol());
            tvTitle.setText(this.mCurrency.getSymbol());
            if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) {
                mOneTextType.setText(this.mCurrency.getSymbol().substring(0, currency.getSymbol().indexOf("/")));
            }
            mTwoTextType.setText(this.mCurrency.getSymbol().substring(0, currency.getSymbol().indexOf("/")));
            buttonTextBuy = String.valueOf(getResources().getString(R.string.text_buy) + this.mCurrency.getSymbol().substring(0, currency.getSymbol().indexOf("/")));
            buttonTextSell = String.valueOf(getResources().getString(R.string.text_sale) + this.mCurrency.getSymbol().substring(0, currency.getSymbol().indexOf("/")));
            btnBuy.setText(buttonTextBuy);
            btnSale.setText(buttonTextSell);
            String format = new DecimalFormat("#0.00000000").format(currency.getClose());
            BigDecimal bg = new BigDecimal(format);
            String v = bg.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
            mPanJia.setText(String.valueOf(v));
            tvchange.setText((mCurrency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(mCurrency.getChg() * 100, 2, "########0.") + "%");
            tvchange.setEnabled(mCurrency.getChg() >= 0);
//            mPanJia.setText(String.valueOf(currency.getClose()));
            mPanJia.setTextColor(currency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) :
                    ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
            if ("CNY".equals(currency.getSymbol().substring(currency.getSymbol().indexOf("/") + 1, currency.getSymbol().length()))) {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(currency.getClose() * 1 * currency.getBaseUsdRate(),
                        2, null) + "CNY"));
            } else {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(currency.getClose() * MainActivity.rate * currency.getBaseUsdRate(),
                        2, null) + "CNY"));
            }
            // 代表选择了哪个币种，需要重新订阅 应该先取消原来的再订阅现在的
            if (!TextUtils.isEmpty(oldSymbol)) {
                stop(oldSymbol, getmActivity().getId());
            }
//            Log.i("miao","币种："+ getmActivity().getId());
            startTCP(currency.getSymbol(), getmActivity().getId());
        }
    }


    public void showPageFragment(String symbol, int pageNo) {
        if (this.currencies == null) {
            return;
        }
        // this.page = pageNo;
        for (Currency currency : currencies) {
            if (symbol.equals(currency.getSymbol())) {
                this.mCurrency = currency;
                break;
            }
        }
        if (this.mCurrency != null) {
            try {
                tvTitle.setText(symbol);
                if (priceType.equals("LIMIT_PRICE") || priceType.equals("CHECK_FULL_STOP")) {
                    mOneTextType.setText(symbol.substring(0, mCurrency.getSymbol().indexOf("/")));
                }
                mTwoTextType.setText(symbol.substring(0, mCurrency.getSymbol().indexOf("/")));

                btnBuy.setText(String.valueOf(WonderfulStringUtils.getString(R.string.text_buy) + symbol.substring(0, mCurrency.getSymbol().indexOf("/"))));
                btnSale.setText(String.valueOf(WonderfulStringUtils.getString(R.string.text_sale) + symbol.substring(0, mCurrency.getSymbol().indexOf("/"))));
                // 获取盘口
                mPresenter.getExchange(this.mCurrency.getSymbol());
                Log.i("getExchange", "3" + mCurrency.getSymbol());
                // 获取盘口精确度
                mPresenter.getSymbolInfo(this.mCurrency.getSymbol());
                if (!TextUtils.isEmpty(oldSymbol)) {
                    stop(oldSymbol, getmActivity().getId());
                }
                startTCP(mCurrency.getSymbol(), getmActivity().getId());
                if (pageNo == 0) { // 买入
                    mRadioGroup.check(R.id.mTabOne);
                } else { //卖出
                    mRadioGroup.check(R.id.mTabTwo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        vpExchange.setCurrentItem(pageNo);
    }

    /**
     * 这个是刚开始
     */
    public void setViewContent(List<Currency> currencies) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
        mPresenter.getDefaultSymbol();
    }

    boolean firstLoad = true;

    @Override
    public void onResume() {
        super.onResume();
        if (firstLoad) {
            if (!TextUtils.isEmpty(oldSymbol)) {
                stop(oldSymbol, getmActivity().getId());
            }
            resetSymbol(mCurrency);
            firstLoad = false;
        }
        if (MyApplication.getApp().isLogin()) {
            if (mCurrency != null) {
                if (isCurrent) {
                    mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), startPage, pageSize, mCurrency.getSymbol(), "", "", "", "");
                } else {
                    mPresenter.getOrderHistory(SharedPreferenceInstance.getInstance().getTOKEN(), startPage, pageSize, mCurrency.getSymbol(), "", "", "", "");
                }
            }
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            mThreeRecycler.setVisibility(View.GONE);
            mTvThree.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (MyApplication.getApp().isLogin() && mCurrency != null) {
                mPresenter.getWallet(0, getmActivity().getToken(), mCurrency.getSymbol());

            }
        }
    }

    public void tcpNotify() {
        if (mCurrency != null) {
            String format = new DecimalFormat("#0.00000000").format(mCurrency.getClose());
            BigDecimal bg = new BigDecimal(format);
            String v = bg.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
            if (mPanJia == null) return;
            mPanJia.setText(String.valueOf(v));
            tvchange.setText((mCurrency.getChg() >= 0 ? "+" : "") + WonderfulMathUtils.getRundNumber(mCurrency.getChg() * 100, 2, "########0.") + "%");
            tvchange.setEnabled(mCurrency.getChg() >= 0);
//            mPanJia.setText(String.valueOf(currency.getClose()));
            mPanJia.setTextColor(mCurrency.getChg() >= 0 ? ContextCompat.getColor(MyApplication.getApp(), R.color.typeGreen) :
                    ContextCompat.getColor(MyApplication.getApp(), R.color.typeRed));
            if ("CNY".equals(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()))) {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mCurrency.getClose() * 1 * mCurrency.getBaseUsdRate(),
                        2, null) + "CNY"));
            } else {
                mPanMoney.setText(String.valueOf("≈" + WonderfulMathUtils.getRundNumber(mCurrency.getClose() * MainActivity.rate * mCurrency.getBaseUsdRate(),
                        2, null) + "CNY"));
            }
        }
    }

    private int check = 0;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.mTabOne: // 点击买入
                check = 0;
                mOneLayout.setVisibility(View.VISIBLE);
                mTwoLayout.setVisibility(View.GONE);
                mTabOne.setSelected(true);
                mTabTwo.setSelected(false);
                break;
            case R.id.mTabTwo: // 点击卖出
                check = 1;
                mOneLayout.setVisibility(View.GONE);
                mTwoLayout.setVisibility(View.VISIBLE);
                mTabOne.setSelected(false);
                mTabTwo.setSelected(true);
                break;
            default:
        }
    }

    @Override
    public void errorMes(int e, String msg) {

        //mTvThree.setVisibility(View.INVISIBLE);
        if (e == 4000) {
            MyApplication.getApp().setCurrentUser(null);
            if (!MyApplication.getApp().isLogin()) {
                btnLogin.setVisibility(View.VISIBLE);
                mThreeRecycler.setVisibility(View.GONE);
                mTvThree.setVisibility(View.GONE);
                mTwoYuE.setText("0.0");
                mOneYuE.setText("0.0");
                SharedPreferenceInstance.getInstance().saveaToken("");
                SharedPreferenceInstance.getInstance().saveTOKEN("");

            } else {
                if (MyApplication.getApp().isLogin() && this.mCurrency != null) {
                    currentPage = startPage;
                    historyPage = startPage;
                    mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), startPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
//                mPresenter.getOrderHistory(SharedPreferenceInstance.getInstance().getTOKEN(), startPage, pageSize, this.currency.getSymbol(), "", "", "", "");
                }
            }
        } else {
            WonderfulCodeUtils.checkedErrorCode(getmActivity(), e, msg);
        }

        //mTvThree.setVisibility(this.mHistoryData.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 提交委托后返回的信息(买入或者卖出成功)
     */
    @Override
    public void getDataLoad(int code, String message) {
        WonderfulToastUtils.showToast(getResources().getString(R.string.success));
        if (code == 0) {
//            setPriceNull();
            mOneTcpEdit.setText(null);
            mTwoTcpEdit.setText(null);
            // 提交委托成功
            if (MyApplication.getApp().isLogin() && this.mCurrency != null) {
                // 重新获取当前订单
                currentPage = startPage;
                historyPage = startPage;
                if (isCurrent) {
                    mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
                } else {
                    mPresenter.getOrderHistory(SharedPreferenceInstance.getInstance().getTOKEN(), historyPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
                }

                // 重新刷新下盘口信息  也许是不用的
                mPresenter.getExchange(mCurrency.getSymbol());
                Log.i("getExchange", "5" + mCurrency.getSymbol());
                // 应该还需要刷新下钱包的接口
                mPresenter.getWallet(1, SharedPreferenceInstance.getInstance().getTOKEN(), this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
                mPresenter.getWallet(2, SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()));
            }
        }
    }

    private LoadDialog mDialog;

    @Override
    public void showDialog() {
        if (mDialog == null) {
            mDialog = new LoadDialog(getmActivity());
        }
        mDialog.show();
    }

    @Override
    public void hideDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void setDefaultSymbol(String symbol) {
        for (Currency currency : currencies) {
            if (currency.getSymbol().equals(symbol)) {
                this.mCurrency = currency;
                break;
            }
        }
        if (mCurrency == null) {
            mCurrency = currencies.get(0);
        }
        if (tvTitle != null && TextUtils.isEmpty(tvTitle.getText()) && mCurrency != null) {
            resetSymbol(mCurrency);
            Log.i("tvTitle1", "setDefaultSymbol");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
    }


    /**
     * 盘口的数据
     */
    @Override
    public void getSuccess(List<Exchange> ask, List<Exchange> bid) {
        this.mOne.clear();
        this.mTow.clear();
        /*for (int i = 0; i < 5; i++) {
            mOne.add(new Exchange(5 - i, "--", "--"));
            mTow.add(new Exchange(i, "--", "--"));
        }
        if (ask.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                mOne.set(4 - i, new Exchange(i + 1, ask.get(i).getPrice(), ask.get(i).getAmount()));
            }
        } else {
            for (int i = 0; i < ask.size(); i++) {
                mOne.set(4 - i, new Exchange(i + 1, ask.get(i).getPrice(), ask.get(i).getAmount()));
            }
        }*/
        this.mOne.addAll(getPlateList(ask));
        mOneAdapter.setList(getDisplayPlateList(mOne, currentPlate, true));
        mOneAdapter.notifyDataSetChanged();
        /*if (bid.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                mTow.set(i, new Exchange(i, bid.get(i).getPrice(), bid.get(i).getAmount()));
            }
        } else {
            for (int i = 0; i < bid.size(); i++) {
                mTow.set(i, new Exchange(i, bid.get(i).getPrice(), bid.get(i).getAmount()));
            }
        }*/
        this.mTow.addAll(getPlateList(bid));
        mTwoAdapter.setList(getDisplayPlateList(mTow, currentPlate, false));
        mTwoAdapter.notifyDataSetChanged();
    }

    private List<Exchange> getPlateList(List<Exchange> list) {
        List<Exchange> result = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < list.size()) {
                result.add(list.get(i));
            } else {
                result.add(new Exchange(i, "--", "--"));
            }
        }
        return result;

    }


    private List<Exchange> getDisplayPlateList(List<Exchange> list, int plateType, boolean reverse) {
        List<Exchange> result = new ArrayList<>();
        int size = 0;
        switch (plateType) {
            case PLATE_DEFAULT:
                size = "CHECK_FULL_STOP".equals(priceType) ? 6 : 5;
                break;
            case PLATE_BUY:
                size = "CHECK_FULL_STOP".equals(priceType) ? 12 : 10;
                break;
            case PLATE_SELL:
                size = "CHECK_FULL_STOP".equals(priceType) ? 12 : 10;
                break;
            default:
        }
        for (int i = 0; i < size; i++) {
            result.add(list.get(i));
        }
        if (reverse) {
            Collections.reverse(result);
        }
        return result;
    }


    static int TYPE_CURRENT = 1;
    static int TYPE_HISTORY = 2;

    /**
     * 当前委托
     */
    @Override
    public void getDataLoaded(List<EntrustHistory> entrusts) {
        btnLogin.setVisibility(View.GONE);
        if (entrusts == null) {
            return;
        }
        setListData(entrusts);
        WonderfulLogUtils.logd("jiejie", "--当前委托啊--" + entrusts.size());
        //trustFragments.get(0).setListData(TrustFragment.TRUST_TYPE_CURRENT, entrusts);
    }

    /**
     * 历史委托
     */
    @Override
    public void getHistorySuccess(List<EntrustHistory> entrustHistories) {
        if (entrustHistories == null) {
            return;
        }
        setListData(entrustHistories);
        //trustFragments.get(1).setListData(TrustFragment.TRUST_TYPE_HISTORY, entrustHistories);
    }

    public void setListData(List list) {
        if (list != null && list.size() > 0) {
            mThreeRecycler.setVisibility(View.VISIBLE);
            mTvThree.setVisibility(View.GONE);
            if (isCurrent) {
                mCurrentData.clear();
                mCurrentData.addAll(list);
                mThreeRecycler.setAdapter(trustCurrentAdapter);
                trustCurrentAdapter.notifyDataSetChanged();
            } else {
                mHistoryData.clear();
                mHistoryData.addAll(list);
                mThreeRecycler.setAdapter(trustHistoryAdapter);
                trustHistoryAdapter.notifyDataSetChanged();
            }
        } else {
            if (isCurrent) {
                mCurrentData.clear();
                trustCurrentAdapter.notifyDataSetChanged();
            } else {
                mHistoryData.clear();
                trustHistoryAdapter.notifyDataSetChanged();
            }
            mThreeRecycler.setVisibility(View.GONE);
            mTvThree.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 取消某个委托成功
     */
    @Override
    public void getCancelSuccess(String success) {
        if (MyApplication.getApp().isLogin() && this.mCurrency != null) {
            // 重新获取当前订单
            currentPage = startPage;
            historyPage = startPage;
            mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
            // 应该还需要刷新下钱包的接口
            mPresenter.getWallet(1, SharedPreferenceInstance.getInstance().getTOKEN(), this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
            mPresenter.getWallet(2, SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()));
        }
    }

    /**
     * 获取钱包成功
     */
    @Override
    public void getWalletSuccess(Object object, int type) {
//        Log.d("jiejie", type + "");
        Money obj = (Money) object;
        switch (type) {
            case 1: // 可卖
                if (obj.getCode() == 0 && obj.getData() != null) {
                    if (mTwoYuE == null) {
                        return;
                    }
                    usdeBalance2 = Double.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 5, null));
                    mTwoYuE.setText(String.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 8, null) +
                            this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/"))));
                    maichu = String.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 8, null));
//                    text_two_jieshu.setText(mTwoYuE.getText().toString());
                } else {
                    mTwoYuE.setText(String.valueOf("0.0" +
                            this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/"))));
                    maichu = "0";
//                    text_two_jieshu.setText(mTwoYuE.getText().toString());
                }
                break;
            case 2: // 可用
                if (obj.getCode() == 0 && obj.getData() != null) {
//                    if ("USDT".equals(currency.getSymbol().substring(currency.getSymbol().indexOf("/") + 1, currency.getSymbol().length()))) {
//                        usdeBalance = obj.getData().getBalance();
//                    }
                    if (mOneYuE == null) {
                        return;
                    }
                    usdeBalance = Double.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 5, null));
                    mOneYuE.setText(String.valueOf(WonderfulMathUtils.getRundNumber(obj.getData().getBalance(), 8, null) +
                            mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                    WonderfulLogUtils.logi("miao", "mOneYuE1" + mOneYuE.getText().toString());
//                    text_one_jieshu.setText(mOneYuE.getText().toString());
                } else {
//                    mOneYuE.setText(String.valueOf("0.0" + this.currency.getSymbol().substring(0, currency.getSymbol().indexOf("/"))));
//                    text_one_jieshu.setText(mOneYuE.getText().toString());

                }
                break;
            case 3:
                if (mTwoYuE == null) {
                    return;
                }
                mTwoYuE.setText(String.valueOf("0.0" +
                        this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/"))));
                mOneYuE.setText(String.valueOf("0.0" + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())));
                WonderfulLogUtils.logi("miao", "mOneYuE3" + mOneYuE.getText().toString());
//                text_one_jieshu.setText(mOneYuE.getText().toString());
//                text_two_jieshu.setText(mTwoYuE.getText().toString());
//                SharedPreferenceInstance.getInstance().saveIsNeedShowLock(false);
//                SharedPreferenceInstance.getInstance().saveLockPwd("");
//                toLoginOrCenter();
//                Log.i("miao","断开登录");
                break;
            default:
        }
    }

    private double usdeBalance = -1;
    private double usdeBalance2 = -1;

    private void toLoginOrCenter() {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), LoginActivity.RETURN_LOGIN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_price_type_selector:
                final LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.text_type)));
                PopupUtils.showListBottom(getActivity(), linkedList, view, view.getMeasuredWidth(), new PopupUtils.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        tv_price_type.setText(linkedList.get(i));
                        if (i == 0) { // 限价
                            priceType = "LIMIT_PRICE";
                            mOneXian.setVisibility(View.VISIBLE);
                            mTwoXian.setVisibility(View.VISIBLE);
                            mTwoShi.setVisibility(View.GONE);
                            mOneShi.setVisibility(View.GONE);
                            mOneJiaoYi.setVisibility(View.VISIBLE);
                            mTwoJiaoYi.setVisibility(View.VISIBLE);
                            if (mCurrency != null) {
                                mOneTextType.setText(mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
                            }
                            mTwoTcpEdit.setHint(getResources().getString(R.string.text_number));
                            mOneTcpEdit.setHint(getResources().getString(R.string.text_number));
                            mOneBuy.setVisibility(View.VISIBLE);
                            mTwoBuy.setVisibility(View.VISIBLE);
                            ll_buy_section_touch.setVisibility(View.GONE);
                            ll_sell_section_touch.setVisibility(View.GONE);
                        } else if (i == 1) { // 市价
                            priceType = "MARKET_PRICE";
                            mOneXian.setVisibility(View.GONE);
                            mTwoXian.setVisibility(View.GONE);
                            mTwoShi.setVisibility(View.VISIBLE);
                            mOneShi.setVisibility(View.VISIBLE);
                            mOneJiaoYi.setVisibility(View.INVISIBLE);
                            mTwoJiaoYi.setVisibility(View.INVISIBLE);
                            if (mCurrency != null) {
                                mOneTextType.setText(mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()));
                            }
                            mTwoTcpEdit.setHint(getResources().getString(R.string.text_number));
                            mOneTcpEdit.setHint(getResources().getString(R.string.text_entrust));
                            mOneBuy.setVisibility(View.INVISIBLE);
                            mTwoBuy.setVisibility(View.INVISIBLE);
                            ll_buy_section_touch.setVisibility(View.GONE);
                            ll_sell_section_touch.setVisibility(View.GONE);
                        } else if (i == 2) { // 止盈止损
                            priceType = "CHECK_FULL_STOP";
                            ll_buy_section_touch.setVisibility(View.VISIBLE);
                            ll_sell_section_touch.setVisibility(View.VISIBLE);
                            mOneXian.setVisibility(View.VISIBLE);
                            mTwoXian.setVisibility(View.VISIBLE);
                            mTwoShi.setVisibility(View.GONE);
                            mOneShi.setVisibility(View.GONE);
                            mOneJiaoYi.setVisibility(View.VISIBLE);
                            mTwoJiaoYi.setVisibility(View.VISIBLE);
                            if (mCurrency != null) {
                                mOneTextType.setText(mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
                            }
                            mTwoTcpEdit.setHint(getResources().getString(R.string.text_number));
                            mOneTcpEdit.setHint(getResources().getString(R.string.text_number));
                            mOneBuy.setVisibility(View.VISIBLE);
                            mTwoBuy.setVisibility(View.VISIBLE);
                        }
                        mOneAdapter.setList(getDisplayPlateList(mOne, currentPlate, true));
                        mTwoAdapter.setList(getDisplayPlateList(mTow, currentPlate, false));
                        mOneAdapter.notifyDataSetChanged();
                        mTwoAdapter.notifyDataSetChanged();
                    }
                });

                break;
            case R.id.ivMenu:
                ArrayList<String> menuTexts = new ArrayList<String>() {{
                    add(getResources().getString(R.string.chargeMoney));
                    add(getResources().getString(R.string.transfer_asset));
                    add(getResources().getString(isFace ? R.string.text_delete_favorite : R.string.text_add_favorite));
                }};
                PopupUtils.showListBottomRight(getActivity(), menuTexts, ivMenu, 0, new PopupUtils.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        switch (position) {
                            case 0:
                                if (MyApplication.getApp().isLogin()) {
                                    ChooseCoinActivity.actionStart(getActivity(), ChooseCoinActivity.TYPE_CHARGE);
                                } else {
                                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
                                }
                                break;
                            case 1:
                                if (MyApplication.getApp().isLogin()) {
                                    AssetTransferActivity.actionStart(getActivity(), AssetTransferActivity.START_TRANSFER_TYPE_MARGIN, mCurrency.getSymbol());
                                } else {
                                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
                                }
                                break;
                            case 2:
                                if (MyApplication.getApp().isLogin()) {
                                    MainActivity.isAgain = true;
                                    if (isFace) { // 已经收藏 则删除
                                        delete(SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol());
                                    } else {
                                        getCollect(SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol());
                                    }
                                } else {
                                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
                                }
                                break;
                            default:
                        }
                    }
                });
                break;
            case R.id.ll_current_trust:
                setCurrentSelected();
                if (MyApplication.getApp().isLogin()) {
                    btnLogin.setVisibility(View.GONE);
                    mThreeRecycler.setVisibility(View.VISIBLE);
                    currentPage = startPage;
                } else {
                    mThreeRecycler.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                    mTwoYuE.setText("0.0");
                    mOneYuE.setText("0.0");
                    SharedPreferenceInstance.getInstance().saveaToken("");
                    SharedPreferenceInstance.getInstance().saveTOKEN("");
                }
                break;
            case R.id.ll_history_trust:
                setHistorySelected();
                if (MyApplication.getApp().isLogin()) {
                    btnLogin.setVisibility(View.GONE);
                    mThreeRecycler.setVisibility(View.VISIBLE);
                    historyPage = startPage;
                } else {
                    mThreeRecycler.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                    mTwoYuE.setText("0.0");
                    mOneYuE.setText("0.0");
                    SharedPreferenceInstance.getInstance().saveaToken("");
                    SharedPreferenceInstance.getInstance().saveTOKEN("");
                }
                break;
            case R.id.ivKline:
                if (this.mCurrency != null) {
                    KlineActivity.actionStart(getActivity(), this.mCurrency.getSymbol());
                }
                break;
            case R.id.btn_toLogin: // 点击登录
                toLoginOrCenter();
                break;
            case R.id.mOneAdd: // +买入 价格
                try {
                    double num = Double.valueOf(mOnePriceEdit.getText().toString());
                    mOnePriceEdit.setText(String.valueOf(new DecimalFormat("#0.000").format(num + 0.01)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mOneSum: // -买入 价格
                try {
                    double num1 = Double.valueOf(mOnePriceEdit.getText().toString());
                    if ((num1 - 0.01) > 0) {
                        mOnePriceEdit.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 - 0.01)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mOneAddTouch: // + 触发价  买入
                try {
                    double num = Double.valueOf(mOnePriceTouch.getText().toString());
                    mOnePriceTouch.setText(String.valueOf(new DecimalFormat("#0.000").format(num + 0.01)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mOneSubTouch: // - 触发价  买入
                try {
                    double num1 = Double.valueOf(mOnePriceTouch.getText().toString());
                    if ((num1 - 0.01) > 0) {
                        mOnePriceTouch.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 - 0.01)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.mTwoAdd: // +  价格  卖出
                try {
                    double num1 = Double.valueOf(mTwoPriceEdit.getText().toString());
                    mTwoPriceEdit.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 + 0.01)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mTwoSub: // _价格  卖出
                try {
                    double num1 = Double.valueOf(mTwoPriceEdit.getText().toString());
                    if ((num1 - 0.01) > 0) {
                        mTwoPriceEdit.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 - 0.01)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.mTwoAddTouch: // +触发价  卖出
                try {
                    double num1 = Double.valueOf(mTwoPriceTouch.getText().toString());
                    mTwoPriceTouch.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 + 0.01)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mTwoSubTouch: // - 触发价  卖出
                try {
                    double num1 = Double.valueOf(mTwoPriceTouch.getText().toString());
                    if ((num1 - 0.01) > 0) {
                        mTwoPriceTouch.setText(String.valueOf(new DecimalFormat("#0.000").format(num1 - 0.01)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.text_to_all: // 查看更多委托
                if (MyApplication.getApp().isLogin()) {
                    TrustListActivity.show(getActivity(), false);
                } else {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
                }
                break;
            case R.id.btnOneBuy: // 买入
                Buy();
                break;
            case R.id.btnTwoPost: // 卖出
                Sale();
                break;
            default:
        }
    }

    boolean isCurrent = true;

    private void setHistorySelected() {
        if (MyApplication.getApp().isLogin()) {
            mTvThree.setVisibility(View.GONE);
            isCurrent = false;
            tvCurrentTrust.setSelected(false);
            tvHistoryTrust.setSelected(true);
            currentTrustUnderline.setVisibility(View.GONE);
            historyTrustUnderline.setVisibility(View.VISIBLE);
            historyPage = startPage;
            if (mCurrency == null) {
                return;
            }
            mPresenter.getOrderHistory(SharedPreferenceInstance.getInstance().getTOKEN(), historyPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
        } else {
//            WonderfulToastUtils.showToast("请去登录");
//            MyApplication.getApp().loginAgain(getmActivity());
            mTwoYuE.setText("0.0");
            mOneYuE.setText("0.0");
            SharedPreferenceInstance.getInstance().saveaToken("");
            SharedPreferenceInstance.getInstance().saveTOKEN("");
        }

    }

    private void setCurrentSelected() {
        if (MyApplication.getApp().isLogin()) {
            mTvThree.setVisibility(View.GONE);
            isCurrent = true;
            tvCurrentTrust.setSelected(true);
            tvHistoryTrust.setSelected(false);
            currentTrustUnderline.setVisibility(View.VISIBLE);
            historyTrustUnderline.setVisibility(View.GONE);
            currentPage = startPage;
            if (mCurrency == null) {
                return;
            }
            mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
        } else {
//            WonderfulToastUtils.showToast("请去登录");
//            MyApplication.getApp().loginAgain(getmActivity());
            mTwoYuE.setText("0.0");
            mOneYuE.setText("0.0");
            SharedPreferenceInstance.getInstance().saveaToken("");
            SharedPreferenceInstance.getInstance().saveTOKEN("");
        }

    }

    private String price; // 价格
    private String touchPrice;//触发价
    private String amout; // 数量
    private String priceType = "LIMIT_PRICE";

    private void Buy() {
        if (mCurrency == null) {
            return;
        }
        if (!MyApplication.getApp().isLogin()) {
            mTwoYuE.setText("0.0");
            mOneYuE.setText("0.0");
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            return;
        }
        if (MyApplication.realVerified != 1) {//1是已实名认证
            WonderfulToastUtils.showToast(getResources().getString(R.string.realname));
            return;
        }
        BBConfirmDialogFragment fragment = null;
        switch (priceType) {
            case "LIMIT_PRICE":
                touchPrice = "";
                price = mOnePriceEdit.getText().toString();
                amout = mOneTcpEdit.getText().toString();
                if (WonderfulStringUtils.isEmpty(price, amout)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance(new BigDecimal(price).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())
                        , new BigDecimal(amout).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")),
                        String.valueOf(mOneDeal.getText().toString()), "BUY", priceType, "");
                break;
            case "MARKET_PRICE":
                price = "0.0";
                touchPrice = "";
                amout = mOneTcpEdit.getText().toString();
                if (WonderfulStringUtils.isEmpty(price, amout)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance("最优价格", "--" + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/"))
                        , new BigDecimal(amout).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1,
                                mCurrency.getSymbol().length()), "BUY", priceType, "");
                break;
            case "CHECK_FULL_STOP":
                price = mOnePriceEdit.getText().toString();
                amout = mOneTcpEdit.getText().toString();
                touchPrice = mOnePriceTouch.getText().toString();
                if (WonderfulStringUtils.isEmpty(touchPrice, price, amout)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance(new BigDecimal(price).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length())
                        , new BigDecimal(amout).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")),
                        String.valueOf(mOneDeal.getText().toString()), "BUY", priceType, touchPrice);
                break;
            default:
        }
        //if (mPresenter != null) mPresenter.getAddOrder(getmActivity().getToken(), currency.getSymbol(), price, amout, "BUY", type);
        fragment.show(getFragmentManager(), "bb_exchange");
        fragment.setTargetFragment(this, Integer.MAX_VALUE);
        fragment.setCallback(new BBConfirmDialogFragment.OperateCallback() {
            @Override
            public void confirm() {
                // 买入
                if (mPresenter != null) {
                    mPresenter.getAddOrder(SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol(), price, amout, "BUY", priceType, iv_check_box_one.isSelected() ? "1" : "0", touchPrice);
                }
            }
        });
    }

    private void Sale() {
        if (mCurrency == null) {
            return;
        }
        if (!MyApplication.getApp().isLogin()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.text_xian_login));
            mTwoYuE.setText("0.0");
            mOneYuE.setText("0.0");
            return;
        }
        if (MyApplication.realVerified != 1) {//1是已实名认证
            WonderfulToastUtils.showToast(getResources().getString(R.string.realname));
            return;
        }
        BBConfirmDialogFragment fragment = null;
        switch (priceType) {
            case "LIMIT_PRICE":
                touchPrice = "";
                price = mTwoPriceEdit.getText().toString();
                amout = mTwoTcpEdit.getText().toString();
                if (WonderfulStringUtils.isEmpty(price, amout)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance(price + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()),
                        amout + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")),
                        String.valueOf(mTwoDeal.getText().toString()), "SELL", priceType, "");
                break;
            case "MARKET_PRICE":
                price = "0.0";
                touchPrice = "";
                amout = mTwoTcpEdit.getText().toString();
                if (WonderfulStringUtils.isEmpty(price, amout)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance(getResources().getString(R.string.text_best_prices), amout + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")),
                        "--" + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()), "SELL", priceType, "");
                break;
            case "CHECK_FULL_STOP":
                touchPrice = mTwoPriceTouch.getText().toString();
                price = mTwoPriceEdit.getText().toString();
                amout = mTwoTcpEdit.getText().toString();
                if (WonderfulStringUtils.isEmpty(price, amout, touchPrice)) {
                    WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
                    return;
                }
                fragment = BBConfirmDialogFragment.getInstance(price + mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()),
                        amout + this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")),
                        String.valueOf(mTwoDeal.getText().toString()), "SELL", priceType, touchPrice);
                break;
            default:
        }
        // 卖出
        fragment.show(getActivity().getSupportFragmentManager(), "bb_exchange");
        fragment.setTargetFragment(this, Integer.MAX_VALUE);
        fragment.setCallback(new BBConfirmDialogFragment.OperateCallback() {
            @Override
            public void confirm() {
                // 卖出
                if (mPresenter != null) {
                    mPresenter.getAddOrder(SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol(), price, amout, "SELL", priceType, iv_check_box_two.isSelected() ? "1" : "0", touchPrice);
                }
            }
        });
    }

    /**
     * 盘口信息的返回 和 当前委托的返回
     *
     * @param response SocketResponse
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(SocketResponse response) {
        if (response.getCmd() == null) {
            return;
        }
        switch (response.getCmd()) {
            case PUSH_EXCHANGE_TRADE:
                try {
                    setResponse(response.getResponse());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PUSH_EXCHANGE_ORDER_CANCELED:
            case PUSH_EXCHANGE_ORDER_COMPLETED:
            case PUSH_EXCHANGE_ORDER_TRADE:
                if (MyApplication.getApp().isLogin() && this.mCurrency != null) {
                    currentPage = startPage;
                    historyPage = startPage;
                    if (isCurrent) {
                        mPresenter.getCurrentOrder(SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
                    } else {
                        mPresenter.getOrderHistory(SharedPreferenceInstance.getInstance().getTOKEN(), historyPage, pageSize, this.mCurrency.getSymbol(), "", "", "", "");
                    }
                    mPresenter.getWallet(1, SharedPreferenceInstance.getInstance().getTOKEN(), this.mCurrency.getSymbol().substring(0, mCurrency.getSymbol().indexOf("/")));
                    mPresenter.getWallet(2, SharedPreferenceInstance.getInstance().getTOKEN(), mCurrency.getSymbol().substring(mCurrency.getSymbol().indexOf("/") + 1, mCurrency.getSymbol().length()));

                }
                break;
            default:
                break;
        }
    }

    /**
     * 更新盘口买卖数据列表
     */
    private void setResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        TextItems items = new Gson().fromJson(response, TextItems.class);
        String symbol = mCurrency.getSymbol();
        if (!symbol.equals(items.getSymbol())) {
            return;
        }
        if ("SELL".equals(items.getDirection())) { // 卖
            this.mOne.clear();
           /* for (int i = 0; i < 5; i++) {
                mOne.add(new Exchange(5 - i, "--", "--"));
            }
            List<Exchange> ask = items.getItems();
            if (ask.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    mOne.set(4 - i, new Exchange(i + 1, ask.get(i).getPrice(), ask.get(i).getAmount()));
                }
            } else {
                for (int i = 0; i < ask.size(); i++) {
                    mOne.set(4 - i, new Exchange(i + 1, ask.get(i).getPrice(), ask.get(i).getAmount()));
                }
            }*/
            this.mOne.addAll(getPlateList(items.getItems()));
            mOneAdapter.setList(getDisplayPlateList(mOne, currentPlate, true));
            mOneAdapter.notifyDataSetChanged();
        } else { // 买
            this.mTow.clear();
           /* for (int i = 0; i < 5; i++) {
                mTow.add(new Exchange(5 - i, "--", "--"));
            }
            List<Exchange> bid = items.getItems();
            if (bid.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    mTow.set(i, new Exchange(i, bid.get(i).getPrice(), bid.get(i).getAmount()));
                }
            } else {
                for (int i = 0; i < bid.size(); i++) {
                    mTow.set(i, new Exchange(i, bid.get(i).getPrice(), bid.get(i).getAmount()));
                }
            }*/
            this.mTow.addAll(getPlateList(items.getItems()));
            mTwoAdapter.setList(getDisplayPlateList(mTow, currentPlate, false));
            mTwoAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void setPresenter(Coin2CoinContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public void asyncCollect(String symbol, boolean isFace) {
        if (symbol.equals(mCurrency.getSymbol())) {
            this.isFace = isFace;
        }
    }

    @BindView(R.id.recyclerSell)
    RecyclerView recyclerSell; // 卖出的
    @BindView(R.id.recyclerBuy)
    RecyclerView recyclerBuy; //买进

    final int PLATE_DEFAULT = 0;
    final int PLATE_BUY = 1;
    final int PLATE_SELL = 2;

    int currentPlate = PLATE_DEFAULT;

    @OnClick(R.id.iv_switch_plate)
    public void switchPlate() {
        final ArrayList<String> list = new ArrayList<String>() {{
            add(getResources().getString(R.string.plate_default));
            add(getResources().getString(R.string.plate_buy));
            add(getResources().getString(R.string.plate_sell));
        }};
        BottomSelectionFragment bottomSelectionFragment = BottomSelectionFragment.getInstance(list);
        bottomSelectionFragment.show(getActivity().getSupportFragmentManager(), "BottomSelectionFragment");
        bottomSelectionFragment.setOnItemSelectListener(new BottomSelectionFragment.OnItemSelectListener() {
            @Override
            public void onItemSelected(int position) {
                setPlateByType(position);
            }
        });
    }

    private void setPlateByType(int position) {
        currentPlate = position;
        switch (position) {
            case PLATE_DEFAULT:
                recyclerSell.setVisibility(View.VISIBLE);
                recyclerBuy.setVisibility(View.VISIBLE);
                mOneAdapter.setList(getDisplayPlateList(mOne, currentPlate, true));
                mOneAdapter.notifyDataSetChanged();
                mTwoAdapter.setList(getDisplayPlateList(mTow, currentPlate, false));
                mTwoAdapter.notifyDataSetChanged();
                break;
            case PLATE_BUY:
                recyclerSell.setVisibility(View.GONE);
                recyclerBuy.setVisibility(View.VISIBLE);
                mTwoAdapter.setList(getDisplayPlateList(mTow, currentPlate, false));
                mTwoAdapter.notifyDataSetChanged();
                break;
            case PLATE_SELL:
                recyclerSell.setVisibility(View.VISIBLE);
                recyclerBuy.setVisibility(View.GONE);
                mOneAdapter.setList(getDisplayPlateList(mOne, currentPlate, true));
                mOneAdapter.notifyDataSetChanged();
                break;
            default:
        }
    }


}
