package top.biduo.exchange.ui.entrust;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.EntrustHistory;

import java.util.List;


public interface ITrustContract {
    interface View extends Contract.BaseView<Presenter> {

        void errorMes(int e, String meg);

        void getCurrentSuccess(List<EntrustHistory> entrusts);

        void getCancelSuccess(String success);

        void onDataNotAvailable(int code, String message);

        void getHistorySuccess(List<EntrustHistory> success);
    }

    interface Presenter {


        /**
         * 获取当前的委托
         */
        void getCurrentOrder(boolean isMargin,String token, int pageNo, int pageSize, String symbol,String type,String direction,String startTime,String endTime);

        /**
         * 获取历史委托
         */
        void getOrderHistory(boolean isMargin,String token, int pageNo, int pageSize, String symbol,String type,String direction,String startTime,String endTime);
        void onDetach();

        /**
         * 取消委托
         */
        void getCancelEntrust(boolean isMargin,String token, String orderId);
    }
}
