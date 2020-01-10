package top.biduo.exchange.ui.message_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import top.biduo.exchange.R;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.entity.Message;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

public class MessageDetailActivity extends BaseActivity implements MessageDetailContract.View {

    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibDetail)
    TextView ibDetail;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.wb)
    WebView wb;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.text_time)
    TextView text_time;
    @BindView(R.id.view_back)
    View view_back;
    private MessageDetailContract.Presenter presenter;
    private String id;
    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new MessageDetailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        initWb();
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
    }

    private void initWb() {
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setSupportZoom(false);
        wb.getSettings().setBuiltInZoomControls(false);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDefaultFontSize(16);
        wb.setWebViewClient(new WebViewClient());
        wb.setBackgroundColor(0);
    }

    @Override
    protected void obtainData() {
        id = getIntent().getStringExtra("id");
        WonderfulLogUtils.logi("miao", id);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        presenter.messageDetail(id);
    }

    @Override
    public void setPresenter(MessageDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            isSetTitle = true;
            ImmersionBar.setTitleBar(this, llTitle);
        }
    }

    @Override
    public void messageDetailSuccess(Message obj) {
        if (obj == null) return;
        if (text == null) {
            return;
        }
        text.setText(obj.getTitle());
        text_time.setText(obj.getCreateTime() );
        wb.getSettings().setSupportZoom(true);

        wb.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:function modifyTextColor(){" +
                        "document.getElementsByTagName('body')[0].style.webkitTextFillColor='#999999'" +
                        "};modifyTextColor();");
            }
        });
        wb.loadDataWithBaseURL(null, obj.getContent(), "text/html", "utf-8", null);

    }

    @Override
    protected void onDestroy() {
        if (wb != null) {
            //加载null内容
            wb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清除历史记录
            wb.clearHistory();
            //移除WebView
            ((ViewGroup) wb.getParent()).removeView(wb);
            //销毁VebView
            wb.destroy();
            //WebView置为null
            wb = null;
        }
        super.onDestroy();
    }

    @Override
    public void messageDetailFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }






}
