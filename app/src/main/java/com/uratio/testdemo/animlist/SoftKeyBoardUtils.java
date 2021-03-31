package com.uratio.testdemo.animlist;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * @author lang
 * @data 2021/3/22
 */
public class SoftKeyBoardUtils {

    /**
     * 软键盘显示/隐藏
     */
    public void hideShowKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
//        if (imm.isActive()) {//如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
//        }
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
