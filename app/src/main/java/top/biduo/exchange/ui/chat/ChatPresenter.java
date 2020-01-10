package top.biduo.exchange.ui.chat;

import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.ChatEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public class ChatPresenter implements ChatContact.Presenter {
    private final DataSource dataRepository;
    private final ChatContact.View view;

    public ChatPresenter(DataSource dataRepository, ChatContact.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getHistoryMessage(String token,String orderId,int pageNo, int pageSize) {
        view.displayLoadingPopup();
        dataRepository.getHistoryMessage(token, orderId, pageNo, pageSize,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.getHistoryMessageSuccess((List< ChatEntity>) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.getHistoryMessageFail(code,toastMessage);
            }
        });
    }
}
