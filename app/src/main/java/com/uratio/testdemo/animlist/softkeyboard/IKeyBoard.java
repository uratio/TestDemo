package com.uratio.testdemo.animlist.softkeyboard;

import android.view.View;

public interface IKeyBoard {

    // EditTextView需要继承TextEditTextView，TextEditTextView是用来监听虚拟按键向下的操作
    MyEditText getMyEditText();

    // 根布局
    View getRoot();

    // dialogFragment 销毁时调用，也就是隐藏时
    void onDismiss();
}

