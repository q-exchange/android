package top.biduo.exchange.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import top.biduo.exchange.R;
import top.biduo.exchange.adapter.HelpNewAdapter;
import top.biduo.exchange.entity.HelpBean;
import top.biduo.exchange.ui.message_detail.MessageHelpActivity;
import top.biduo.exchange.adapter.GongGaoAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.base.MyListView;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.ui.message_detail.MessageHelpNewActivity;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/9.
 */
public class HelpActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.listview_help)
    MyListView listview;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.view_back)
    View view_back;
    @BindView(R.id.tvMessage)
    LinearLayout tvMessage;
    private HelpNewAdapter adapterNew;
    private List<HelpBean> helpBeans;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_help;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
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
        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageHelpNewActivity.actionStart(HelpActivity.this, helpBeans.get(position).getId() + "");
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
        WonderfulOkhttpUtils.get().url(UrlFactory.getHelpNew()).build().execute(new StringCallback() {
            @Override
            public void onResponse(String response) {
                Log.i("help", response);
                try {
                    helpBeans = new Gson().fromJson(new JSONObject(response).getString("data"), new TypeToken<List<HelpBean>>() {
                    }.getType());
                    adapterNew = new HelpNewAdapter(HelpActivity.this, helpBeans);
                    listview.setAdapter(adapterNew);
                    adapterNew.notifyDataSetChanged();
                    if(helpBeans.size()==0){
                        tvMessage.setVisibility(View.VISIBLE);
                    }else{
                        tvMessage.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(HelpActivity.this, llTitle);
            isSetTitle = true;
        }
    }
}
