package top.biduo.exchange.ui.entrust;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import top.biduo.exchange.R;
import top.biduo.exchange.adapter.TrustAdapterNewHistory;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.DropdownLayout;
import top.biduo.exchange.ui.dialog.EntrustOperateDialogFragment;
import top.biduo.exchange.entity.Currency;
import top.biduo.exchange.entity.EntrustHistory;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import org.angmarch.views.NiceSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 当前委托和历史委托的界面-币币交易
 */
public class TrustListActivity extends BaseActivity implements View.OnClickListener, ITrustContract.View {

    private TextView emptyMessage;

    public static void show(Activity activity, boolean isMargin) {
        Intent intent = new Intent(activity, TrustListActivity.class);
        intent.putExtra("isMargin", isMargin);
        activity.startActivity(intent);
    }

    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.ibBack)
    ImageView ibBack;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rvAds)
    RecyclerView rvAds;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.tv_title_current_trust)
    TextView tv_title_current_trust;
    @BindView(R.id.tv_title_history_trust)
    TextView tv_title_history_trust;
    @BindView(R.id.current_trust_underline)
    View current_trust_underline;
    @BindView(R.id.history_trust_underline)
    View history_trust_underline;
    @BindView(R.id.dropdown_layout)
    DropdownLayout dropdown_layout;
    @BindView(R.id.sp_symbol)
    NiceSpinner sp_symbol;
    @BindView(R.id.sp_type)
    NiceSpinner sp_type;
    @BindView(R.id.sp_direction)
    NiceSpinner sp_direction;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_reset)
    TextView tv_reset;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    private List<EntrustHistory> entrusts = new ArrayList<>();
    TrustAdapterNewHistory adapter;
    private List<EntrustHistory> entrustHistories = new ArrayList<>();
    private int currentPage = 1;
    private int historyPage = 1;
    private ITrustContract.Presenter mPresenter;
    private String symbol = "";
    String startTime = "";
    String endTime = "";
    String type = "";
    String direction = "";
    boolean isCurrent = true;
    @BindView(R.id.line_2)
    LinearLayout line_2;
    @BindView(R.id.view_zhe)
    View view_zhe;
    int pageSize = 20;
    boolean isMargin = false;
    @BindView(R.id.tb_trust_title)
    TabLayout tb_trust_title;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_current_trust;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new TrustPresentImpl(Injection.provideTasksRepository(TrustListActivity.this), this);
        isMargin = getIntent().getBooleanExtra("isMargin", false);
        ibBack.setOnClickListener(this);
        iv_filter.setOnClickListener(this);
        tv_title_current_trust.setOnClickListener(this);
        tv_title_history_trust.setOnClickListener(this);

        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initSpinners();
        rvAds.setLayoutManager(new LinearLayoutManager(this));
        initRecycler();
