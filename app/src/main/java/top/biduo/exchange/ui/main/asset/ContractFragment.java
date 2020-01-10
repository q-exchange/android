package top.biduo.exchange.ui.main.asset;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.TextWatcher;
import top.biduo.exchange.adapter.WalletAdapter;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.base.BaseFragment;
import top.biduo.exchange.customview.BottomSelectionFragment;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.ui.extract.ExtractActivity;
import top.biduo.exchange.ui.recharge.RechargeActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulMathUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

/**
 * @author 明哥
 * 币币资产
 * 参考火币命名
 */

public class ContractFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contract;
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

    }



}
