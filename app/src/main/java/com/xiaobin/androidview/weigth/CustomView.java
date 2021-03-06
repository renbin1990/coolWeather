package com.xiaobin.androidview.weigth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * Time:20210312
 * Author:renbin
 */
public class CustomView extends View {

    private int lastx;
    private int lasty;
    private Scroller scroller;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手机触摸的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastx = x;
                lasty = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动距离
                int offerx = x - lastx;
                int offery = y - lasty;
                //调用layout方法重置位置
                //1、第一种
                //    layout(getLeft()+offerx,getTop()+offery,getRight()+offerx,getBottom()+offery);
                //2、第二种，也可以调取下面方法刷新位置
                offsetLeftAndRight(offerx);
                offsetTopAndBottom(offery);
                //3、第三种 使用LayoutParams改变控件位置
                //      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offerx;
//                layoutParams.topMargin = getTop() + offery;
//                setLayoutParams(layoutParams);
                break;
        }
        return true;
    }

    //系统会在绘制View的时候在draw（）方法中调用该方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destx,int desty){
        int scrollx = getScrollX();
        int deltax =destx -  scrollx;
        int scrolly = getScrollY();
        int deltay =desty -  scrolly;
        scroller.startScroll(scrollx,scrolly,deltax,deltay,2000);
    }
}
