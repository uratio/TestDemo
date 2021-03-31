package com.uratio.testdemo.animlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lang
 * @data 2021/3/22
 */
public class MyRecyclerView extends RecyclerView {
    private boolean isFingle;
    private OnFingerScrollListener listener;

    public void setOnFingleScrollListener(OnFingerScrollListener listener) {
        this.listener = listener;
    }

    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (listener != null) {
                    listener.onFingerScrolled();
                }
//                isFingle = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("data", "onTouchEvent: ACTION_UP");
                isFingle = false;
                break;
        }
        return super.onTouchEvent(e);
    }

    public interface OnFingerScrollListener {
        void onFingerScrolled();
    }
}