//        initViewPager();
        iv_filter.post(new Runnable() {
            @Override
            public void run() {
                setCurrentSelected();
            }
        });
        line_2.setVisibility(View.GONE);
        view_zhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_layout.hide();
                line_2.setVisibility(View.GONE);
                Drawable drawable = getResources().getDrawable(R.drawable.icon_filter_no);
                iv_filter.setBackgroundDrawable(drawable);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isCurrent) {
                    currentPage = 1;
                    WonderfulLogUtils.logi("miao", "2333");
                    mPresenter.getCurrentOrder(isMargin, getToken(), currentPage, pageSize, symbol, type, direction, startTime, endTime);
                    if (adapter != null) {
                        adapter.setEnableLoadMore(false);
                        adapter.loadMoreEnd(false);
                    }
                } else {
                    historyPage = 1;
                    mPresenter.getOrderHistory(isMargin, getToken(), historyPage, pageSize, symbol, type, direction, startTime, endTime);
                    if (adapter != null) {
                        adapter.setEnableLoadMore(false);
                        adapter.loadMoreEnd(false);
                    }
                }
            }
        });
        initTable();

    }



    private void initRecycler() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_no_order, null);
        emptyMessage = emptyView.findViewById(R.id.tvMessage);
        emptyMessage.setText(getText(isCurrent ? R.string.no_data_current_entrust : R.string.no_data_history_entrust));
        adapter = isCurrent ? new TrustAdapterNewHistory(entrusts, true) : new TrustAdapterNewHistory(entrustHistories, false);
        adapter.bindToRecyclerView(rvAds);
        adapter.setEnableLoadMore(false);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvAds);
        adapter.setEmptyView(emptyView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if ("COMPLETED".equals(entrustHistories.get(position).getStatus())) {
                    TrustDetailActivity.show(TrustListActivity.this, entrustHistories.get(position).getSymbol(), entrustHistories.get(position));
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (isCurrent) {
                    showBottomFragment((EntrustHistory) adapter.getData().get(position));
                }
            }
        });
    }

    private void loadMore() {
        refreshLayout.setEnabled(false);
        if (isCurrent) {
            currentPage = currentPage + 1;
            mPresenter.getCurrentOrder(isMargin, getToken(), currentPage, pageSize, symbol, type, direction, startTime, endTime);
        } else {
            historyPage = historyPage + 1;
            mPresenter.getOrderHistory(isMargin, getToken(), historyPage, pageSize, symbol, type, direction, startTime, endTime);
        }
    }

    private void initSpinners() {
        //类型选择（市价-MARKET_PRICE，限价-LIMIT_PRICE，止盈止损-CHECK_FULL_STOP）
        line_2.setVisibility(View.VISIBLE);
        sp_type.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.text_type_array))));
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = "";
                    sp_type.setText("");
                } else {
                    type = getResources().getStringArray(R.array.text_type_param)[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //方向选择（买0，卖1）
        sp_direction.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.text_direction))));
        sp_direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    direction = "";
                    sp_direction.setText("");
                } else {
                    direction = (position - 1) + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> currencyNames = getAllCurrencyName(MyApplication.list);
        //交易对选择
        sp_symbol.attachDataSource(currencyNames);
        sp_symbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    symbol = "";
                    sp_symbol.setText("");
                } else {
                    symbol = currencyNames.get(position);
                }

                Log.i("sp_symbol", symbol + "--" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_type.setText("");
        sp_direction.setText("");
        sp_symbol.setText("");


    }

    private List<String> getAllCurrencyName(List<Currency> list) {
        List<String> names = new ArrayList<>();
        names.add("取消选择");
        for (Currency currency : list) {
            names.add(currency.getSymbol());
        }
        return names;
    }

    @Override
    protected void obtainData() {
//        mPresenter.getCurrentOrder(getToken(), currentPage, 10, symbol, type, direction, startTime, endTime);
//        Log.i("params", symbol + "" + type + "--" + direction + "--" + startTime + "--" + endTime);
//        mPresenter.getOrderHistory(getToken(), historyPage, 10, symbol, type, direction, startTime, endTime);
    }

    private void showBottomFragment(EntrustHistory entrust) {
        EntrustOperateDialogFragment entrustOperateFragment =
                EntrustOperateDialogFragment.getInstance(entrust);
        entrustOperateFragment.show(getSupportFragmentManager(), "bottom");
        entrustOperateFragment.setCallback(new EntrustOperateDialogFragment.OperateCallback() {
            @Override
            public void cancleOrder(String orderId) {
                // 撤消
                if (mPresenter != null) {
                    mPresenter.getCancelEntrust(isMargin, getToken(), orderId);
                }
            }
        });
    }

    @Override
    protected void fillWidget() {

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
    protected void loadData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.iv_filter://展开/收起筛选窗口
                if (dropdown_layout.isOpen()) {
                    dropdown_layout.hide();
                    line_2.setVisibility(View.GONE);
//                    Drawable drawable = getResources().getDrawable(R.drawable.icon_filter_no);
//                    iv_filter.setImageDrawable(drawable);
                } else {
                    dropdown_layout.show();
                    line_2.setVisibility(View.VISIBLE);
//                    Drawable drawable = getResources().getDrawable(R.drawable.icon_filter_orange);
//                    iv_filter.setImageDrawable(drawable);
                }
                break;
            case R.id.tv_reset://清空筛选条件
                clearFilters();
                break;
            case R.id.tv_confirm://确认筛选条件
                Log.i("params", symbol + "" + type + "--" + direction + "--" + startTime + "--" + endTime);
                if (isCurrent) {
                    WonderfulLogUtils.logi("当前委托", "999");
                    mPresenter.getCurrentOrder(isMargin, SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, symbol, type, direction, startTime, endTime);
                } else {
                    mPresenter.getOrderHistory(isMargin, SharedPreferenceInstance.getInstance().getTOKEN(), historyPage, pageSize, symbol, type, direction, startTime, endTime);
                }
                dropdown_layout.hide();
                line_2.setVisibility(View.GONE);

                break;
            case R.id.tv_title_current_trust:
                setCurrentSelected();
                break;
            case R.id.tv_title_history_trust:
                setHistorySelected();
                break;
            case R.id.tv_start_time:
                showTimePickerView(tv_start_time, true);
                break;
            case R.id.tv_end_time:
                showTimePickerView(tv_end_time, false);
                break;
            default:
        }
    }

    private void setHistorySelected() {
        isCurrent = false;
        historyPage = 1;
        entrustHistories.clear();
        initRecycler();
        tv_title_current_trust.setSelected(false);
        current_trust_underline.setVisibility(View.GONE);
        tv_title_history_trust.setSelected(true);
        history_trust_underline.setVisibility(View.VISIBLE);
        mPresenter.getOrderHistory(isMargin, SharedPreferenceInstance.getInstance().getTOKEN(), historyPage, pageSize, "", "", "", "", "");
    }

    private void setCurrentSelected() {
        currentPage = 1;
        isCurrent = true;
        entrusts.clear();
        initRecycler();
        tv_title_current_trust.setSelected(true);
        current_trust_underline.setVisibility(View.VISIBLE);
        tv_title_history_trust.setSelected(false);
        history_trust_underline.setVisibility(View.GONE);
        WonderfulLogUtils.logi("miao", "444");
        mPresenter.getCurrentOrder(isMargin, SharedPreferenceInstance.getInstance().getTOKEN(), currentPage, pageSize, "", "", "", "", "");
    }

    public void showTimePickerView(final TextView tvTime, final boolean isStart) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, 5, 1, 0, 0, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(System.currentTimeMillis());
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00");
                tvTime.setText(df.format(date));
                if (isStart) {
                    startTime = WonderfulStringUtils.dateToLong(date) + "";
                } else {
                    endTime = WonderfulStringUtils.dateToLong(date) + "";
                }
            }
        }).setType(new boolean[]{true, true, true, true, false, false})
                .setCancelText(getResources().getString(R.string.cancle))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.confirm))//确认按钮文字
                .setSubmitColor(Color.parseColor("#0b0b0b"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#787878"))//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定

                .build();
        pvTime.show();
    }

    private void clearFilters() {
        symbol = "";
        type = "";
        direction = "";
        startTime = "";
        endTime = "";
        sp_symbol.setText("");
        sp_direction.setText("");
        sp_type.setText("");
        tv_start_time.setText("");
        tv_end_time.setText("");
    }

    @Override
    public void errorMes(int e, String meg) {
        hideLoadingPopup();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);

    }

    @Override
    public void onDataNotAvailable(int code, String message) {
        hideLoadingPopup();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        WonderfulCodeUtils.checkedErrorCode(this, code, message);
    }

    @Override
    public void getHistorySuccess(List<EntrustHistory> entrustHistories) {
        hideLoadingPopup();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        setListData(entrustHistories);
        adapter.loadMoreComplete();
    }

    public void setListData(List list) {
        if (list != null && list.size() > 0) {
            Log.i("size", list.size() + "," + entrusts.size() + "," + entrustHistories.size());
            refreshLayout.setVisibility(View.VISIBLE);
            rvAds.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            if (isCurrent) {
                if (currentPage == 1) {
                    entrusts.clear();
                }
                entrusts.addAll(list);
            } else {
                if (historyPage == 1) {
                    entrustHistories.clear();
                }
                entrustHistories.addAll(list);
            }
            adapter.notifyDataSetChanged();
            adapter.setEnableLoadMore(list.size() == pageSize);
        }
    }

    /**
     * 取消委托成功的返回
     */
    @Override
    public void getCancelSuccess(String success) {
        WonderfulToastUtils.showToast(getResources().getString(R.string.text_cancel_success));
        setCurrentSelected();
    }

    @Override
    public void getCurrentSuccess(List<EntrustHistory> entrust) {
        hideLoadingPopup();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);

        setListData(entrust);

    }

    @Override
    public void setPresenter(ITrustContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    private void initTable() {
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.entrust_title)));
        setupTabViews(linkedList);
        tb_trust_title.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    setCurrentSelected();
                }else{
                    setHistorySelected();
                }
                changeTabSelect(tab);   //Tab获取焦点
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);   //Tab失去焦点
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tb_trust_title.getTabAt(0).select();

    }

    /**
     * 设置每个TabLayout的View
     */
    private void setupTabViews(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            tb_trust_title.addTab(tb_trust_title.newTab().setCustomView(getTabView(titles, i)));
        }
    }

    /**
     * 提供TabLayout的View
     * 根据index返回不同的View
     * 主意：默认选中的View要返回选中状态的样式
     */
    private View getTabView(List<String> titles, int index) {
        //自定义View布局
        View view = LayoutInflater.from(this).inflate(R.layout.textview_pop, null);
        TextView title = (TextView) view.findViewById(R.id.tv_text);
        title.setText(titles.get(index));
        if (index ==0) {
            view.setAlpha(1f);
            view.setScaleX(1.3f);
            view.setScaleY(1.3f);
        } else {
            view.setScaleX(1f);
            view.setScaleY(1f);
            view.setAlpha(0.5f);
        }
        return view;
    }


    /**
     * 改变TabLayout的View到选中状态
     * 使用属性动画改编Tab中View的状态
     */
    private void changeTabSelect(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(view, "scaleX", 1.0F, 1.3F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
                view.setAlpha(1f);
            }
        });
    }

    /**
     * 改变TabLayout的View到未选中状态
     */
    private void changeTabNormal(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(view, "scaleX", 1.3F, 1.0F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setScaleX(cVal);
                view.setScaleY(cVal);
                view.setAlpha(0.5f);
            }
        });
    }
}
