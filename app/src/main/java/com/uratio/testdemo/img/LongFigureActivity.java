package com.uratio.testdemo.img;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uratio.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class LongFigureActivity extends AppCompatActivity {
    private RecyclerView rcvLongFigure;
    private LongFigureAdapter adapter;
    private List<Bitmap> dataList = new ArrayList<>();

    private BigView bigView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    handler.sendEmptyMessageDelayed(1, 1000);
                    initDataList();
                    break;
                case 1:
                    initDataList();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private Runnable fistRun = new Runnable() {
        @Override
        public void run() {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_long_figure);
            bitmap = resizeImage(bitmap, getResources().getDisplayMetrics().widthPixels);
            int heightPixels = getResources().getDisplayMetrics().heightPixels;
            Bitmap newBM = null;
            if (heightPixels < bitmap.getHeight()) {
                newBM = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), heightPixels);
            } else {
                newBM = bitmap;
            }
            dataList.clear();
            dataList.add(newBM);
            handler.sendEmptyMessage(2);
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_figure);


        rcvLongFigure = findViewById(R.id.rcv_long_figure);
        rcvLongFigure.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LongFigureAdapter(dataList, this);
        rcvLongFigure.setAdapter(adapter);


        bigView = findViewById(R.id.big_view);


//        handler.post(fistRun);
//        handler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handler.sendEmptyMessage(1);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_title_load_data:
                rcvLongFigure.setVisibility(View.VISIBLE);
                bigView.setVisibility(View.GONE);
                initDataList();
                break;
            case R.id.btn_load_to_big_view:
                rcvLongFigure.setVisibility(View.GONE);
                bigView.setVisibility(View.VISIBLE);
                bigView.setImage(getResources().openRawResource(R.drawable.ic_long_figure_4));
                break;
        }
    }

    private void initDataList() {
        int viewH = rcvLongFigure.getHeight();
        Log.e("data", "onClickView: ??????_width=" + getResources().getDisplayMetrics().widthPixels);
        Log.e("data", "onClickView: ??????_height=" + getResources().getDisplayMetrics().heightPixels);
        Log.e("data", "onClickView: rcv_height=" + viewH);
        new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_long_figure_4);
//                Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ic_long_figure_4));
//                Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.ic_long_figure_4, 2);
                Log.e("data", "onClickView: ???bitmap_size=" + bitmap.getByteCount());
                Log.e("data", "onClickView: ??? ????????????=" + bitmap.getAllocationByteCount() / 1024 / 1024);
                bitmap = resizeImage(bitmap, getResources().getDisplayMetrics().widthPixels);
                Log.e("data", "onClickView: ???bitmap_size=" + bitmap.getByteCount());
                Log.e("data", "onClickView: ??? ????????????=" + bitmap.getAllocationByteCount() / 1024 / 1024);
                int sumH = bitmap.getHeight();
                boolean haveTail = sumH % viewH > 0;
                int size = sumH / viewH + (haveTail ? 1 : 0);
                Bitmap newBM = null;
                dataList.clear();
                for (int i = 0; i < size; i++) {
                    if (haveTail && i == size - 1) {
                        newBM = Bitmap.createBitmap(bitmap, 0, viewH * i, bitmap.getWidth(), sumH % viewH);
                    } else  {
                        newBM = Bitmap.createBitmap(bitmap, 0, viewH * i, bitmap.getWidth(), viewH);
                    }
                    dataList.add(newBM);
                }
                LongFigureActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /**
     * ???????????????????????????
     */
    public static Bitmap resizeImage(Bitmap bitmap, int w) {
        // ?????????bitmap
        // ???????????????
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // ?????????????????????
        // ?????????????????????
        float scaleWidth = ((float) w) / width;
        float scaleHeight = scaleWidth * height;
        // ?????????????????????
        Matrix matrix = new Matrix();
        // ????????????
        matrix.postScale(scaleWidth, scaleWidth);
        // ?????????????????????
        // matrix.postRotate(45);
        // ????????????bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int inSampleSize) {

        // ???????????? inJustDecodeBounds ???true?????????????????????inSample?????????????????????inJustDecodeBounds???false????????????????????????
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // ??????inSample??????
        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Bitmap bitmap = dataList.get(i);
                if (bitmap != null && !bitmap.isRecycled()){
                    // ??????????????????null
                    bitmap.recycle();
                    bitmap = null ;
                }
            }
            dataList.clear();
            System.gc();
        }
    }
}
