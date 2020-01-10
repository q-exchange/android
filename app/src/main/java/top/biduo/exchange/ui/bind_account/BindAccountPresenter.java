package top.biduo.exchange.ui.bind_account;

import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.AccountSetting;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class BindAccountPresenter implements BindAccountContact.Presenter {
    private final DataSource dataRepository;
    private final BindAccountContact.View view;

    public BindAccountPresenter(DataSource dataRepository, BindAccountContact.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getAccountSetting(String token) {
        //view.displayLoadingPopup();
        dataRepository.getAccountSetting(token, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                //view.hideLoadingPopup();
                view.getAccountSettingSuccess((AccountSetting) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                //view.hideLoadingPopup();
                view.getAccountSettingFail(code,toastMessage);
            }
        });
    }
}
