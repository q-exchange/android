package top.biduo.exchange.ui.my_promotion;


import java.util.List;

import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.data.DataSource;

import top.biduo.exchange.entity.PromotionReward;
import top.biduo.exchange.entity.ScoreRecordBean;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class PromotionRewardPresenter implements PromotionRewardContract.Presenter {

    private final DataSource dataRepository;
    private final PromotionRewardContract.View view;

    public PromotionRewardPresenter(DataSource dataRepository, PromotionRewardContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getPromotionReward(String token,String page,String number) {
        view.displayLoadingPopup();
        dataRepository.getScoreRecord( token,page,number, GlobalConstant.PROMOTION_GIVING,"","", new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getPromotionRewardSuccess((List<ScoreRecordBean>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getPromotionRewardFail(code, toastMessage);
            }
        });
    }
}
