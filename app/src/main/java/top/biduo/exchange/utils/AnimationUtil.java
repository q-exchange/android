package top.biduo.exchange.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

    /**
     * 动画平移-从上到下
     *
     * @param v
     */
    public static void upToDown(View v,boolean fromStartLocation) {

        float fromY=0,toY=0;
        if(fromStartLocation){
            fromY=0;toY=1;
        }else{
            fromY=1;toY=0;
        }
        TranslateAnimation translateAni = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);
        //设置动画执行的时间，单位是毫秒
        translateAni.setDuration(300);
        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        translateAni.setRepeatCount(0);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        translateAni.setRepeatMode(Animation.RESTART);
        //结束时听到最后
        translateAni.setFillAfter(true);
        // 启动动画
        v.startAnimation(translateAni);
    }

    /**
     * 动画平移-从下到上
     *
     * @param v
     */
    public static void downToUp(View v,boolean fromStartLocation) {

        float fromY=0,toY=0;
        if(fromStartLocation){
            fromY=0;toY=-1;
        }else{
            fromY=-1;toY=0;
        }
        TranslateAnimation translateAni = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);
        //设置动画执行的时间，单位是毫秒
        translateAni.setDuration(300);
        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        translateAni.setRepeatCount(0);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        translateAni.setRepeatMode(Animation.RESTART);
        //结束时听到最后
        translateAni.setFillAfter(true);
        // 启动动画
        v.startAnimation(translateAni);
    }


}
