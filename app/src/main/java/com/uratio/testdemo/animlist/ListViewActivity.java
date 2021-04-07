package com.uratio.testdemo.animlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uratio.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private ListViewForScrollView listView;
    private RecyclerView rvAdd;
    private LinearLayout llContainer;
    private FlowLayout flowLayout;
    private FlowLayout flowLayout2;
    private ListViewAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private RvAddAdapter rvAdapter;
    private List<String> dataRv = new ArrayList<>();

    private List<String> list = new ArrayList<>();
    private int mViewCount = 0;
    private boolean isTransitionFinish = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    View flowView2 = View.inflate(ListViewActivity.this, R.layout.item_anim_flow_view, null);
                    TextView tvContent = flowView2.findViewById(R.id.tv_content);
                    tvContent.setText(list.get(flowLayout2.getChildCount()));
//                    Animation animation = AnimationUtils.loadAnimation(ListViewActivity.this, R.anim.anim_item_in);
//                    flowView2.startAnimation(animation);

                    flowLayout2.addView(flowView2);
                    if (flowLayout2.getChildCount() < 6) {
                        sendEmptyMessageDelayed(0, 100);
                    }
                    break;
                case 1:
                    dataRv.add("123");
                    rvAdapter.notifyDataSetChanged();
                    if (dataRv.size() < 6) {
                        sendEmptyMessageDelayed(1, 100);
                    }
                    break;
                case 2:
                    dataList.add("111");
                    adapter.notifyDataSetChanged();
                    if (dataList.size() < 6) {
                        sendEmptyMessageDelayed(2, 100);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        listView = findViewById(R.id.listView);
        llContainer = findViewById(R.id.ll_container);
        flowLayout = findViewById(R.id.flow_layout);
        flowLayout2 = findViewById(R.id.flow_layout2);
        rvAdd = findViewById(R.id.rv_add);

        adapter = new ListViewAdapter(this, dataList);
        listView.setAdapter(adapter);




        rvAdd.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new RvAddAdapter(this, dataRv);
        rvAdd.setAdapter(rvAdapter);

        list.add("查询转账");
        list.add("我要买理财");
        list.add("帮我转账给xx");
        list.add("我要买缴费");
        list.add("我要买基金");
        list.add("热门活动");

        // 重写动画
        MyItemAnimator myItemAnimator = new MyItemAnimator();
//        myItemAnimator.setAddDuration(500);
        myItemAnimator.setAlphaDuration(1000);
        myItemAnimator.setTranslationDuration(1000);
        myItemAnimator.setRemoveDuration(10);
        rvAdd.setItemAnimator(myItemAnimator);

        initAnim();
    }

    private void initAnim() {
        LayoutTransition mTransition = new LayoutTransition();
        mTransition.setDuration(300);
//        mTransition.setDuration(LayoutTransition.CHANGE_APPEARING,200);
//        mTransition.setDuration(LayoutTransition.CHANGE_DISAPPEARING,200);
//        mTransition.setDuration(LayoutTransition.APPEARING,200);
//        mTransition.setDuration(LayoutTransition.DISAPPEARING,200);
        //-----------------------设置动画--------------------
        mTransition.setAnimator(LayoutTransition.APPEARING, getInAnim());
//        mTransition.setAnimator(LayoutTransition.DISAPPEARING,getOutAnim());
//        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,getChangeAnimation());
        //---------------------------------------------------
        mTransition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
        mTransition.setStartDelay(LayoutTransition.APPEARING, 0);
        mTransition.setStartDelay(LayoutTransition.DISAPPEARING, 0);
        mTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
        //----viewgroup绑定----
        llContainer.setLayoutTransition(mTransition);
        flowLayout.setLayoutTransition(mTransition);
    }

    private Animator getInAnim() {
//        AnimatorSet mSet = new AnimatorSet();
//        mSet.playTogether(
////                ObjectAnimator.ofFloat(null, "ScaleX", 1.0f, 0f),
////                ObjectAnimator.ofFloat(null, "ScaleY", 1.0f, 0f),
//                ObjectAnimator.ofFloat(null, "Alpha", 0.0f, 1.0f),
//                ObjectAnimator.ofFloat(null,"translationY",400,0)
//        );
//        return mSet;

        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 0);
        PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX", 100f, 0f);
        PropertyValuesHolder trY = PropertyValuesHolder.ofFloat("translationY", 100f, 0f);
        PropertyValuesHolder trAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
