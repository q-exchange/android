package top.biduo.exchange.ui.account_pwd;

import top.biduo.exchange.data.DataSource;

/**
 * Created by Administrator on 2018/4/10.
 */

public class EditPwdPresenter implements AccountPwdContract.EditPresenter {
    private final DataSource dataRepository;
    private final AccountPwdContract.EditView view;

    public EditPwdPresenter(DataSource dataRepository, AccountPwdContract.EditView view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void editAccountPed(String token, String newPassword, String oldPassword,String msgCode,String googleCode) {
        view.displayLoadingPopup();
        dataRepository.editAccountPed(token, newPassword, oldPassword,msgCode,googleCode, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.editAccountPedSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.editAccountPedFail(code, toastMessage);

            }
        });
    }

    @Override
    public void sendEditAccountPwdCode(String token) {
        view.displayLoadingPopup();
        dataRepository.sendEditAccountPwdCode(token, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.sendEditAccountPwdCodeSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.sendEditAccountPwdCodeFail(code, toastMessage);

            }
        });




    }
}
