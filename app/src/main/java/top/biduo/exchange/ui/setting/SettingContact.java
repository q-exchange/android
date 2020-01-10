package top.biduo.exchange.ui.setting;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.Vision;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class SettingContact {

    interface View extends Contract.BaseView<SettingContact.Presenter> {

        void getNewVisionSuccess(Vision obj);

        void getNewVisionFail(Integer code, String toastMessage);

    }

    interface Presenter extends Contract.BasePresenter {

        void getNewVision(String token);

    }
}
