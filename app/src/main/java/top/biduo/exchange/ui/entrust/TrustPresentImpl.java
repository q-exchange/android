package top.biduo.exchange.ui.entrust;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.entity.EntrustHistory;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Request;

import static top.biduo.exchange.app.GlobalConstant.JSON_ERROR;
import static top.biduo.exchange.app.GlobalConstant.OKHTTP_ERROR;


public class TrustPresentImpl implements ITrustContract.Presenter {

    private ITrustContract.View view;

    public TrustPresentImpl(DataSource dataRepository, ITrustContract.View mView) {
        this.view = mView;
        view.setPresenter(this);
    }

    /**
     * 获取当前委托
     */
    @Override
    public void getCurrentOrder(boolean isMargin,String token, int pageNo, int pageSize, String symbol,String type,String direction,String startTime,String endTime) {
        view.displayLoadingPopup();
        String url=isMargin?UrlFactory.getOrderCurrentMargin():UrlFactory.getEntrustUrl();
        String pageNumberKey=isMargin?"pageNum":"pageNo";
        WonderfulOkhttpUtils.post().url(url)
                .addHeader("x-auth-token", token)
                .addParams(pageNumberKey, pageNo + "")
                .addParams("pageSize", pageSize + "")
                .addParams("type", type )
                .addParams("direction", direction )
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("symbol", symbol).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
//                Log.d("jiejie","-----" + e);
                if (view != null) view.errorMes(OKHTTP_ERROR, null);
                view.hideLoadingPopup();
            }

            @Override
            public void onResponse(String response) {
                view.hideLoadingPopup();
                Log.d("trust", "当前委托" + response);
                if (view != null) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response);
                        view.errorMes(object.getInt("code"), object.optString("message"));
                    } catch (JSONException e) {
                        try {
                            List<EntrustHistory> objs = gson.fromJson(object.getJSONArray("content").toString(), new TypeToken<List<EntrustHistory>>() {
                            }.getType());
                            view.getCurrentSuccess(objs);
                        } catch (JSONException e1) {
                            view.errorMes(JSON_ERROR, null);
                        }
                    }
                }
            }
        });
    }

    /**
     * 取消某个委托
     */
    @Override
    public void getCancelEntrust(boolean isMargin,String token, String orderId) {
        view.displayLoadingPopup();
        String url=isMargin?UrlFactory.getCancelOrderMargin():UrlFactory.getCancleEntrustUrl();
        WonderfulOkhttpUtils.post().url(url + orderId)
                .addHeader("x-auth-token", token).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                view.hideLoadingPopup();
                if (view != null) view.onDataNotAvailable(OKHTTP_ERROR, null);
            }

            @Override
            public void onResponse(String response) {
                view.hideLoadingPopup();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        view.getCancelSuccess(object.getString("message"));
                    } else {
                        view.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.onDataNotAvailable(JSON_ERROR, null);
                }
            }
        });

    }

    /**
     * 获取历史的订单
     */
    @Override
    public void getOrderHistory(boolean isMargin,String token, final int pageNo, int pageSize, String symbol, String type, String direction, String startTime, String endTime) {
        view.displayLoadingPopup();
        String url=isMargin?UrlFactory.getOrderHistoryMargin():UrlFactory.getHistoryEntrus();
        String pageNumberKey=isMargin?"pageNum":"pageNo";
        WonderfulOkhttpUtils.post().url(url)
                .addHeader("x-auth-token", token)
                .addParams(pageNumberKey, pageNo + "")
                .addParams("pageSize", pageSize + "")
                .addParams("type", type )
                .addParams("direction", direction )
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("symbol", symbol).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                view.hideLoadingPopup();
                if (view != null) view.errorMes(OKHTTP_ERROR, null);
            }

            @Override
            public void onResponse(String response) {
                view.hideLoadingPopup();
                if (view == null) return;
                Log.d("trust", "-历史的订单--"+"pageNo--"+pageNo + response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    view.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                } catch (JSONException e) {
                    try {
                        JsonObject object1 = new JsonParser().parse(response).getAsJsonObject();
                        List<EntrustHistory> orders = gson.fromJson(object1.getAsJsonArray("content")
                                .getAsJsonArray(), new TypeToken<List<EntrustHistory>>() {
                        }.getType());
                        view.getHistorySuccess(orders);
                    } catch (Exception e1) {
                        view.onDataNotAvailable(JSON_ERROR, null);
                    }
                }
            }
        });
    }


    @Override
    public void onDetach() {

    }
}
