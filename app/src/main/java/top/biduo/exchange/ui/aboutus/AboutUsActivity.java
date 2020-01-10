package top.biduo.exchange.ui.aboutus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.BindView;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.app.Injection;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.R;
import top.biduo.exchange.entity.Vision;
import top.biduo.exchange.ui.setting.SettingActivity;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulPermissionUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class AboutUsActivity extends BaseActivity implements AboutUsContract.View{

    private static String versionName;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibRegist)
    TextView ibRegist;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.view_back)
    View view_back;
    @BindView(R.id.llVersion)
    LinearLayout llVersion;
    @BindView(R.id.tvVersionNumber)
    TextView tvVersionNumber;
    private AboutUsContract.Presenter presenter;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_about_us;
    }
    @Override
    public void setPresenter(AboutUsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new AboutUsPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        versionName = getAppVersionName(this);
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
        llVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getNewVision(getToken());
            }
        });
        tvVersionNumber.setText(getAppVersionName(this));
    }


    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
//       presenter.appInfo();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            isSetTitle = true;
            ImmersionBar.setTitleBar(this, llTitle);
        }
    }

    /**
     * 返回当前程序版本名
     */
    public   String getAppVersionName(Context context) {
        versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            //versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
//            WonderfulLogUtils.logi("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    public void getNewVisionSuccess(final Vision obj) {
        if (obj.getData() == null){
            WonderfulToastUtils.showToast(getResources().getString(R.string.versionUpdateTip));
            return;
        }
        if (versionName.equals(obj.getData().getVersion())) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.versionUpdateTip));
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(null)
                    .setTitle(getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.versionUpdateTip2))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (obj.getData().getDownloadUrl() == null || "".equals(obj.getData().getDownloadUrl())) {
                                WonderfulToastUtils.showToast(getResources().getString(R.string.versionUpdateTip3));
                            }else{
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(obj.getData().getDownloadUrl()));//Url 就是你要打开的网址
                                intent.setAction(Intent.ACTION_VIEW);
                                startActivity(intent); //启动浏览器
                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        }
    }

    @Override
    public void getNewVisionFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }




}
