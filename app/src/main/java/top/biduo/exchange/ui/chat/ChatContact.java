package top.biduo.exchange.ui.chat;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.ChatEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public interface ChatContact {
    interface View extends Contract.BaseView<Presenter>{
       void getHistoryMessageSuccess(List<ChatEntity> entityList);

        void getHistoryMessageFail(Integer code, String toastMessage);
    }

    interface Presenter extends  Contract.BasePresenter{
        void getHistoryMessage(String token,String orderId,int pageNo, int pageSize);
    }
}
