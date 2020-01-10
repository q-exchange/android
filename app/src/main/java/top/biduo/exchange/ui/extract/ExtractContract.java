package top.biduo.exchange.ui.extract;


import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.ExtractInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface ExtractContract {
    interface View extends Contract.BaseView<Presenter> {

        void extractinfoFail(Integer code, String toastMessage);

        void extractinfoSuccess(List<ExtractInfo> obj);

        void extractSuccess(String obj);

        void extractFail(Integer code, String toastMessage);
    }
        
    interface Presenter extends Contract.BasePresenter {
        void extractinfo(String token);

        void extract(String token, String unit, String amount, String fee, String remark, String jyPassword, String address,String code,String googleCode);
    }
}
