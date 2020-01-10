package top.biduo.exchange.ui.myinfo;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.SafeSetting;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface MyInfoContract {
    interface View extends Contract.BaseView<Presenter> {

        void safeSettingSuccess(SafeSetting obj);

        void safeSettingFail(Integer code, String toastMessage);


    }

    interface Presenter extends Contract.BasePresenter {

        void safeSetting(String token);

    }
}
