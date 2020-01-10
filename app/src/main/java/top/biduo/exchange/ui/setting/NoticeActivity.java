package top.biduo.exchange.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.message_detail.MessageDetailActivity;
import top.biduo.exchange.adapter.GongGaoAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.base.LinListView;
import top.biduo.exchange.entity.Message;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/9.
 */
public class NoticeActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.listview_1)
    LinListView listview_1;
    @BindView(R.id.view_back)
    View view_back;
    @BindView(R.id.tvMessage)
    LinearLayout tvMessage;

    private GongGaoAdapter adapter;
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_notice;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter=new GongGaoAdapter(NoticeActivity.this,messageList);
        listview_1.setEveryPageItemCount(100);
        listview_1.setAdapter(adapter);
        listview_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDetailActivity.actionStart(NoticeActivity.this, messageList.get(position).getId());
            }
        });
        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview_1.setOnRefreshListener(new LinListView.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                listview_1.setNotMore();
            }

            @Override
            public void onRefresh() {
                listview_1.stopFreshing();
                getMessage();
            }
        });
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getMessage();
    }

    private void getMessage() {
        WonderfulOkhttpUtils.post().url(UrlFactory.getMessageUrl())
                .addParams("pageNo", 1 + "").addParams("pageSize", "100")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                WonderfulLogUtils.logi("miao",response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        List<Message> messages = new Gson().fromJson(object.getJSONObject("data").getJSONArray("content").toString(), new TypeToken<List<Message>>() {
                        }.getType());
                        messageList.clear();
                        messageList.addAll(messages);
                        adapter.notifyDataSetChanged();
                        if(messageList.size()==0){
                            tvMessage.setVisibility(View.VISIBLE);
                        }else{
                            tvMessage.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<Message> messageList = new ArrayList<>();
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }
}
