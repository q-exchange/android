package top.biduo.exchange.ui.change_phone;


import top.biduo.exchange.base.Contract;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface ChangePhoneContract {
    interface View extends Contract.BaseView<Presenter> {

        void sendChangePhoneCodeSuccess(String obj);

        void sendChangePhoneCodeFail(Integer code, String toastMessage);

        void changePhoneSuccess(String obj);

        void changePhoneFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter {
        void sendChangePhoneCode(String token);

        void changePhone(String token, String password, String phone, String code);

    }
}
