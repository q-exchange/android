package top.biduo.exchange.ui.message_detail;


import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.Message;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MessageDetailPresenter implements MessageDetailContract.Presenter {
    private final DataSource dataRepository;
    private final MessageDetailContract.View view;

    public MessageDetailPresenter(DataSource dataRepository, MessageDetailContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void messageDetail(String id) {
        view.displayLoadingPopup();
        dataRepository.messageDetail(id, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.messageDetailSuccess((Message) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.messageDetailFail(code, toastMessage);
            }
        });
    }
}
