package com.uratio.testdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lang
 * @data 2021/3/10
 */
public class Test extends AppCompatActivity {
    private ImageView ivImg;
    private String url = "/storage/emulated/0/test.jpg";
    private String png_url = "/storage/emulated/0/test_png.png";
    private Integer resId = R.drawable.ic_long_figure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(0);
        initView();
        checkPermission();

        looadBitmap(ivImg, R.drawable.ic_long_figure);
        looadBitmap(ivImg, url);//3.4332275mb

        //1.设置bitmap格式压缩(内存，也可将压缩的bitmap写入磁盘造成磁盘压缩)
        Bitmap bitmap = compressFormatBitmap(Bitmap.Config.RGB_565, R.drawable.ic_long_figure);
        logBitmapInfo(bitmap);
        ivImg.setImageBitmap(bitmap);

        //2.质量压缩(磁盘文件压缩)
        compressFileQuality(new File(url), Bitmap.CompressFormat.JPEG,0);//压缩jpg
        compressFileQuality(new File(png_url), Bitmap.CompressFormat.PNG, 0);//压缩png无效

        //3.设置采样率压缩(内存，磁盘都会压缩)
        compressInSampleSize(new File(url),2);//2  0.8583069mb //锯齿效果好一点

        //4.设置缩放压缩(内存，磁盘都会压缩)
        compressScale(new File(url),2);//2 0.8583069mb 锯齿明显
    }

    private void initView() {
//        ivImg = (ImageView) findViewById(R.id.iv_img);
    }


    //1.采取转换不同位图格式的方式压缩bitmap大小(内存压缩，也可写入磁盘，使磁盘文件压缩)
    private Bitmap compressFormatBitmap(Bitmap.Config config, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;//将Config设为RGB565
        options.inPreferredConfig = config;//它表示的就是每个像素点对ARGB通道值的存储方案
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    /**
     * 2.质量压缩(磁盘文件压缩)
     * 质量压缩的原理是通过改变图片的位深和透明度来减小图片占用的磁盘空间大小---由于png是无损压缩，所以设置quality无效
     *
     * @param format  图片格式 jpeg,png,webp
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    public static void compressFileQuality(File file, Bitmap.CompressFormat format, int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, quality, byteArrayOutputStream);
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File(file.getParentFile().getAbsolutePath() + "/compress_" + file.getName()));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3.采样率压缩(内存，磁盘都会压缩)

    /**
     * @param file
     * @param inSampleSize inSampleSize建议设置为2的平方(网上一些说不设2的平方，最终会自动转接近值的2的平方，测试发现除了小于1会最终转成1，其他值都能正常压缩，不过还是建议设置成2的平方
     *                     (效果好))
     */
    private void compressInSampleSize(File file, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true;
        Bitmap emptyBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;//设置采样率
        Bitmap resultBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        logBitmapInfo(resultBitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File(file.getParentFile().getAbsolutePath() + "/compress_" + file.getName()));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 4.缩放压缩(内存，磁盘都会压缩)
     *
     * @param file
     * @param radio 缩放比（1/radio）
     */
    public void compressScale(File file, int radio) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / radio, bitmap.getHeight() / radio,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0, 0, bitmap.getWidth() / radio, bitmap.getHeight() / radio);
        //将原图画在缩放之后的矩形上
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        logBitmapInfo(result);
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File(file.getParentFile().getAbsolutePath() + "/compress_" + file.getName()));
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //打印bitmap信息
    private void logBitmapInfo(Bitmap bitmap) {
        Log.i("logBitmapInfo-----", "width: " + bitmap.getWidth() + "  height : " + bitmap.getHeight());
        float bitmapSize = ((float) bitmap.getAllocationByteCount()) / 1024 / 1024;
        Log.i("MlogBitmapInfo-----", "内存大小  " + bitmapSize);
    }

    //探究bitmap引用本地磁盘图片的大小
    private void looadBitmap(ImageView imageView, String fielUrl) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(fielUrl, options);
        float bitmapSize = ((float) bitmap.getAllocationByteCount()) / 1024 / 1024;
        imageView.setImageBitmap(bitmap);
        Log.i("MainActivity-----", "width: " + bitmap.getWidth() + "  height : " + bitmap.getHeight());
        Log.i("MainActivity-----", "屏幕密度: " + options.inDensity);//0
        Log.i("MainActivity-----", "内存大小  " + bitmapSize);
    }

    //探究bitmap引用res资源图片的大小
    private void looadBitmap(ImageView imageView, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        float bitmapSize = ((float) bitmap.getAllocationByteCount()) / 1024 / 1024;
        imageView.setImageBitmap(bitmap);
        Log.i("MainActivity-----", "width: " + bitmap.getWidth() + "  height : " + bitmap.getHeight());
        Log.i("MainActivity-----", "屏幕密度: " + options.inDensity);//该Bitmap适合的屏幕Dpi(当目标屏幕的Dpi不等于它时，会缩放以适应目标机器,
        // 例当前屏幕dpi=320,强制使用mdpi(160)中的图片，该图片会自动放大1倍)
        Log.i("MainActivity-----", "内存大小  " + bitmapSize);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//适配6.0权限
            if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 1);
            }
        }
    }

    //现在的手机屏幕dpi计算=根号(width^2+height^2)/inch*1.139 （1.139测试中粗略换算的,以前的的公式计算的dpi和现在手机设置的dpi总有1.139倍的差距）
}