//        return ObjectAnimator.ofPropertyValuesHolder(this,trY,trAlpha,trX);
//        return ObjectAnimator.ofPropertyValuesHolder(this, trY, pvhLeft, pvhTop, pvhRight, pvhBottom);
        return ObjectAnimator.ofPropertyValuesHolder(this, trY);
    }

    private Animator getChangeAnimation() {
        PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX", 100f, 0f);
        PropertyValuesHolder trY = PropertyValuesHolder.ofFloat("translationY", 400f, 0f);
        PropertyValuesHolder trAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
//        return ObjectAnimator.ofPropertyValuesHolder(this,trY,trAlpha,trX);
        return ObjectAnimator.ofPropertyValuesHolder(this, trY);
    }

    private Animator getOutAnim() {
        PropertyValuesHolder trY2 = PropertyValuesHolder.ofFloat("translationY", 0f, -100f);
        PropertyValuesHolder trX = PropertyValuesHolder.ofFloat("translationX", 0f, 0f);
        PropertyValuesHolder trAlpha2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        return ObjectAnimator.ofPropertyValuesHolder(this, trY2, trAlpha2, trX);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_list_init:
                dataList.clear();
                adapter.notifyDataSetChanged();
                for (int i = 0; i < 6; i++) {
                    dataList.add("111");
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_list_add:
                if (dataList.size() > 5) {
                    dataList.clear();
                }
                handler.sendEmptyMessageDelayed(2, 100);
                break;
            case R.id.btn_ll_add:
                if (llContainer.getChildCount() > 3) {
                    llContainer.removeAllViews();
                }
                View convertView = View.inflate(ListViewActivity.this, R.layout.item_anim_ll_view, null);
                TextView textView = convertView.findViewById(R.id.tv_content);
                textView.setText("中间填充文字 ：：： " + (llContainer.getChildCount() + 1));

//                convertView.setTranslationY(convertView.getHeight());
//                convertView.animate().setDuration(500).translationY(0);
                llContainer.addView(convertView);
                break;
            case R.id.btn_flow_add:
                if (flowLayout.getChildCount() > 5) {
                    flowLayout.removeAllViews();
                }
                for (int i = 0; i < list.size(); i++) {
                    View flowView = View.inflate(ListViewActivity.this, R.layout.item_anim_flow_view, null);
                    TextView tvContent = flowView.findViewById(R.id.tv_content);
                    tvContent.setText(list.get(i));

                    flowLayout.addView(flowView);
                }
                break;
            case R.id.btn_flow2_add:
                if (flowLayout2.getChildCount() > 5) {
                    flowLayout2.removeAllViews();
                }
//                for (int i = 0; i < list.size(); i++) {
//                    View flowView2 = View.inflate(ListViewActivity.this, R.layout.item_anim_flow_view, null);
//                    TextView tvContent = flowView2.findViewById(R.id.tv_content);
//                    tvContent.setText(list.get(i));
//                    Animation animation = AnimationUtils.loadAnimation(ListViewActivity.this, R.anim.anim_item_in);
//                    flowView2.startAnimation(animation);
//
//                    flowLayout2.addView(flowView2);
//                }
                handler.sendEmptyMessage(0);
                break;
            case R.id.btn_flow2_add2:
                if (flowLayout2.getChildCount() > 5) {
                    flowLayout2.removeAllViews();
                }
                for (int i = 0; i < list.size(); i++) {
                    View flowView2 = View.inflate(ListViewActivity.this, R.layout.item_anim_flow_view, null);
                    TextView tvContent = flowView2.findViewById(R.id.tv_content);
                    tvContent.setText(list.get(i));

                    flowLayout2.addView(flowView2);
                }
                break;
            case R.id.btn_rv_add:
                if (dataRv.size() > 5) {
                    dataRv.clear();
                }
//                dataRv.add("123");
//                rvAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
                break;
            case R.id.btn_rv_clear:
                dataRv.clear();
                rvAdapter.notifyDataSetChanged();
                break;
        }
    }
}
