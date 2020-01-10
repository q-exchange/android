package top.biduo.exchange.ui.message;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.Message;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface MessageContract {
    interface View extends Contract.BaseView<Presenter> {

        void messageSuccess(List<Message> obj);

        void messageFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter {

        void message(int pageNo, int pageSize);

    }
}
