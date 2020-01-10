package top.biduo.exchange.ui.extract;


import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.ExtractInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class ExtractPresenter implements ExtractContract.Presenter {
    private final DataSource dataRepository;
    private final ExtractContract.View view;

    public ExtractPresenter(DataSource dataRepository, ExtractContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void extractinfo(String token) {
        view.displayLoadingPopup();
        dataRepository.extractinfo(token, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.extractinfoSuccess((List<ExtractInfo>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.extractinfoFail(code, toastMessage);
            }
        });
    }

    @Override
    public void extract(String token, String unit, String amount, String fee, String remark, String jyPassword,String address,String code,String googleCode) {
        view.displayLoadingPopup();
        dataRepository.extract(token, unit, amount, fee, remark, jyPassword,address, code,googleCode,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.extractSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.extractFail(code, toastMessage);

            }
        });
    }
}
