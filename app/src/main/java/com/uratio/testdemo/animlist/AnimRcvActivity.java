package com.uratio.testdemo.animlist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uratio.testdemo.R;
import com.uratio.testdemo.animlist.softkeyboard.KeyboardStateListener;
import com.uratio.testdemo.animlist.softkeyboard.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;

public class AnimRcvActivity extends AppCompatActivity implements AnimRcvAdapter.AnimRcvListener,
        AnimRcvAdapter.EditAbleListAdapterListener {
    private static final String TAG = "AnimRcvActivity";

    private RelativeLayout rlLayout;

    private LinearLayoutManager manager;
    private MyRecyclerView recyclerView;
    private AnimRcvAdapter rcvAdapter;
    private List<HCMessage> dataRcv = new ArrayList<>();

    private Animation animationOut, animationIn;

    private TitleShowView titleShow;
    private TextView tvTitle;

//    private int editPosition = -1;
    private EditText etInput;
    private View viewBottom;
    private LinearLayout llInput;
    private LinearLayout llBottom;

    private ViewGroup.LayoutParams params;


    /**
     * 隐藏ActionBar和StatusBar
     */
    private void hideActionStatusBar() {
        //set no title bar 需要在setContentView之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //如果上面的不起作用，可以换成下面的。
        if (getSupportActionBar()!=null) getSupportActionBar().hide();
        if (getActionBar()!=null) getActionBar().hide();
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 隐藏 NavigationBar和StatusBar
     */
    protected void hideBottomStatusBar() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //取消状态栏的标题
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {//判断SDK的版本是否 =21
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //允许页面可以拉伸到顶部状态栏并且定义顶部状态栏透名
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |  //设置全屏显示
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT); //设置状态栏为透明
            window.setNavigationBarColor(Color.TRANSPARENT); //设置虚拟键为透明
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();   //将actionBar隐藏
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        hideActionStatusBar();
//        hideBottomStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_anim_rcv);

        rlLayout = findViewById(R.id.rl_layout);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /**
         * RecyclerView
         */
        recyclerView = findViewById(R.id.recyclerView);
        titleShow = findViewById(R.id.title_show);
        tvTitle = findViewById(R.id.tv_title);


        etInput = findViewById(R.id.et_input);

        viewBottom = findViewById(R.id.view_bottom);
        params = viewBottom.getLayoutParams();

        llInput = findViewById(R.id.ll_input);
        llBottom = findViewById(R.id.ll_bottom);

        animationOut = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_out);
        animationIn = AnimationUtils.loadAnimation(this, R.anim.anim_dialog_in);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        rcvAdapter = new AnimRcvAdapter(this, dataRcv, this, this);
        recyclerView.setAdapter(rcvAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, 60));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 系统动画
//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setAddDuration(1000);
//        defaultItemAnimator.setRemoveDuration(1000);
//        recyclerView.setItemAnimator(defaultItemAnimator);

        // 重写动画
        MyItemAnimator myItemAnimator = new MyItemAnimator();
//        myItemAnimator.setAddDuration(1000);
//        myItemAnimator.setAlphaDuration(500);
        myItemAnimator.setTranslationDuration(1000);
        myItemAnimator.setRemoveDuration(0);
        recyclerView.setItemAnimator(myItemAnimator);

        KeyboardStateListener.assistActivity(this).setOnKeyboardStateChangedListener(new KeyboardStateListener.OnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardShow(int kbHeight) {
                //软键盘弹出
                Log.e(TAG, "onKeyboardHide: 软键盘弹出");
                if (etInput.hasFocus()) {
                    params.height = kbHeight;
                    viewBottom.setLayoutParams(params);
                }
            }

            @Override
            public void onKeyboardHide(int kbHeight) {
                //软键盘隐藏
                Log.e(TAG, "onKeyboardHide: 软键盘隐藏");
                params.height = 0;
                viewBottom.setLayoutParams(params);
                if (!etInput.hasFocus()) {
                    etInputShowOrHide(true);
                }
                clearItemFocus();
            }
        });

        titleShow.setTitleShowListener(new TitleShowView.TitleShowListener() {
            @Override
            public void onAnimationEnd() {
                rcvAdapter.addData();
                recyclerView.smoothScrollToPosition(dataRcv.size());
            }
        });
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_rcv_init:
                dataRcv.clear();
                rcvAdapter.notifyDataSetChanged();
                // 头占位 View
//                HCMessage hcMessageT = new HCMessage();
//                hcMessageT.setType(0);
//                dataRcv.add(hcMessageT);
//                rcvAdapter.notifyItemChanged(0);
                // 数据
                for (int i = 0; i < 4; i++) {
                    HCMessage hcMessage = new HCMessage();
                    hcMessage.setType(i % 2 + 1);
                    hcMessage.setContent((i + 1) + "");
                    dataRcv.add(hcMessage);
                    rcvAdapter.notifyItemChanged(dataRcv.size());
                }
                // 尾占位 View
                HCMessage hcMessage = new HCMessage();
                hcMessage.setType(4);
                dataRcv.add(hcMessage);
                rcvAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_rcv_add:
                if (dataRcv.size() > 6) {
                    dataRcv.clear();
                    rcvAdapter.notifyDataSetChanged();
                }
                rcvAdapter.addData2();
//                titleShow.setContent("添加的文字");
                rcvAdapter.addData2();
//                scrollPositionToTop(dataRcv.size() - 3);
                break;
            case R.id.btn_rcv_remove:
                rcvAdapter.removeData();
//                rcvAdapter.removeData(4);
//                rcvAdapter.removeData(5);
                break;
        }
    }

    private void etInputShowOrHide(boolean toShow) {
        if (toShow) {
            llInput.animate().setDuration(50).translationY(0).start();
        } else {
            llInput.animate().setDuration(20).translationY(llInput.getHeight()).start();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

//            if (editPosition != -1) {
//                //重置点击坐标
//                editPosition = -1;
//            }
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                clearItemFocus();
                if (!etInput.hasFocus()) {
                }
                return true;
            }
        }
        return false;
    }

    // 清除item中焦点
    private void clearItemFocus() {
        if (etInput.hasFocus()) return;
        if (getCurrentFocus() != null) {
            getCurrentFocus().clearFocus();
        }
    }


    private void scrollPositionToTop(int position) {
        manager.scrollToPositionWithOffset(position, 20);
    }

    @Override
    public void onClickQuestion(int position, boolean hasFocus) {
        if (!hasFocus) {
            llBottom.setVisibility(View.VISIBLE);
            return;
        }
        params.height = 0;
        viewBottom.setLayoutParams(params);
//        editPosition = position;
        scrollPositionToTop(position);
        if (!etInput.hasFocus()) {
            etInputShowOrHide(false);
        }
    }

    @Override
    public void onClickSend(String text) {
        clearItemFocus();

        // 尾占位 View
        HCMessage hcMessage = new HCMessage();
        hcMessage.setType((dataRcv.size() - 2) % 2 + 1);
        hcMessage.setContent(text);
        dataRcv.add(dataRcv.size() - 1, hcMessage);
        rcvAdapter.notifyDataSetChanged();

        scrollPositionToTop(dataRcv.size() - 1);
    }

    @Override
    public void onEditTextChanged(int position, String value) {
//        dataRcv.get(position).setContent(value);
//        rcvAdapter.notifyDataSetChanged();
    }
}
