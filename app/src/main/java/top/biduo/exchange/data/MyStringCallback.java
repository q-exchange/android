package top.biduo.exchange.data;

import android.content.Intent;
import android.util.Log;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

public abstract class MyStringCallback<S> extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        Log.i("MyStringCallback",response.body().string());
        String string = response.body().string();
        return string;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(String entity, int id) {

    }

    public abstract void onContinue(String entity, int id);

}
