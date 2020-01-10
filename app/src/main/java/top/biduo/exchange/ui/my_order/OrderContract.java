package top.biduo.exchange.ui.my_order;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.Order;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface OrderContract {
    interface View extends Contract.BaseView<Presenter> {

        void myOrderFail(Integer code, String toastMessage);

        void myOrderSuccess(List<Order> obj);
    }
        
    interface Presenter extends Contract.BasePresenter {
        void myOrder(String token, int status, int pageNo, int pageSize);
    }
}
