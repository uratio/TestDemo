package com.uratio.testdemo.animlist;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uratio.testdemo.R;
import com.uratio.testdemo.animlist.softkeyboard.KeyboardStateListener;
import com.uratio.testdemo.animlist.softkeyboard.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;

public class AnimListActivity extends AppCompatActivity {
    private static final String TAG = "AnimRcvActivity";
    private ListView listView;
    private AnimListAdapter adapter;
    private List<HCMessage> dataList = new ArrayList<>();

    private TitleShowView titleShow;

    private int editPosition = -1;
    private EditText etInput;
    private LinearLayout llInput;
    private LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_list);

        /**
         * listView
         */
        listView = findViewById(R.id.listView);

        for (int i = 0; i < 4; i++) {
            HCMessage hcMessage = new HCMessage();
            hcMessage.setType(i % 2);
            hcMessage.setContent((i + 1) + "");
            dataList.add(hcMessage);
        }
        HCMessage hcMessage1 = new HCMessage();
        hcMessage1.setType(3);
        dataList.add(hcMessage1);
        adapter = new AnimListAdapter(this, dataList);
        listView.setAdapter(adapter);

        listView.smoothScrollToPositionFromTop(0, 20);

        adapter.setAnimRcvListener(new AnimListAdapter.AnimRcvListener() {
            @Override
            public void onClickQuestion(int position) {
                editPosition = position;
                scrollPositionFromTop(position);
            }
        });

        titleShow = findViewById(R.id.title_show);
        etInput = findViewById(R.id.tv_input);
        llInput = findViewById(R.id.ll_input);
        llBottom = findViewById(R.id.ll_bottom);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow() {
                //软键盘弹出
                if (!etInput.hasFocus()) {
                    llBottom.setVisibility(View.GONE);
                }

            }

            @Override
            public void keyBoardHide() {
                //软键盘隐藏
                if (!etInput.hasFocus()) {
                    etInputShowOrHide(true);
                    if (getCurrentFocus() != null) {
                        getCurrentFocus().clearFocus();
                    }
                }
            }
        });
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_list_add:
                adapter.addData();
                break;
            case R.id.btn_list_remove:
                adapter.removeData();
                break;
        }
    }

    private void etInputShowOrHide(boolean toShow) {
        if (toShow) {
            llInput.animate().setDuration(50).translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    llBottom.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        } else {
            llInput.animate().setDuration(20).translationY(llInput.getHeight()).start();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

            if (editPosition != -1) {
                //重置点击坐标
                editPosition = -1;
            }
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
                if (!etInput.hasFocus()) {
                    v.clearFocus();
                }
                return true;
            }
        }
        return false;
    }

    private void scrollPositionFromTop(int position) {
        listView.smoothScrollToPositionFromTop(position, 20);
    }
}
