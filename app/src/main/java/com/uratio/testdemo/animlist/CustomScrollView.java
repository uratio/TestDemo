package com.uratio.testdemo.animlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * @author niu
 * @date 2019/7/4
 */

public class CustomScrollView extends ScrollView {
    private OnScrollListener listener;

    private int mTouchSlop;
    private float mPrevX;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //判断用户在进行滑动操作的最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                //获取水平移动距离
                float xDiff = Math.abs(eventX - mPrevX);
                //当水平移动距离大于滑动操作的最小距离的时候就认为进行了横向滑动
                //不进行事件拦截,并将这个事件交给子View处理
                if (xDiff > mTouchSlop) {
                    return false;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {

            //这里只传了垂直滑动的距离
            listener.onScroll(t);
        }
    }


    //设置接口
    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

}
