package top.biduo.exchange.ui.seller;

import top.biduo.exchange.data.DataSource;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class SellerApplyPresent implements SellerApplyContract.Presenter {

    private final DataSource dataRepository;
    private final SellerApplyContract.View view;

    public SellerApplyPresent(DataSource dataRepository, SellerApplyContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }



}
