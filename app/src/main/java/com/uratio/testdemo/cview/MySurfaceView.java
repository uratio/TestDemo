package com.uratio.testdemo.cview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 *  继承SurfaceView并实现SurfaceHolder.Callback接口
 *  ----> SurfaceView.getHolder()获得SurfaceHolder对象
 *  ----> SurfaceHolder.addCallback(callback)添加回调函数
 *  ----> SurfaceHolder.lockCanvas()获得Canvas对象并锁定画布
 *  ----> Canvas绘画
 *  ----> SurfaceHolder.unlockCanvasAndPost(Canvas canvas)结束锁定画图，并提交改变，将图形显示。
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在创建时激发，一般在这里调用画图的线程。
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    /**
     * 在surface的大小发生改变时激发
     * @param holder
     * @param format
     * @param width SurfaceView的新宽度
     * @param height SurfaceView的新高度
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 销毁时激发，一般在这里将画图的线程停止、释放
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
