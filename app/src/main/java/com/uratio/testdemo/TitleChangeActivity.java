package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class TitleChangeActivity extends AppCompatActivity {
    private static final String TAG = "TitleChangeActivity";
    private Context activity;
    private TextView ivLogo;
    private TextView tvPopLogo;
    private int count;

    private Runnable runnableCheck = () -> checkSpace();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            long time = (Long) msg.obj;
            switch (msg.what) {
                case 0:
                    if (System.currentTimeMillis() - time < 200) {
                        handler.removeCallbacks(runnableCheck);
                    }
                    handler.postDelayed(runnableCheck, 200);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_change);
        activity = this;

        ivLogo = findViewById(R.id.ivLogo);
        tvPopLogo = findViewById(R.id.tvPopLogo);

        ivLogo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                message = Message.obtain();
                message.what = 0;
                message.obj = System.currentTimeMillis();
                handler.sendMessage(message);
            }
        });
        tvPopLogo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Message message;

    //??????????????????????????????????????????????????????????????????
    private void checkSpace() {
        int[] locationCenter = new  int[2] ;
        ivLogo.getLocationOnScreen(locationCenter);//???????????????????????????????????????

        int[] locationRight = new  int[2] ;
        tvPopLogo.getLocationOnScreen(locationRight);//???????????????????????????????????????
        Log.e(TAG, "?????????????????????????????????=" + (getLocation(ivLogo)[2] + getLocation(ivLogo)[0]));
        Log.e(TAG, "???????????????????????????=" + getLocation(tvPopLogo)[0]);
        if (getLocation(tvPopLogo)[0] - getLocation(ivLogo)[2] - getLocation(ivLogo)[0] < 10) {
            ivLogo.setTextSize(TypedValue.COMPLEX_UNIT_SP, DensityUtil.px2dip(activity, ivLogo.getTextSize()) - 0.6f);
            tvPopLogo.setTextSize(TypedValue.COMPLEX_UNIT_SP, DensityUtil.px2dip(activity, tvPopLogo.getTextSize()) - 0.6f);

            Log.e(TAG, "???????????? ivLogo.getSize=" + DensityUtil.px2dip(activity, ivLogo.getTextSize()));
            Log.e(TAG, "???????????? tvPopLogo.getSize=" + DensityUtil.px2dip(activity, tvPopLogo.getTextSize()));
            message = Message.obtain();
            message.what = 0;
            message.obj = System.currentTimeMillis();
            handler.sendMessage(message);
        }

        Log.e(TAG, "ivLogo.getSize=" + DensityUtil.px2dip(activity, ivLogo.getTextSize()));
        Log.e(TAG, "tvPopLogo.getSize=" + DensityUtil.px2dip(activity, tvPopLogo.getTextSize()));
    }

//    private void adjustTvTextSize() {
//        String text = getText().toString();
//        int avaiWidth = (int) (maxWidth - getPaddingLeft() - getPaddingRight() - DisplayUtils.dip2px(getContext(), 2));
//        if (avaiWidth <= 0) {
//            return;
//        }
//        TextPaint textPaintClone = new TextPaint(getPaint());
//        float trySize = maxTextSize;
//        textPaintClone.setTextSize(trySize);
//        while (textPaintClone.measureText(text) > avaiWidth && trySize > minTextSize) {
//            trySize--;
//            textPaintClone.setTextSize(trySize);
//        }
//        setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
//    }
//
    private int[] getLocation(View view) {
        int[] loc = new int[4];
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        loc[0] = location[0];
        loc[1] = location[1];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);

        loc[2] = view.getMeasuredWidth();
        loc[3] = view.getMeasuredHeight();
        return loc;
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                Log.e(TAG, "***********************************************************");
                String mid = "";
                String right = "";
                count++;
                switch (count) {
                    case 1:
                        mid = "??????????????????";
                        right = "??????";
                        break;
                    case 2:
                        mid = "?????????????????????";
                        right = "??????";
                        break;
                    case 3:
                        mid = "????????????????????????";
                        right = "?????????";
                        break;
                    case 4:
                        mid = "???????????????????????????";
                        right = "????????????";
                        break;
                    case 5:
                        mid = "??????????????????????????????";
                        right = "????????????";
                        break;
                    case 6:
                        mid = "??????????????????";
                        right = "??????";
                        break;
                    case 7:
                        mid = "?????????????????????????????????";
                        right = "????????????";
                        break;
                    case 8:
                        mid = "?????????????????????????????????";
                        right = "???????????????";
                        break;
                }
                ivLogo.setText(mid);
                tvPopLogo.setText(right);
                break;
        }
    }
}
