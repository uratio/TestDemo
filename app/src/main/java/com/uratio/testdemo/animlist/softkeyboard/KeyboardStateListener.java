package com.uratio.testdemo.animlist.softkeyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * @author lang
 * @data 2021/3/23
 */
public class KeyboardStateListener {
    public static KeyboardStateListener assistActivity(Activity activity) {
        return new KeyboardStateListener(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private OnKeyboardStateChangedListener listener;

    //虚拟键 + 状态栏 总高度
    private int vsHeight;

    public void setOnKeyboardStateChangedListener(OnKeyboardStateChangedListener listener) {
        this.listener = listener;
    }

    private KeyboardStateListener(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        //界面出现变动都会调用这个监听事件
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
    }

    //重新调整跟布局的高度
    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        //当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
            //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            Log.e("data", "possiblyResizeChildOfContent: heightDifference=" + heightDifference);
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 软键盘出现
                if (listener != null) listener.onKeyboardShow(heightDifference - vsHeight);
            } else {
                // 软键盘消失
                vsHeight = heightDifference;
                if (listener != null) listener.onKeyboardHide(heightDifference - vsHeight);
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算mChildOfContent可见高度 ** @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    public interface OnKeyboardStateChangedListener {
        void onKeyboardShow(int kbHeight);

        void onKeyboardHide(int kbHeight);
    }
}