package top.biduo.exchange.ui.main;

import top.biduo.exchange.base.Contract;
import top.biduo.exchange.entity.BannerEntity;
import top.biduo.exchange.entity.C2C;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.entity.CoinInfo;
import top.biduo.exchange.entity.EntrustHistory;
import top.biduo.exchange.entity.Favorite;
import top.biduo.exchange.entity.Plate;
import top.biduo.exchange.entity.SafeSetting;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public interface MainContract {
    interface View extends Contract.BaseView<Presenter> {

        void allCurrencySuccess(Object obj);

        void allCurrencyFail(Integer code, String toastMessage);

        void findSuccess(List<Favorite> obj);

        void findFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter {

        void allCurrency();

        void find(String token);

        void startTCP(short cmd, byte[] body);
    }

    interface OnePresenter extends Contract.BasePresenter {

        void banners(String sysAdvertiseLocation);
    }

    interface OneView extends Contract.BaseView<OnePresenter> {

        void bannersSuccess(List<BannerEntity> obj);

        void bannersFail(Integer code, String toastMessage);
    }

    interface TwoPresenter extends Contract.BasePresenter {

    }

    interface TwoView extends Contract.BaseView<TwoPresenter> {

    }

    interface ThreePresenter extends Contract.BasePresenter {

    }

    interface ThreeView extends Contract.BaseView<ThreePresenter> {
    }

    interface BaseCoinPresenter extends Contract.BasePresenter {

        void all();
    }

    interface BaseCoinView extends Contract.BaseView<BaseCoinPresenter> {

        void allSuccess(List<CoinInfo> obj);

        void allFail(Integer code, String toastMessage);
    }

    interface UserPresenter extends Contract.BasePresenter {


        void safeSetting(String token);
    }

    interface UserView extends Contract.BaseView<UserPresenter> {

        void safeSettingSuccess(SafeSetting obj);

        void safeSettingFail(Integer code, String toastMessage);
    }

    interface BuyPresenter extends Contract.BasePresenter {

        void exChange(String token, String symbol, String price, String amount, String direction, String type);

        void walletBase(String token, String s);

        void walletOther(String token, String s);

        void plate(String symbol);
    }

    interface BuyView extends Contract.BaseView<BuyPresenter> {

        void exChangeSuccess(String obj);

        void exChangeFail(Integer code, String toastMessage);

        void walletBaseSuccess(Coin obj);

        void walletBaseFail(Integer code, String toastMessage);

        void walletOtherSuccess(Coin obj);

        void walletOtherFail(Integer code, String toastMessage);

        void plateSuccess(Plate obj);

        void plateFail(Integer code, String toastMessage);
    }

    interface SellPresenter extends Contract.BasePresenter {

        void exChange(String token, String symbol, String price, String amount, String direction, String type);

        void walletBase(String token, String baseCoin);

        void walletOther(String token, String otherCoin);

        void plate(String symbol);
    }

    interface SellView extends Contract.BaseView<SellPresenter> {

        void exChangeSuccess(String obj);

        void exChangeFail(Integer code, String toastMessage);

        void walletBaseSuccess(Coin obj);

        void walletBaseFail(Integer code, String toastMessage);

        void walletOtherSuccess(Coin obj);

        void walletOtherFail(Integer code, String toastMessage);

        void plateSuccess(Plate obj);

        void plateFail(Integer code, String toastMessage);
    }

    interface C2CPresenter extends Contract.BasePresenter {

        void advertise(int pageNo, int pageSize, String advertiseType, String id);
    }

    interface C2CView extends Contract.BaseView<C2CPresenter> {

        void advertiseSuccess(C2C obj);

        void advertiseFail(Integer code, String toastMessage);
    }

    interface EntrustPresenter extends Contract.BasePresenter {

        void entrust(String token, int pageSize, int pageNo, String symbol);

        void cancleEntrust(String token, String orderId);
    }

    interface EntrustView extends Contract.BaseView<EntrustPresenter> {

        void entrustSuccess(List<EntrustHistory> obj);

        void entrustFail(Integer code, String toastMessage);

        void cancleEntrustSuccess(String obj);

        void cancleEntrustFail(Integer code, String toastMessage);
    }



}
