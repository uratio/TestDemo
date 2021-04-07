package com.uratio.testdemo.animlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.uratio.testdemo.R;

/**
 * @author lang
 * @data 2021/3/19
 */
public class TitleShowView extends LinearLayout {
    private static final String TAG = TitleShowView.class.getSimpleName();
    private TextView textView;
    private TitleShowListener listener;

    public TitleShowView(Context context) {
        this(context, null);
    }

    public TitleShowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setVisibility(GONE);

        View view = inflate(context, R.layout.layout_title_show, this);
        textView = view.findViewById(R.id.tv_show);
//        setBackgroundColor(context.getResources().getColor(R.color.color_000000));
//
//        setPadding(60,60,60,60);
//
//        textView = new TextView(context);
//
//        textView.setTextSize(21);
//        textView.setTextColor(context.getResources().getColor(R.color.color_FFFFFF));
//        addView(textView);
        Log.e(TAG, "init: start textView.getY()=" + textView.getY());
        Log.e(TAG, "init: start textView.getTop()=" + textView.getTop());
//        textView.setVisibility(GONE);
        Log.e(TAG, "init: start textView.getY()=" + textView.getY());
        textView.setY(300);

    }

    public void setTitleShowListener(TitleShowListener listener) {
        this.listener = listener;
    }

    public void setContent(String content) {
        setVisibility(VISIBLE);
        // 设置初始状态
        textView.setTranslationY(300);
        textView.setText(content);
        Log.e(TAG, "init: start textView.getY()=" + textView.getY());
        Log.e(TAG, "init: start textView.getTop()=" + textView.getTop());
        ViewPropertyAnimator animate = textView.animate();
//        animate.start();
        Log.e(TAG, "setContent: start");
        animate.translationY(0).setDuration(1000).setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        Log.e(TAG, "onAnimationStart: start");
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        Log.e(TAG, "onAnimationCancel");
                        textView.setTranslationY(0);
                        setVisibility(GONE);
                        // 设置初始状态
//                        textView.setTranslationY(300);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.e(TAG, "onAnimationEnd textView.getY()=" + textView.getY());
                        Log.e(TAG, "onAnimationEnd textView.getTop()=" + textView.getTop());
                        if (textView.getY() == textView.getTop()) {
                            if (listener != null) {
                                listener.onAnimationEnd();
                            }
                            setVisibility(GONE);
                            // 设置初始状态
//                            textView.setTranslationY(300);
                        }
                    }
                }).start();
    }

    public interface TitleShowListener {
        void onAnimationEnd();
    }
}
