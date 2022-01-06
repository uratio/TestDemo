package com.uratio.testdemo.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {
    private TextView tvText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvText = findViewById(R.id.tv_text);
        button = findViewById(R.id.button);


        Observable observable = Observable.just("hello", "hi", "Aloha");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        final int drawables = 0;
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Drawable> emitter) throws Exception {
                Drawable drawable = getResources().getDrawable(drawables);
                emitter.onNext(drawable);
            }
        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull Drawable drawable) {

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observable.just("image/logo.png")
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) {
//                        return getBitmapFromPath(filePath);
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//
//                    }
//                });

        Student[] students = new Student[3];
        Observer<Course> observer = new Observer<Course>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull Course course) {

            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        /**
         * 有问题
         */
        Observable.just(students)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Student[], ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NotNull Student[] students) throws Exception {
                        return Observable.just(students);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe((Consumer<? super Object>) observer);

        //防抖动
        RxUtils.clickView(button)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        //点击了button
                    }
                });
    }
}
