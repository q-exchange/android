package top.biduo.exchange.ui.common;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.ui.lock.LockActivity;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.serivce.MyTextService;
import top.biduo.exchange.utils.WonderfulLogUtils;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startService(new Intent(StartActivity.this, MyTextService.class));
        if (!isTaskRoot()) {
            finish();
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new MyTask(),2000);
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            Intent intent = new Intent( StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }




}
