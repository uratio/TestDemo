package com.uratio.testdemo.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
        observable.subscribe(new Observer() {

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {

            }
        });

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {

            }
        };
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction);
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);


        final int drawables = 0;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawables);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {

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
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {

            }
        };
        Observable.from(students)
                .subscribeOn(Schedulers.immediate())
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribeOn(Schedulers.test())
                .subscribe(subscriber);

        //防抖动
        RxUtils.clickView(button)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //点击了button
                    }
                });
    }
}
