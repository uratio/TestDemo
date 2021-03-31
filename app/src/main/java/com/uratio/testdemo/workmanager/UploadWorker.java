package com.uratio.testdemo.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {
    private static final String TAG = "UploadWorker";

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        uploadImages();

        // Indicate whether the work finished successfully with the Result
        /**
         * Result.success()：工作成功完成。
         * Result.failure()：工作失败。
         * Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
         */
        return Result.success();
    }

    /**
     * 上传图片
     */
    private void uploadImages() {
        Log.e(TAG, "uploadImages: ***********");
    }
}
