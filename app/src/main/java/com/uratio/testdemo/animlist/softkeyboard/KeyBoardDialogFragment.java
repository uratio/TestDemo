//package com.uratio.testdemo.animlist.softkeyboard;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Rect;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Message;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//import androidx.lifecycle.LifecycleObserver;
//
//import com.uratio.testdemo.R;
//
///**
// * 这个类时用来实现输入框随软键盘弹出的情况，这个dialogFragment只负责弹起键盘的操作，不负责具体的ui显示和逻辑，
// * 应该实现一个UI类来实现相应的显示和逻辑部分。
// * <p>
// * 使用方法：
// * 1. 需要实现{@link IKeyBoard}
// * 2. UI类需要包含{@link MyEditText}
// * 3. 如果UI需要监听声明周期，需要实现{@link LifecycleObserver}
// * <p>
// * ps:父类的Fragment或者Activity  window的setSoftInputMode设置为{@link WindowManager} SOFT_INPUT_ADJUST_NOTHING
// *
// * @author liyachao
// * @date 2018/4/17
// */
//
//public class KeyBoardDialogFragment extends DialogFragment implements MyEditText.OnKeyBoardHideListener,
//        WeakHandler.IHandler {
//    private static final String TAG = "KeyBoardDialogFragment";
//
//    private IKeyBoard mKeyBoardView;
//    private MyEditText mMyEditText;
//    private boolean softKeyBoardIsVisible;
//    private Activity mActivity;
//    private WeakHandler mHandler;
//    private Rect mRect = new Rect();
//
//
//    public static KeyBoardDialogFragment newInstance(IKeyBoard keyBoard) {
//        KeyBoardDialogFragment fragment = new KeyBoardDialogFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        fragment.setKeyBoardView(keyBoard);
//        return fragment;
//    }
//
//    /**
//     * 安全检查
//     * @param keyBoardView 业务逻辑的view
//     */
//    public void setKeyBoardView(IKeyBoard keyBoardView) {
//        if (keyBoardView == null) {
//            throw new RuntimeException("keyBoardView must not be null");
//        } else if (keyBoardView.getMyEditText() == null) {
//            throw new RuntimeException("keyBoardView must has EditTextView");
//        } else if (keyBoardView.getRoot() == null) {
//            throw new RuntimeException("keyBoardView must has root layout");
//        }
//        mKeyBoardView = keyBoardView;
//        mMyEditText = keyBoardView.getMyEditText();
//    }
//
//    /**
//     * 设置主题 input_dialog_style_large的具体设置如下
//     * <style name="input_dialog_style_large" parent="@android:style/Theme.Dialog">
//     *         <item name="android:windowBackground">@color/transparent</item> //winndow 背景为透明色
//     *         <item name="android:windowNoTitle">true</item> // 没有title
//     *         <item name="android:backgroundDimEnabled">false</item> // 没有默认的背景色
//     *         <item name="android:windowAnimationStyle">@style/keyboard_dialog_animation</item> //window动画，可以不设置
//     *     </style>
//     * 业务逻辑view，注册DialogFragment声明周期
//     *
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NO_TITLE, R.style.input_dialog_style_large);
//        if (mKeyBoardView == null || mKeyBoardView.getRoot() == null) {
//            dismiss();
//            return;
//        }
//        if (mKeyBoardView.getRoot() instanceof LifecycleObserver) {
//            getLifecycle().addObserver((LifecycleObserver) mKeyBoardView.getRoot());
//        }
//        mHandler = new WeakHandler(this);
//    }
//
//
//    @Override
//    public void onAttach(Context activity) {
//        super.onAttach(activity);
//        mActivity = (Activity) activity;
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return mKeyBoardView.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mMyEditText.setOnKeyBoardHideListener(this);
//        initWindowParams();
//    }
//
//    /**
//     * 设置window属性
//     */
//    public void initWindowParams() {
//        Window window = getDialog().getWindow();
//        if (window == null) {
//            return;
//        }
//        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.dimAmount = 0;
//        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.gravity = Gravity.BOTTOM;
//        window.setBackgroundDrawable(new ColorDrawable(0));
//        window.setAttributes(lp);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setCanceledOnTouchOutside(true);
//        return dialog;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mHandler.sendEmptyMessageDelayed(1, 100);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        dismissAllowingStateLoss();
//        mHandler.removeMessages(1);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        mKeyBoardView.onDismiss();
//    }
//
//    @Override
//    public void onKeyHide() {
//        dismiss();
//    }
//
//    public void onGlobalLayout() {
//        Window window = getDialog().getWindow();
//        if (window != null) {
//            mRect.setEmpty();
//            window.getDecorView().getWindowVisibleDisplayFrame(mRect);
//            int screenHeight = UIUtils.getScreenHeight(getContext());
//            int heightDifference = screenHeight - (mRect.bottom - mRect.top);
//            if (heightDifference > screenHeight / 3) {
//                Log.d(TAG, "键盘弹出");
//                softKeyBoardIsVisible = true;
//            } else {
//                if (softKeyBoardIsVisible) {
//                    Log.d(TAG, "键盘隐藏");
//                    dismiss();
//                    softKeyBoardIsVisible = false;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void handleMsg(Message msg) {
//        if (msg.what == 1) {
//            onGlobalLayout();
//            mHandler.sendEmptyMessageDelayed(1, 100);
//        }
//    }
//}
