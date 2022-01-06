package com.uratio.testdemo.rxjava;

import android.view.View;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by wangkangfei on 17/4/21.
 */

public class RxUtils {

    public static Observable<Void> clickView(@NonNull View view) {
        checkNoNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * 查空
     */
    private static <T> void checkNoNull(T value) {
        if (value == null) {
            throw new NullPointerException("generic value here is null");
        }
    }

    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<Void> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@NotNull ObservableEmitter<Void> emitter) throws Exception {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //订阅没取消
                    if (!emitter.isDisposed()) {
                        //发送消息
                        emitter.onNext(null);
                    }
                }
            };
            view.setOnClickListener(onClickListener);
        }
    }
}
