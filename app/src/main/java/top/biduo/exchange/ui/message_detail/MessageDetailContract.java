package top.biduo.exchange.ui.message_detail;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.Message;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface MessageDetailContract {
    interface View extends Contract.BaseView<Presenter> {

        void messageDetailSuccess(Message obj);

        void messageDetailFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter {

        void messageDetail(String id);
    }
}
