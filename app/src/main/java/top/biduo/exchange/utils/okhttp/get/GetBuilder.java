package top.biduo.exchange.utils.okhttp.get;

import android.net.Uri;
import android.util.Log;


import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.EncryUtils;
import top.biduo.exchange.utils.okhttp.RequestBuilder;
import top.biduo.exchange.utils.okhttp.RequestCall;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2017/11/13.
 */

public class GetBuilder extends RequestBuilder {
    @Override
    public RequestCall build() {
        String token = EncryUtils.getInstance().decryptString(SharedPreferenceInstance.getInstance().getToken(), MyApplication.getApp().getPackageName());
        Log.i("token",token);
        addHeader("access-auth-token", token);
        if (params != null)
        {
            url = appendParams(url, params);
        }
        return new GetRequest(url, params, headers).build();
    }

    private String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    @Override
    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }


    @Override
    public GetBuilder addParams(String key, String value) {
        if (this.params == null) params = new HashMap<>();
        params.put(key, value);
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String value) {
        if (this.headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }
}
