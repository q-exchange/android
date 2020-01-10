package top.biduo.exchange.ui.credit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import top.biduo.exchange.R;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.CircleButtonView;
import top.biduo.exchange.customview.MovieRecorderView;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulFileUtils;
import top.biduo.exchange.utils.WonderfulPermissionUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.movie_record)
    MovieRecorderView mRecorderView;
    @BindView(R.id.tv_random)
    TextView tv_random;

    @OnClick(R.id.iv_change_camera)
    public void changeCamera() {
        try {
            mRecorderView.changeCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                WonderfulToastUtils.showToast(getResources().getString(R.string.start_upload));
                displayLoadingPopup();
                RemoteDataSource.getInstance().uploadVideo(getToken(), mRecorderView.getmRecordFile().getName(), mRecorderView.getmRecordFile(), new DataSource.DataCallback() {
                    @Override
                    public void onDataLoaded(Object obj) {
                        WonderfulToastUtils.showToast(getResources().getString(R.string.success));
                        hideLoadingPopup();
                        String videoStr = (String) obj;
                        Intent intent = new Intent();
                        intent.putExtra("videoStr", videoStr);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onDataNotAvailable(Integer code, String toastMessage) {
                        WonderfulCodeUtils.checkedErrorCode(VideoActivity.this,code,toastMessage);
                    }
                });
            }
        }
    };


    @BindView(R.id.iv_start_record)
    CircleButtonView iv_start_record;

    @OnClick(R.id.iv_start_record)
    public void onRecordClick() {
        WonderfulToastUtils.showToast(getResources().getString(R.string.long_press_video_recording));
    }

    private void startRecord() {
        mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {
            @Override
            public void onRecordFinish() { //录制完成(结束)回调
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        /*if (!WonderfulPermissionUtils.isCanUseCamera(this)) {
            checkPermission(GlobalConstant.PERMISSION_CAMERA, Permission.CAMERA);
        }*/
        iv_start_record.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
            @Override
            public void onLongClick() {
                startRecord();
            }

            @Override
            public void onNoMinRecord(int currentTime) {
                mRecorderView.stopRecord();
                WonderfulToastUtils.showToast(getResources().getString(R.string.video_too_short));
            }

            @Override
            public void onRecordFinishedListener() {
                mRecorderView.stop();
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void obtainData() {
        String random = getIntent().getStringExtra("random");
        tv_random.setText(random);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }


}
