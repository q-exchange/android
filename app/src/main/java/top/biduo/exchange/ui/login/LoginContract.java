package top.biduo.exchange.ui.login;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.User;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface LoginContract {
    interface View extends Contract.BaseView<Presenter> {

        void loginFail(Integer code, String toastMessage);

        void loginSuccess(User obj);

        void getLoginAuthTypeSuccess(String obj);

        void getLoginAuthTypeFail(Integer code, String toastMessage);

        void sendLoginCodeSuccess(String obj);

        void sendLoginCodeFail(Integer code, String toastMessage);

    }
        
    interface Presenter extends Contract.BasePresenter {
        void login(String username, String password, String challenge, String validate, String seccode,boolean isPass);

        void getLoginAuthType(String phone);

        void sendLoginCode(String phone);

    }
}
