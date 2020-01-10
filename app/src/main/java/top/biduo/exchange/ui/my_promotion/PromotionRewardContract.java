package top.biduo.exchange.ui.my_promotion;


import java.util.List;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.ScoreRecordBean;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public interface PromotionRewardContract {

    interface View extends Contract.BaseView<Presenter>{
        void getPromotionRewardFail(Integer code, String toastMessage);

        void getPromotionRewardSuccess(List<ScoreRecordBean> obj);
    }


    interface Presenter extends Contract.BasePresenter{
        void getPromotionReward(String token, String page, String number);
    }

}
