package top.biduo.exchange.ui.myinfo;


import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.SafeSetting;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MyInfoPresenter implements MyInfoContract.Presenter {
    private final DataSource dataRepository;
    private final MyInfoContract.View view;

    public MyInfoPresenter(DataSource dataRepository, MyInfoContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void safeSetting(String token) {
        view.displayLoadingPopup();
        dataRepository.safeSetting(token, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.safeSettingSuccess((SafeSetting) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.safeSettingFail(code, toastMessage);

            }
        });
    }


}
