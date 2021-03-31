package com.uratio.testdemo.hexagon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lang
 * @data 2020/9/8
 */
public class HexagonView extends AppCompatTextView {
    private List<Point> mPoints;
    private Paint paint;
    private static final int DEFAULT_WIDTH = 300;

    public HexagonView(Context context) {
        super(context);
        init(context, null);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPoints = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int measuredWidth;
        if (mode == MeasureSpec.EXACTLY) {
            measuredWidth = getMeasuredWidth();
        } else {
            measuredWidth = DEFAULT_WIDTH;
        }
        int measuredHeight = (int) Math.abs(measuredWidth / Math.sin(Math.PI / 180 * 60));
        float width = measuredHeight / 2;
        setMeasuredDimension(measuredWidth, measuredHeight);
        for (int i = 0; i < 6; i++) {
            Point point = new Point();
            switch (i) {
                case 0:
                    point.setX(measuredWidth / 2);
                    point.setY(0);
                    break;
                case 1:
                    point.setX(measuredWidth);
                    point.setY(width / 2);
                    break;
                case 2:
                    point.setX(measuredWidth);
                    point.setY(width * 3 / 2);
                    break;
                case 3:
                    point.setX(measuredWidth / 2);
                    point.setY(measuredHeight);
                    break;
                case 4:
                    point.setX(0);
                    point.setY(width * 3 / 2);
                    break;
                case 5:
                    point.setX(0);
                    point.setY(width / 2);
                    break;
            }
            mPoints.add(point);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF5ee5ff);
        paint.setStrokeWidth(5);

        Path path = new Path();
        path.moveTo(mPoints.get(0).getX(), mPoints.get(0).getY());
        path.lineTo(mPoints.get(1).getX(), mPoints.get(1).getY());
        path.lineTo(mPoints.get(2).getX(), mPoints.get(2).getY());
        path.lineTo(mPoints.get(3).getX(), mPoints.get(3).getY());
        path.lineTo(mPoints.get(4).getX(), mPoints.get(4).getY());
        path.lineTo(mPoints.get(5).getX(), mPoints.get(5).getY());
        path.lineTo(mPoints.get(0).getX(), mPoints.get(0).getY());
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                if (!isWithin(event.getX(), event.getY())) {
                    return false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isWithin(float x, float y) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int length = measuredHeight / 2;

        return length - Math.abs(y - length) > Math.abs(x - measuredWidth / 2) / Math.sqrt(3);
        //旋转 90 度后
//        return length - Math.abs(x - measuredWidth / 2) > Math.abs(y - length) / Math.sqrt(3);
    }
}
