package top.biduo.exchange.ui.main.trade;

import top.biduo.exchange.ui.main.MainContract;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.CoinInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class BaseCoinPresenter implements MainContract.BaseCoinPresenter {
    private MainContract.BaseCoinView view;
    private DataSource dataRepository;

    public BaseCoinPresenter(DataSource dataRepository, MainContract.BaseCoinView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void all() {
        dataRepository.all(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.allSuccess((List<CoinInfo>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.allFail(code, toastMessage);
            }
        });
    }
}
