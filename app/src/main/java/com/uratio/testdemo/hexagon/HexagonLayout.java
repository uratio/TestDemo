package com.uratio.testdemo.hexagon;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.uratio.testdemo.R;

public class HexagonLayout extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "HexagonLayout";
    private Context context;
    private int left; // 左侧视图的宽度
    private int top; // 第一行显示的高度（除去重叠部分）
    private int right; // 右侧视图的宽度
    private int bottom; // 最后一行显示的高度（除去重叠部分）
    private int count; // 第一行按钮数
    private String[] arrBtnText; // 第一行按钮数
    private int width; //六边形宽度
    private int height; //六边形宽度
    private int row; // 总行数

    private int index; // 按钮索引

    private OnClickDexagonListener listener;

    public void setOnClickDexagonListener(OnClickDexagonListener listener) {
        this.listener = listener;
    }

    public HexagonLayout(Context context) {
        this(context, null);
    }

    public HexagonLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HexagonLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public void init(int left, int top, int right, int bottom, int count, String[] arrBtnText) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.count = count;
        this.arrBtnText = arrBtnText;

        row = arrBtnText.length % (2 * count - 1) > 0 ? 2 * arrBtnText.length / (2 * count - 1) + 1 : 2 * arrBtnText.length / (2 * count - 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        width = (measuredWidth - left - right) / count;
        height = (int) Math.abs(width / Math.sin(Math.PI / 180 * 60));

        int measuredHeight = top + bottom + height * 3/ 4 * row + height / 4;
        setMeasuredDimension(measuredWidth, measuredHeight);
        Log.e(TAG, "onMeasure: measuredWidth=" + measuredWidth);

        setChildView();
    }

    public void setChildView() {
        Log.e(TAG, "onMeasure: width=" + width);
        if (index >= arrBtnText.length) index = 0;
        for (int i = 0; i < row + 2; i++) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(HORIZONTAL);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                //第一行
                params.setMargins(0, top - height * 3 / 4, 0, 0);

                addOtherHexagon(layout, count + 1, true);
            } else if (i == row + 1) {
                //最后一行
                params.setMargins(0, -height / 4, 0, 0);

                addOtherHexagon(layout, count + (row % 2 == 0 ? 2 : 1), false);
            } else {
                params.setMargins(0, -height / 4, 0, 0);
                if (i % 2 == 0) {
                    //按钮第 偶数 行
                    addHexagonView(layout, count + 1);
                } else {
                    //按钮第 奇数 行
                    addHexagonView(layout, count + 2);
                }
            }
            layout.setLayoutParams(params);
            addView(layout);
        }
    }

    /**
     * 添加上下两层六边形
     */
    private void addOtherHexagon(LinearLayout layout, int size, boolean isTop) {
        for (int j = 0; j < size; j++) {
            HexagonView hexagonView = new HexagonView(context);
            LayoutParams hParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
            hexagonView.setLayoutParams(hParams);
            if (j == 0) {
                // 左侧背景
                if (isTop) hexagonView.setBackgroundResource(R.drawable.icon_hexagon_bg);
            } else if (j == size - 1) {
                // 右侧背景
                if (isTop) hexagonView.setBackgroundResource(R.drawable.icon_hexagon_bg);
            } else {
                // 中间背景
                hexagonView.setBackgroundResource(R.drawable.icon_hexagon_bg);
            }
            layout.addView(hexagonView);
        }
    }

    /**
     * 添加按钮
     */
    private void addHexagonView(LinearLayout layout, int size) {
        for (int j = 0; j < size; j++) {
            HexagonView hexagonView = new HexagonView(context);
            hexagonView.setGravity(Gravity.CENTER);
            hexagonView.setTextSize(20);
            LayoutParams hParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
            hexagonView.setLayoutParams(hParams);
            if (j == 0) {
                hexagonView.setBackgroundResource(R.drawable.icon_hexagon_bg);
            } else if (j == size - 1) {
                hexagonView.setBackgroundResource(R.drawable.icon_hexagon_bg);
            } else {
                hexagonView.setTextColor(Color.RED);
                hexagonView.setText(arrBtnText[index]);
                hexagonView.setTag(index);
                hexagonView.setOnClickListener(this);
                index++;
            }
            layout.addView(hexagonView);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClick((Integer) v.getTag());
        }
    }

    interface OnClickDexagonListener {
        void OnClick(int index);
    }
}