package top.biduo.exchange.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import top.biduo.exchange.customview.MyRoundCornersTransformation;
import top.biduo.exchange.utils.GlideRoundTransform;
import top.biduo.exchange.utils.ScreenUtils;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/1/8.
 */

public class BannerImageLoader extends ImageLoader {
    private int w;
    private int h;

    public BannerImageLoader(int w, int h) {
        if (w<=0){
            this.w = 500;
        }
        if (h<=0){
            this.h = 100;
        }
        this.w = w;
        this.h = h;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (w<=0){
            w = 500;
        }
        if (h<=0){
            h = 100;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(context).load(path)   .transform(new MyRoundCornersTransformation(context, ScreenUtils.dip2px(context,4),
                MyRoundCornersTransformation.CornerType.ALL)).override(w, h).into(imageView);
    }
}
