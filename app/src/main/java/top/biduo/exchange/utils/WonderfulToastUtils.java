package top.biduo.exchange.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import top.biduo.exchange.app.MyApplication;


public class WonderfulToastUtils {
    private static Context context = MyApplication.getApp();
    private static Toast mToast;

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 获取string.xml 资源文件字符串数组
     *
     * @param id 资源文件ID
     * @return 资源文件对应字符串数组
     */
    public static String[] getStringArray(int id) {
        return context.getResources().getStringArray(id);
    }

    public static void showToast(String s) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mToast.show();
            }
        });
    }

    public static void showToast(int resourceId) {
        showToast(MyApplication.getApp().getResources().getString(resourceId));
    }

}
