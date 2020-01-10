package top.biduo.exchange.ui.main.user;

import top.biduo.exchange.ui.main.MainContract;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.SafeSetting;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UserPresenter implements MainContract.UserPresenter {
    private MainContract.UserView view;
    private DataSource dataRepository;

    public UserPresenter(DataSource dataRepository, MainContract.UserView view) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.view.setPresenter(this);
    }



    @Override
    public void safeSetting(String token) {
        dataRepository.safeSetting(token, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.safeSettingSuccess((SafeSetting) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.safeSettingFail(code, toastMessage);

            }
        });
    }
}
