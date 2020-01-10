package top.biduo.exchange.ui.asset_transfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.BottomSelectionFragment;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.CoinTransferSupportBean;
import top.biduo.exchange.entity.FiatAssetBean;
import top.biduo.exchange.entity.MarginAssetBean;
import top.biduo.exchange.utils.AnimationUtil;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class AssetTransferActivity extends BaseActivity {
    boolean isSwitch = false;
    int direction = 0;
    private String accountType = GlobalConstant.ACCOUNT_TYPE_FIAT;//默认币币转法币
    int REQUEST_CODE_SELECT_ACCOUNT = 666;
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @BindView(R.id.ll_transfer_from)
    LinearLayout ll_transfer_from;
    @BindView(R.id.ll_transfer_to)
    LinearLayout ll_transfer_to;
    @BindView(R.id.et_transfer_amount)
    EditText et_transfer_amount;
    @BindView(R.id.tv_coin_name0)
    TextView tv_coin_name0;
    @BindView(R.id.tv_coin_name1)
    TextView tv_coin_name1;
    @BindView(R.id.tv_asset_to)
    TextView tv_asset_to;
    @BindView(R.id.tv_available_coin)
    TextView tv_available_coin;
    private String symbol;
    private List<MarginAssetBean> marginCoins = new ArrayList<>();
    private List<FiatAssetBean> fiatCoins = new ArrayList<>();
    private List<Coin> exchangeCoins = new ArrayList<>();
    private String coinName = "USDT";
    String balance = "0";
    //启动划转页面的类型：币币，杠杆，法币
    public static final String START_TRANSFER_TYPE_EXCHANGE = "START_TRANSFER_TYPE_EXCHANGE";
    public static final String START_TRANSFER_TYPE_MARGIN = "START_TRANSFER_TYPE_MARGIN";
    public static final String START_TRANSFER_TYPE_FIAT = "START_TRANSFER_TYPE_FIAT";
    private String symbolOrCoin;
    private String startType;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_asset_transfer;
    }


    @OnClick(R.id.tv_commit)
    public void commitTransfer() {
        switch (accountType) {
            case GlobalConstant.ACCOUNT_TYPE_FIAT:
                transferFiat();
                break;
            case GlobalConstant.ACCOUNT_TYPE_MARGIN:
                transferMargin();
                break;
            default:
        }
    }

    @OnClick(R.id.ibBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.tv_transfer_all)
    public void transferAll() {
        et_transfer_amount.setText(balance);
    }

    @OnClick(R.id.ll_select_coin)
    public void selectCoin() {
        getSupportBeansToSelect();
    }

    private void showSelector(final ArrayList<String> beans) {
        BottomSelectionFragment bottomSelectionFragment = BottomSelectionFragment.getInstance(beans);
        bottomSelectionFragment.show(getSupportFragmentManager(), "BottomSelectionFragment");
        bottomSelectionFragment.setOnItemSelectListener(new BottomSelectionFragment.OnItemSelectListener() {
            @Override
            public void onItemSelected(int position) {
                coinName = beans.get(position);
                tv_coin_name0.setText(coinName);
                tv_coin_name1.setText(coinName);
                tv_available_coin.setText(getAvailableCoin(coinName));
            }
        });
    }

    private String getAvailableCoin(String coinName) {
        String text = "";
        if (!isSwitch) {//未切换，显示币币账户对应币种的余额
            for (Coin coin : exchangeCoins) {
                String coinUnit = coin.getCoin().getUnit();
                if (coinName.equals(coinUnit)) {
                    text = getResources().getString(R.string.availableCoin) + coin.getBalance() + coinName;
                    balance = coin.getBalance() + "";
                }
            }
        } else {//已切换，根据accountType确定显示法币账户还是杠杆账户
            switch (accountType) {
                case GlobalConstant.ACCOUNT_TYPE_FIAT:
                    for (FiatAssetBean coin : fiatCoins) {
                        if (coinName.equals(coin.getCoin().getUnit())) {
                            text = getResources().getString(R.string.availableCoin) + coin.getBalance() + coinName;
                            balance = coin.getBalance() + "";
                        }
                    }
                    break;
                case GlobalConstant.ACCOUNT_TYPE_MARGIN:
                    for (MarginAssetBean coin : marginCoins) {
                        if (symbol.equals(coin.getSymbol())) {
                            for (int i = 0; i < 2; i++) {
                                if (coin.getLeverWalletList().get(i).getCoin().getUnit().equals(coinName)) {
                                    text = getResources().getString(R.string.availableCoin) + coin.getLeverWalletList().get(i).getBalance() + coinName;
                                    balance = coin.getLeverWalletList().get(i).getBalance() + "";
                                }
                            }
                        }
                    }
                    break;
                default:
            }
        }
        return text;
    }

    private void getSupportBeansToSelect() {
        ArrayList<String> beans = new ArrayList<>();
        switch (accountType) {
            case GlobalConstant.ACCOUNT_TYPE_FIAT:
                if (!isSwitch) {//币币和法币互转时，未切换，显示币币的币种
                    for (Coin coin : exchangeCoins) {
                        beans.add(coin.getCoin().getUnit());
                    }
                    showSelector(beans);
                }else{
                    //币币和法币互转时，已切换，接口获取法币支持的币种
                    for (FiatAssetBean fiatAssetBean : fiatCoins) {
                        beans.add(fiatAssetBean.getCoin().getUnit());
                    }
                    showSelector(beans);
                }
                break;
            case GlobalConstant.ACCOUNT_TYPE_MARGIN:
                //选择杠杆账户的交易对后，可选币种用交易对拆分
                if (!TextUtils.isEmpty(symbol) && symbol.contains("/")) {
                    String[] coinNames = symbol.split("/");
                    beans.add(coinNames[0]);
                    beans.add(coinNames[1]);
                }
                showSelector(beans);
                break;
            default:
        }
    }
    //获取系统支持的法币币种，目前的逻辑没用到
    //个人觉得应该这样：
    //法币资产转出到币币资产的时候，可选币种应该是法币资产中所有的币种
    //币币转入法币时，可选币种应该是系统支持的法币币种
    //（新账号未从币币转入法币USDT，法币账户中就默认没有USDT账户）
    private void getTransferSupportCoinFiat() {
        RemoteDataSource.getInstance().getTransferSupportCoin(getToken(), true, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                ArrayList<CoinTransferSupportBean> coinTransferSupportBeans = (ArrayList<CoinTransferSupportBean>) obj;
                ArrayList<String> beans = new ArrayList<>();
                for (CoinTransferSupportBean coinTransferSupportBean : coinTransferSupportBeans) {
                    beans.add(coinTransferSupportBean.getUnit());
                }
                showSelector(beans);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });
    }

    @OnClick(R.id.iv_switch)
    public void switchDirection() {
        switchDirectionView();
        tv_available_coin.setText(getAvailableCoin(coinName));
    }

    private void switchDirectionView() {
        AnimationUtil.upToDown(ll_transfer_from, !isSwitch);
        AnimationUtil.downToUp(ll_transfer_to, !isSwitch);
        isSwitch = !isSwitch;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void obtainData() {
        symbolOrCoin = getIntent().getStringExtra("symbolOrCoin");
        startType = getIntent().getStringExtra("startType");
        switch (startType) {
            case START_TRANSFER_TYPE_EXCHANGE:
                coinName = symbolOrCoin;
                accountType=GlobalConstant.ACCOUNT_TYPE_FIAT;
                break;
            case START_TRANSFER_TYPE_FIAT:
                switchDirectionView();
                coinName = symbolOrCoin;
                accountType=GlobalConstant.ACCOUNT_TYPE_FIAT;
                break;
            case START_TRANSFER_TYPE_MARGIN:
                switchDirectionView();
                coinName = symbolOrCoin.split("/")[1];
                accountType=GlobalConstant.ACCOUNT_TYPE_MARGIN;
                symbol = symbolOrCoin;
                tv_asset_to.setText(symbol + "  " + getString(R.string.margin_asset));
                break;
            default:
        }
        tv_coin_name0.setText(coinName);
        tv_coin_name1.setText(coinName);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getAllAsset();
    }

    private void getAllAsset() {
        RemoteDataSource.getInstance().getMarginAsset(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                marginCoins.clear();
                marginCoins.addAll((List<MarginAssetBean>) obj);
                if (START_TRANSFER_TYPE_MARGIN.equals(startType)) {
                    for (MarginAssetBean coin : marginCoins) {
                        if (symbol.equals(coin.getSymbol())) {
                            for (int i = 0; i < 2; i++) {
                                if (coin.getLeverWalletList().get(i).getCoin().getUnit().equals(coinName)) {
                                    String text = getResources().getString(R.string.availableCoin) + coin.getLeverWalletList().get(i).getBalance() + coinName;
                                    balance = coin.getLeverWalletList().get(i).getBalance() + "";
                                    tv_available_coin.setText(text);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });

        RemoteDataSource.getInstance().getFiatAsset(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                fiatCoins.clear();
                fiatCoins.addAll((List<FiatAssetBean>) obj);
                if (START_TRANSFER_TYPE_FIAT.equals(startType)) {
                    for (FiatAssetBean coin : fiatCoins) {
                        if (coinName.equals(coin.getCoin().getUnit())) {
                            String text = getResources().getString(R.string.availableCoin) + coin.getBalance() + coinName;
                            balance = coin.getBalance() + "";
                            tv_available_coin.setText(text);
                        }
                    }
                }
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });

        RemoteDataSource.getInstance().myWallet(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                exchangeCoins.clear();
                exchangeCoins.addAll((List<Coin>) obj);
                if (START_TRANSFER_TYPE_EXCHANGE.equals(startType)) {
                    for (Coin coin : exchangeCoins) {
                        String coinUnit = coin.getCoin().getUnit();
                        if (coinName.equals(coinUnit)) {
                            String text =getResources().getString(R.string.availableCoin) + coin.getBalance() + coinName;
                            balance = coin.getBalance() + "";
                            tv_available_coin.setText(text);
                        }
                    }
                }
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });

    }

    public static void actionStart(Activity activity, String startType, String symbolOrCoin) {
        Intent intent = new Intent(activity, AssetTransferActivity.class);
        intent.putExtra("startType", startType);
        intent.putExtra("symbolOrCoin", symbolOrCoin);
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


    //币币和法币划转
    public void transferFiat() {
        String amount = et_transfer_amount.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.input_transfer_count));
            return;
        }
        direction = isSwitch ? 1 : 0;
        String coinName = tv_coin_name0.getText().toString();
        RemoteDataSource.getInstance().transferFiat(coinName, amount, direction + "", getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                WonderfulToastUtils.showToast((String) obj);
                finish();
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });

    }

    //币币-杠杆划转
    private void transferMargin() {
        String amount = et_transfer_amount.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.input_transfer_count));
            return;
        }
        RemoteDataSource.getInstance().transferMargin(!isSwitch, coinName, amount, symbol, getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                WonderfulToastUtils.showToast((String) obj);
                finish();
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(AssetTransferActivity.this,code,toastMessage);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_ACCOUNT && resultCode == RESULT_OK) {
            accountType = data.getStringExtra("accountType");
            if (accountType.equals(GlobalConstant.ACCOUNT_TYPE_FIAT)) {
                tv_asset_to.setText(getString(R.string.fiat_asset));
                tv_available_coin.setText(getAvailableCoin(coinName));
            } else {
                symbol = data.getStringExtra("coinUnit");
                tv_asset_to.setText(symbol + "  " + getString(R.string.margin_asset));
                tv_available_coin.setText(getAvailableCoin(coinName));
            }
        }
    }


}
