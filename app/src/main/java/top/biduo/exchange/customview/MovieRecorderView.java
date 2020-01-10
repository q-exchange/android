package top.biduo.exchange.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.hardware.Camera.*;
import android.media.MediaRecorder;
import android.media.MediaRecorder.*;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import top.biduo.exchange.R;

public class MovieRecorderView extends LinearLayout implements MediaRecorder.OnErrorListener {
    private static final int FRONT = 1;//前置摄像头标记
    private static final int BACK = 2;//后置摄像头标记
    private int currentCameraType = FRONT;//当前打开的摄像头标记

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ProgressBar mProgressBar;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private Timer mTimer;// 计时器
    private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

    private int mWidth;// 视频分辨率宽度
    private int mHeight;// 视频分辨率高度
    private boolean isOpenCamera;// 是否一开始就打开摄像头
    private int mRecordMaxTime = 10;// 一次拍摄最长时间
    private int mTimeCount;// 时间计数
    private File mRecordFile = null;// 文件

    public MovieRecorderView(Context context) {
        this(context, null);
    }

    public MovieRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化各项组件
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieRecorderView, defStyle, 0);
        mWidth = a.getInteger(R.styleable.MovieRecorderView_video_width, 640);
        mHeight = a.getInteger(R.styleable.MovieRecorderView_video_height, 480);

        isOpenCamera = a.getBoolean(R.styleable.MovieRecorderView_is_open_camera, true);// 默认打开
        //mRecordMaxTime = a.getInteger(R.styleable.MovieRecorderView_record_max_time, 10);// 默认为10

        LayoutInflater.from(context).inflate(R.layout.movie_recorder_view, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new CustomCallBack());
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        a.recycle();
    }


    private class CustomCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            initCamera(FRONT);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            freeCameraResource();
        }

    }

    /**
     * 初始化摄像头
     */
    @SuppressLint("NewApi")
    public void initCamera(int type) {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            int frontIndex = -1;
            int backIndex = -1;
            int cameraCount = Camera.getNumberOfCameras();
            CameraInfo info = new CameraInfo();
            for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
                Camera.getCameraInfo(cameraIndex, info);
                if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                    frontIndex = cameraIndex;
                } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    backIndex = cameraIndex;
                }
            }
            currentCameraType = type;
            if (type == FRONT && frontIndex != -1) {
                mCamera = Camera.open(frontIndex);
            } else if (type == BACK && backIndex != -1) {
                mCamera = Camera.open(backIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null) {
            return;
        }
        Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//自动对焦
        }
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.cancelAutoFocus();
        mCamera.startPreview();
        mCamera.unlock();
    }

    /**
     * 释放摄像头资源
     */
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    private void createRecordDir() {
        File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator + "im/video/");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        File vecordDir = sampleDir;
        // 创建文件
        try {
            mRecordFile = File.createTempFile("recording", ".mp4", vecordDir); //mp4格式
            Log.i("TAG", mRecordFile.getAbsolutePath());
        } catch (IOException e) {
        }
    }

    /**
     * 初始化
     */
    private void initRecord() throws IOException {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        if (mCamera != null) {
            mMediaRecorder.setCamera(mCamera);

        }
        mMediaRecorder.setOnErrorListener(this);
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
        mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
        mMediaRecorder.setOutputFormat(OutputFormat.THREE_GPP);// 视频输出格式
        mMediaRecorder.setVideoEncoder(VideoEncoder.H264);// 视频录制格式
        mMediaRecorder.setAudioEncoder(AudioEncoder.AAC);// 音频格式
        mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：
        mMediaRecorder.setVideoEncodingBitRate(1 * 1280 * 720);
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
        mMediaRecorder.setOutputFile(mRecordFile.getAbsolutePath());
        mMediaRecorder.prepare();
        try {
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录制视频 达到指定时间之后回调接口
     */
    public void record(final OnRecordFinishListener onRecordFinishListener) {

        this.mOnRecordFinishListener = onRecordFinishListener;
        createRecordDir();

        try {
            if (!isOpenCamera)// 如果未打开摄像头，则打开
                initCamera(FRONT);
            initRecord();
            mTimeCount = 0;// 时间计数器重新赋值

            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mTimeCount++;
                    mProgressBar.setProgress(mTimeCount);// 设置进度条
                    if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
                        stop();
                        if (mOnRecordFinishListener != null)
                            mOnRecordFinishListener.onRecordFinish();
                    }
                }
            }, 0, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止拍摄
     */
    public void stop() {
        stopRecord();
        releaseRecord();
        freeCameraResource();
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        mProgressBar.setProgress(0);
        if (mTimer != null)
            mTimer.cancel();
        if (mMediaRecorder != null) {
            // 设置后不会崩
            mMediaRecorder.setOnErrorListener(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMediaRecorder.setPreviewDisplay(null);
        }
    }

    /**
     * 释放资源
     */
    private void releaseRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            try {
                mMediaRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder = null;
    }

    public int getTimeCount() {
        return mTimeCount;
    }

    /**
     * @return the mVecordFile
     */
    public File getmRecordFile() {
        return mRecordFile;
    }

    /**
     * 录制完成回调接口
     */
    public interface OnRecordFinishListener {
        public void onRecordFinish();
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (mr != null)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void changeCamera() throws IOException {

        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            if (currentCameraType == FRONT) {
                initCamera(BACK);
            } else if (currentCameraType == BACK) {
                initCamera(FRONT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }


    }

}