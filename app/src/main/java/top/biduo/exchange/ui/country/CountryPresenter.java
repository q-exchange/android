package top.biduo.exchange.ui.country;

import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.Country;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class CountryPresenter implements CountryContract.Presenter {
    private final DataSource dataRepository;
    private final CountryContract.View view;

    public CountryPresenter(DataSource dataRepository, CountryContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void country() {
        view.displayLoadingPopup();
        dataRepository.country(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.countrySuccess((List<Country>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.countryFail(code, toastMessage);

            }
        });
    }
}
