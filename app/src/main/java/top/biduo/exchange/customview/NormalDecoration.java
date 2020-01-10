package top.biduo.exchange.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import top.biduo.exchange.adapter.ChooseCoinAdapter;

public class NormalDecoration extends RecyclerView.ItemDecoration {


    //画笔，绘制头部和分割线
    private Paint mItemHeaderPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;

    private Rect mTextRect;


    public NormalDecoration(Context context,  int lineColor) {
        mTextRect = new Rect();
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor((lineColor));

    }

    /**
     * 绘制Item的分割线和组头
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() instanceof ChooseCoinAdapter) {
            int count = parent.getChildCount();//获取可见范围内Item的总数
            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                c.drawRect(left, view.getTop() - 1, right, view.getTop(), mLinePaint);
            }
        }
    }



    /**
     * 设置Item的间距
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() instanceof ChooseCoinAdapter) {
            outRect.top = 1;
        }
    }


    /**
     * dp转换成px
     */
    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}