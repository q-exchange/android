package top.biduo.exchange.ui.signup;


import top.biduo.exchange.base.Contract;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface SignUpContract {
    interface View extends Contract.BaseView<Presenter> {

    }

    interface Presenter extends Contract.BasePresenter {

    }

    interface PhoneView extends Contract.BaseView<PhonePresenter> {

        void phoneCodeSuccess(String obj);

        void phoneCodeFail(Integer code, String toastMessage);

        void signUpByPhoneSuccess(String obj);

        void signUpByPhoneFail(Integer code, String toastMessage);

    }

    interface PhonePresenter extends Contract.BasePresenter {

        void phoneCode(String phone, String country);

        void signUpByPhone(String phone, String username, String password, String country, String code,String tuijianma,String challenge, String validate, String seccode);

    }


    interface EmailView extends Contract.BaseView<EmailPresenter> {

        void signUpByEmailSuccess(String obj);

        void signUpByEmailFail(Integer code, String toastMessage);

    }

    interface EmailPresenter extends Contract.BasePresenter {

        void signUpByEmail(String email, String username, String password, String country, String ticket, String randstr,  String tuijian2);

    }

}
