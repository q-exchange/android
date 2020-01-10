package top.biduo.exchange.ui.credit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

/**
 * @author 明哥
 */
public class VideoCreditActivity extends BaseActivity {
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @BindView(R.id.tv_random)
    TextView tv_random;
    @BindView(R.id.ll_fail_reason)
    LinearLayout ll_fail_reason;
    @BindView(R.id.tv_failed_reason)
    TextView tv_failed_reason;
    @OnClick(R.id.ll_start_record)
    public void startRecord(){
        checkPermission(GlobalConstant.PERMISSION_CAMERA, Permission.CAMERA);
    }

    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }

    @OnClick(R.id.ibBack)
    public void back(){
        finish();
    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case GlobalConstant.PERMISSION_CAMERA:
                    checkPermission(GlobalConstant.PERMISSION_STORAGE, Permission.STORAGE);
                    break;
                case GlobalConstant.PERMISSION_STORAGE:
                    checkPermission(GlobalConstant.PERMISSION_AUDIO, new String[]{Manifest.permission.RECORD_AUDIO});
                    break;
                case GlobalConstant.PERMISSION_AUDIO:
                    Intent intent=new Intent(VideoCreditActivity.this,VideoActivity.class);
                    intent.putExtra("random",tv_random.getText().toString());
                    startActivityForResult(intent,10086);
                    break;
                default:
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case GlobalConstant.PERMISSION_CAMERA:
                    WonderfulToastUtils.showToast(getResources().getString(R.string.camera_permission));
                    break;
                case GlobalConstant.PERMISSION_STORAGE:
                    WonderfulToastUtils.showToast(getResources().getString(R.string.storage_permission));
                    break;
                case GlobalConstant.PERMISSION_AUDIO:
                    WonderfulToastUtils.showToast(getResources().getString(R.string.audio_permission));
                    break;
                default:
            }
        }
    };

    private void checkPermission(int requestCode, String[] permissions) {
        AndPermission.with(this).requestCode(requestCode).permission(permissions).callback(permissionListener).start();
    }


    String videoString;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10086&&resultCode==RESULT_OK){
            videoString=data.getStringExtra("videoStr");
            Log.i("videoStr",videoString);
        }
    }


    @OnClick(R.id.ibRegist)
    public void creditVideo(){
        if(TextUtils.isEmpty(videoString)){
            WonderfulToastUtils.showToast(getResources().getString(R.string.upload_video_first));
            return;
        }
        RemoteDataSource.getInstance().creditVideo(getToken(), videoString, tv_random.getText().toString(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                WonderfulToastUtils.showToast((String) obj);
                finish();
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(VideoCreditActivity.this,code,toastMessage);
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_video_credit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void obtainData() {
        String reason = getIntent().getStringExtra("reason");
        ll_fail_reason.setVisibility(TextUtils.isEmpty(reason)? View.GONE:View.VISIBLE);
        tv_failed_reason.setText(reason);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        RemoteDataSource.getInstance().getRandom(getToken(), new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                try {
                    JSONObject jsonObject=new JSONObject((String) obj);
                    String random=jsonObject.getString("random");
                    tv_random.setText(random);
                } catch (JSONException e) {

                }
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                WonderfulCodeUtils.checkedErrorCode(VideoCreditActivity.this,code,toastMessage);
            }
        });
    }

    public static void actionStart(Activity activity, String reason) {
        Intent intent=new Intent(activity,VideoCreditActivity.class);
        intent.putExtra("reason",reason);
        activity.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }
}
