package top.biduo.exchange.ui.aboutus;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.AppInfo;
import top.biduo.exchange.entity.Vision;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface AboutUsContract {

    interface View extends Contract.BaseView<Presenter> {


        void getNewVisionSuccess(Vision obj);

        void getNewVisionFail(Integer code, String toastMessage);


    }

    interface Presenter extends Contract.BasePresenter {

        void getNewVision(String token);
    }
}
