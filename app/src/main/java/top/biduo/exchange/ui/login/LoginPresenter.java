package top.biduo.exchange.ui.login;


import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.User;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final DataSource dataRepository;
    private final LoginContract.View view;

    public LoginPresenter(DataSource dataRepository, LoginContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }
    
    @Override
    public void login(String username, String password, String code, String validate, String seccode,boolean isPass) {
        view.displayLoadingPopup();
        dataRepository.login(username, password, code, validate, seccode, isPass,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.loginSuccess((User) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.loginFail(code, toastMessage);
            }
        });
    }


    @Override
    public void getLoginAuthType(String phone) {
        view.displayLoadingPopup();
        dataRepository.getLoginAuthType( phone,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getLoginAuthTypeSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getLoginAuthTypeFail(code, toastMessage);
            }
        });
    }

    @Override
    public void sendLoginCode(String phone) {
        view.displayLoadingPopup();
        RemoteDataSource.getInstance().sendLoginCode( phone,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.sendLoginCodeSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.sendLoginCodeFail(code, toastMessage);
            }
        });
    }
}
