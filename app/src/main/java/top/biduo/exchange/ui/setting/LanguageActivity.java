package top.biduo.exchange.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import top.biduo.exchange.R;
import top.biduo.exchange.base.ActivityManage;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;

public class LanguageActivity extends BaseActivity {

    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibMessage)
    ImageButton ibMessage;
    @BindView(R.id.ivChinese)
    ImageView ivChinese;
    @BindView(R.id.llChinese)
    LinearLayout llChinese;
    @BindView(R.id.ivEnglish)
    ImageView ivEnglish;
    @BindView(R.id.llEnglish)
    LinearLayout llEnglish;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    private int languageCode = 1;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (languageCode != 1) language(1);
            }
        });
        llEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (languageCode != 2) language(2);
            }
        });

    }

    private void language(int languageCode) {
        SharedPreferenceInstance.getInstance().saveLanguageCode(languageCode);
        ActivityManage.finishAll();
        MainActivity.actionStart(LanguageActivity.this);
    }

    @Override
    protected void obtainData() {
        languageCode = SharedPreferenceInstance.getInstance().getLanguageCode();
        if (languageCode == 1) ivChinese.setVisibility(View.VISIBLE);
        else if (languageCode == 2) ivEnglish.setVisibility(View.VISIBLE);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            isSetTitle = true;
            ImmersionBar.setTitleBar(this, llTitle);
        }
    }
}
