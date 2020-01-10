package top.biduo.exchange.ui.forgot_pwd;

import top.biduo.exchange.base.BaseTransFragment;

/**
 * Created by Administrator on 2018/2/2.
 */

public abstract class BaseForgotFragment extends BaseTransFragment {
    public interface OperateCallback {
        void switchType(Type type);
    }

    public enum Type {
        PHONE, EMAIL
    }
}
