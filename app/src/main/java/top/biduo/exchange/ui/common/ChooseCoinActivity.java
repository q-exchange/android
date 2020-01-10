package top.biduo.exchange.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.ChooseCoinAdapter;
import top.biduo.exchange.adapter.TextWatcher;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.NormalDecoration;
import top.biduo.exchange.customview.StickHeaderDecoration;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.ui.dialog.CommonDialog;
import top.biduo.exchange.ui.dialog.CommonDialogFragment;
import top.biduo.exchange.ui.extract.ExtractActivity;
import top.biduo.exchange.ui.recharge.RechargeActivity;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class ChooseCoinActivity extends BaseActivity {

    public final static int TYPE_CHARGE = 0;
    public final static int TYPE_WITHDRAW = 1;
    private int type;
    @BindView(R.id.rv_chose_coin)
    RecyclerView rv_chose_coin;
    private ChooseCoinAdapter mAdapter;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.et_search)
    EditText et_search;
    StickHeaderDecoration stickHeaderDecoration;

    @OnClick(R.id.tv_cancel)
    public void onCancelClick() {
        onBackPressed();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_choose_coin;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        stickHeaderDecoration = new StickHeaderDecoration(ChooseCoinActivity.this, getResources().getColor(R.color.background_gray),
                getResources().getColor(R.color.primaryText), getResources().getColor(R.color.line_gray));
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (mAdapter != null) {
                    if (s != null && s.length() != 0) {
                        mAdapter.setKeyword(s.toString());
                        rv_chose_coin.removeItemDecoration(stickHeaderDecoration);
                    }else{
                        mAdapter.setKeyword("");
                        rv_chose_coin.addItemDecoration(stickHeaderDecoration);
                    }
                }
            }
        });
    }

    @Override
    protected void obtainData() {
        type = getIntent().getIntExtra("type", 0);
        getAccountAssets();
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {


    }

    public static void actionStart(Activity activity, int type) {
        Intent intent = new Intent(activity, ChooseCoinActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private List<Coin> exchangeCoins = new ArrayList<>();

    public void getAccountAssets() {
        RemoteDataSource.getInstance().myWallet(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                exchangeCoins.clear();
                exchangeCoins.addAll((List<Coin>) obj);
                if (exchangeCoins.size() == 0) {
                    return;
                }
                mAdapter = new ChooseCoinAdapter(ChooseCoinActivity.this, exchangeCoins);
                rv_chose_coin.addItemDecoration(stickHeaderDecoration);
                rv_chose_coin.setLayoutManager(new LinearLayoutManager(ChooseCoinActivity.this));
                rv_chose_coin.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new ChooseCoinAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ChooseCoinAdapter adapter, View view, int position) {
                        switch (type) {
                            case TYPE_CHARGE:
                                if(exchangeCoins.get(position).getCoin().getCanRecharge()!=-1){
                                    RechargeActivity.actionStart(ChooseCoinActivity.this, exchangeCoins.get(position).getCoin().getUnit());
                                    finish();
                                }else{
                                    final CommonDialogFragment dialog = CommonDialogFragment.getInstance(CommonDialogFragment.TYPE_DEFALUT,getString(R.string.reminder),
                                            exchangeCoins.get(position).getCoin().getUnit() + getString(R.string.not_support)+getString(R.string.chargeMoney),
                                            getString(R.string.confirm), "", false);
                                    dialog.setCommitClickListener(new CommonDialogFragment.OnCommitClickListener() {
                                        @Override
                                        public void onCommitClick(String pass) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show(getSupportFragmentManager(),"1");
                                }
                                break;
                            case TYPE_WITHDRAW:
                                if(exchangeCoins.get(position).getCoin().getCanWithdraw()!=-1){
                                    ExtractActivity.actionStart(ChooseCoinActivity.this, exchangeCoins.get(position));
                                    finish();
                                }else{
                                    final CommonDialogFragment dialog = CommonDialogFragment.getInstance(CommonDialogFragment.TYPE_DEFALUT,getString(R.string.reminder),
                                            exchangeCoins.get(position).getCoin().getUnit() + getString(R.string.not_support)+getString(R.string.withdraw),
                                            getString(R.string.confirm), "", false);
                                    dialog.setCommitClickListener(new CommonDialogFragment.OnCommitClickListener() {
                                        @Override
                                        public void onCommitClick(String pass) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show(getSupportFragmentManager(),"2");
                                }
                                break;
                            default:
                        }
                    }
                });
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulToastUtils.showToast(toastMessage);
            }
        });
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
