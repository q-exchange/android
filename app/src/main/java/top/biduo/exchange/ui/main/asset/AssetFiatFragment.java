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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.FiatAssetAdapter;
import top.biduo.exchange.adapter.TextWatcher;
import top.biduo.exchange.base.BaseFragment;
import top.biduo.exchange.customview.BottomSelectionFragment;
import top.biduo.exchange.entity.FiatAssetBean;
import top.biduo.exchange.entity.MarginAssetBean;
import top.biduo.exchange.ui.asset_transfer.AssetTransferActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulMathUtils;

/**
 * @author 明哥
 * 法币资产
 * 参考火币命名
 */

public class AssetFiatFragment extends BaseFragment {
    @BindView(R.id.rc_asset)
    RecyclerView recyclerView;
    List<FiatAssetBean> fiatCoins = new ArrayList<>();
    private FiatAssetAdapter adapter;

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.cbHide)
    CheckBox cbHide;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_asset_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        etSearch.addTextChangedListener(localChangeWatcher);
        cbHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setHideZero(isChecked);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new FiatAssetAdapter(R.layout.adapter_wallet, fiatCoins);
        adapter.isFirstOnly(true);
        recyclerView.setAdapter(adapter);

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


    private TextWatcher localChangeWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            adapter.setSearchKey(s.toString());
        }
    };

    public void setData(List<FiatAssetBean> exchangeCoins) {
        fiatCoins.clear();
        fiatCoins.addAll(exchangeCoins);
        if(adapter!=null)adapter.notifyDataSetChanged();
        getFiatTotalAsset();
    }

    public void hideAmount(boolean isShowAmount){
        if(adapter!=null) {
            adapter.setHideAmount(isShowAmount);
            adapter.notifyDataSetChanged();
        }
    }



    double sumUsd = 0;
    double sumCny = 0;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvCnyAmount)
    TextView tvCnyAmount;


    private void getFiatTotalAsset() {
        sumCny=0;
        sumUsd=0;
        if(fiatCoins!=null&&fiatCoins.size()!=0){
            for (FiatAssetBean coin : fiatCoins) {
                sumUsd += (coin.getBalance() * coin.getCoin().getUsdRate());
                sumCny += (coin.getBalance() * coin.getCoin().getCnyRate());
            }
        }
        if (SharedPreferenceInstance.getInstance().getMoneyShowType() == 1) {
            tvAmount.setText(WonderfulMathUtils.getRundNumber(sumUsd, 8, null));
            tvCnyAmount.setText("≈"+WonderfulMathUtils.getRundNumber(sumCny, 2, null) + " CNY");
        } else if (SharedPreferenceInstance.getInstance().getMoneyShowType() == 2) {
            tvAmount.setText("********");
            tvCnyAmount.setText("*****");
        }

    }

}
