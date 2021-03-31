package com.uratio.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author lang
 * @data 2020/11/5
 */
public class PikerList extends ListView implements AbsListView.OnScrollListener {
    public PikerList(Context context) {
        super(context);
        init();
    }

    public PikerList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PikerList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
