package top.biduo.exchange.ui.my_promotion;


import java.util.List;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.PromotionRecord;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public interface PromotionRecordContract {

    interface View extends Contract.BaseView<Presenter>{
        void getPromotionFail(Integer code, String toastMessage);

        void getPromotionSuccess(List<PromotionRecord> obj);
    }


    interface Presenter extends Contract.BasePresenter{
        void getPromotion(String token, String page, String number);
    }

}
