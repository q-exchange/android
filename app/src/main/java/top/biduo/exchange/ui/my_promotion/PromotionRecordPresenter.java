package top.biduo.exchange.ui.my_promotion;

import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.PromotionRecord;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class PromotionRecordPresenter implements PromotionRecordContract.Presenter {

    private final DataSource dataRepository;
    private final PromotionRecordContract.View view;

    public PromotionRecordPresenter(DataSource dataRepository, PromotionRecordContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getPromotion(String token,String page,String number) {
        view.displayLoadingPopup();
        dataRepository.getPromotion(token,page,number, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getPromotionSuccess((List<PromotionRecord>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getPromotionFail(code, toastMessage);
            }
        });
    }
}
