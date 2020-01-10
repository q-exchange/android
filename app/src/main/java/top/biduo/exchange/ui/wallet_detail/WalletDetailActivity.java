package top.biduo.exchange.ui.wallet_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import top.biduo.exchange.R;
import top.biduo.exchange.customview.DropdownLayout;
import top.biduo.exchange.adapter.WalletDetailAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.TransactionTypeBean;
import top.biduo.exchange.entity.WalletDetailNew;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;
import okhttp3.Request;

import static top.biduo.exchange.app.GlobalConstant.OKHTTP_ERROR;

public class WalletDetailActivity extends BaseActivity implements WalletDetailContract.View, View.OnClickListener {

    @BindView(R.id.ibBack)
    ImageView ibBack;
    @BindView(R.id.llTitle)
    RelativeLayout llTitle;
    @BindView(R.id.rvDetail)
    RecyclerView rvDetail;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.dropdown_layout)
    DropdownLayout dropdownLayout;
    @BindView(R.id.sp_type)
    NiceSpinner sp_type;
    @BindView(R.id.sp_symbol)
    NiceSpinner sp_symbol;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.view_zhe)
    View view_zhe;

    private List<WalletDetailNew.ContentBean> walletDetails = new ArrayList<>();
    private WalletDetailAdapter adapter;
    private int pageNo = 1;
    private int limit = 20;
    private WalletDetailContract.Presenter presenter;
    String startTime = "";
    String endTime = "";
    String symbol = "";
    String type = "";
    private List<Coin> coins = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WalletDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new WalletDetailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        ivFilter.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        view_zhe.setOnClickListener(this);

    }

    private void initSpinner() {
        if (coins != null && coins.size() > 0) {
            final List<String> symbols = getAllSymbol();
            sp_symbol.attachDataSource(symbols);
            sp_symbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
//                    symbol = "";
                        sp_symbol.setText("");
                    } else {
//                    symbol = sp_symbol.getText().toString().trim();
                        WonderfulLogUtils.logd("params", sp_symbol.getText().toString().trim() + "---77--");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        clearFilters();
    }

    private List<String> getAllSymbol() {
        ArrayList<String> symbols = new ArrayList<>();
        if (coins != null && coins.size() > 0) {
            symbols.add("暂不选择");
            for (Coin currency : coins) {
                symbols.add(currency.getCoin().getUnit());
            }
        }
        return symbols;
    }

    private void refresh() {
        pageNo = 1;
        presenter.allTransaction(SharedPreferenceInstance.getInstance().getTOKEN(), pageNo, limit, SharedPreferenceInstance.getInstance().getID(), startTime, endTime, symbol, type);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreEnd(false);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        initRvDetail();
    }

    private void initRvDetail() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(manager);
        adapter = new WalletDetailAdapter(R.layout.adapter_assets_transaction, walletDetails, typeBeans);
        View emptyView = getLayoutInflater().inflate(R.layout.empty_no_order, null);
        ((TextView) emptyView.findViewById(R.id.tvMessage)).setText(getResources().getString(R.string.detailTableViewTip));
        adapter.bindToRecyclerView(rvDetail);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rvDetail);
        adapter.setEmptyView(emptyView);
        ivFilter.setOnClickListener(this);
    }

    private void loadMore() {
        refreshLayout.setEnabled(false);
        pageNo++;
        WonderfulLogUtils.logi("miao", SharedPreferenceInstance.getInstance().getTOKEN() + "----" + pageNo + "-----" + limit + "-------" + SharedPreferenceInstance.getInstance().getID() + "-----" + startTime + "-----" + endTime + "------" + symbol + "-------" + type + "");
        presenter.allTransaction(SharedPreferenceInstance.getInstance().getTOKEN(), pageNo, limit, SharedPreferenceInstance.getInstance().getID(), startTime, endTime, symbol, type);
    }

    @Override
    protected void loadData() {
        jiaoyidui();
        WonderfulLogUtils.logi("miao", SharedPreferenceInstance.getInstance().getTOKEN() + "----" + pageNo + "-----" + limit + "-------" + SharedPreferenceInstance.getInstance().getID() + "-----" + startTime + "-----" + endTime + "------" + symbol + "-------" + type + "");
        getTransactionType();
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
    public void setPresenter(WalletDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void allTransactionSuccess(WalletDetailNew walletDetailNew) {
        if (refreshLayout == null) {
            return;
        }
        view_zhe.setVisibility(View.GONE);
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        if (walletDetailNew == null) {
            return;
        }
        if (pageNo == 1) {
            this.walletDetails.clear();
        } else if (walletDetailNew.getContent().size() <limit) {
            adapter.loadMoreEnd();
        }
        this.walletDetails.addAll(walletDetailNew.getContent());
        adapter.notifyDataSetChanged();
        //adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void allTransactionFail(Integer code, String toastMessage) {
        if (refreshLayout == null) {
            return;
        }
        adapter.setEnableLoadMore(true);
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_filter:
                if (!dropdownLayout.isOpen()) {
                    dropdownLayout.show();
                    view_zhe.setVisibility(View.VISIBLE);
                } else {
                    dropdownLayout.hide();
                    view_zhe.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_start_time:
                showTimePickerView(tv_start_time, true);
                break;
            case R.id.tv_end_time:
                showTimePickerView(tv_end_time, false);
                break;
            case R.id.tv_reset://清空筛选条件
                clearFilters();
                break;
            case R.id.tv_confirm://确认筛选条件
                WonderfulLogUtils.logd("params", symbol + "" + type + "--" + startTime + "--" + endTime);
                symbol = sp_symbol.getText().toString().trim();
                if (symbol.equals("暂不选择")) {
                    symbol = "";
                }
                if (checkFilter()) {
                    pageNo = 1;
                    presenter.allTransaction(SharedPreferenceInstance.getInstance().getTOKEN(), pageNo, 10, SharedPreferenceInstance.getInstance().getID(), startTime, endTime, symbol, type);
                    dropdownLayout.hide();
                }
                break;
            case R.id.view_zhe:
                if (!dropdownLayout.isOpen()) {
                    dropdownLayout.show();
                    view_zhe.setVisibility(View.VISIBLE);
                } else {
                    dropdownLayout.hide();
                    view_zhe.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }


    private void clearFilters() {
        type = "";
        startTime = "";
        endTime = "";

        sp_type.setSelectedIndex(0);
        sp_type.setText("");
        tv_start_time.setText("");

        sp_symbol.setSelectedIndex(0);
        sp_symbol.setText("");
        tv_end_time.setText("");
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
                    startTime = WonderfulStringUtils.dateToString(date, "yyyy-MM-dd HH:mm:ss");
                } else {
                    endTime = WonderfulStringUtils.dateToString(date, "yyyy-MM-dd HH:mm:ss");
                }
            }
        }).setType(new boolean[]{true, true, true, true, false, false})
                .setCancelText(getResources().getString(R.string.cancle))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.confirm))//确认按钮文字
                .setTitleColor(getResources().getColor(R.color.primaryText))
                .setSubmitColor(getResources().getColor(R.color.primaryText))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.primaryTextGray))//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setTitleBgColor(getResources().getColor(R.color.primaryBackNormal))
                .setBgColor(getResources().getColor(R.color.primaryBackNormal))
                .setTextColorCenter(getResources().getColor(R.color.blue_main))
                .setDate(endDate)
                .build();
        pvTime.show();
    }

    private boolean checkFilter() {
        //如果筛选条件全为空
        if (TextUtils.isEmpty(symbol) && TextUtils.isEmpty(type) && TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime)) {
            WonderfulToastUtils.showToast("请选择筛选条件");
            return false;
        }
        //开始时间和结束时间
        if (TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            WonderfulToastUtils.showToast("请选择开始时间");
            return false;
        }
        if (!TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime)) {
            WonderfulToastUtils.showToast("请选择结束时间");
            return false;
        }
        return true;
    }

    private void jiaoyidui() {
        displayLoadingPopup();
        WonderfulOkhttpUtils.post().url(UrlFactory.getWalletUrl()).addHeader("x-auth-token", SharedPreferenceInstance.getInstance().getTOKEN()).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                WonderfulLogUtils.logi("获取所有钱包出错1", e.getMessage());
                WonderfulCodeUtils.checkedErrorCode(WalletDetailActivity.this, OKHTTP_ERROR, "");
            }

            @Override
            public void onResponse(String response) {
                //WonderfulLogUtils.logi("获取所有钱包回执：", "获取所有钱包回执：" + response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        coins.clear();
                        List<Coin> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<Coin>>() {
                        }.getType());
                        coins.addAll(objs);
                        initSpinner();
                        hideLoadingPopup();
                    } else {
                        WonderfulCodeUtils.checkedErrorCode(WalletDetailActivity.this, object.optInt("code"), object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    List<TransactionTypeBean> typeBeans = new ArrayList<>();

    //获取资产流水的交易类型
    private void getTransactionType() {
        sp_type.setText("");
        RemoteDataSource.getInstance().getTransactionType(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                typeBeans.addAll((List<TransactionTypeBean>) obj);
                presenter.allTransaction(SharedPreferenceInstance.getInstance().getTOKEN(), pageNo, limit, SharedPreferenceInstance.getInstance().getID(), startTime, endTime, symbol, type);
                ArrayList<String> typeList = new ArrayList<>();
                if (typeBeans.size() == 0) {
                    return;
                }
                typeList.add("暂不选择");
                for (TransactionTypeBean transactionTypeBean : typeBeans) {
                    typeList.add(transactionTypeBean.getName());
                }
                sp_type.attachDataSource(typeList);
                sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            type = "";
                            sp_type.setText("");
                        } else {
                            type = typeBeans.get(position - 1).getKey() + "";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(WalletDetailActivity.this, code, toastMessage);
            }
        });


    }

}
