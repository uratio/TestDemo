package com.uratio.testdemo.animlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.uratio.testdemo.R;

/**
 * @author lang
 * @data 2021/3/19
 */
public class TitleShowView extends LinearLayout {
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
        setBackgroundColor(context.getResources().getColor(R.color.color_000000));

        textView = new TextView(context);

        textView.setTextSize(21);
        textView.setTextColor(context.getResources().getColor(R.color.color_FFFFFF));
        addView(textView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.setMargins(60,60,60,60);
    }

    public void setTitleShowListener(TitleShowListener listener) {
        this.listener = listener;
    }

    public void setContent(String content) {
        setVisibility(VISIBLE);
        textView.setText(content);
        // 设置初始状态
        textView.setTranslationY(textView.getHeight() * 4);
        textView.animate().translationY(0).setDuration(800)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        textView.setTranslationY(0);
                        setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (listener != null) {
                            listener.onAnimationEnd();
                        }
                        setVisibility(GONE);
                    }
                }).start();
    }

    public interface TitleShowListener {
        void onAnimationEnd();
    }
}
