package com.uratio.tools.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.uratio.tools.R;

/**
 * @author lang
 * @data 2020/11/6
 */
public class BaseDialog {
    private Dialog dialog;
    protected Activity activity;

    protected String selectFirstItem, selectSecondItem, selectThirdItem = "";
    private int selectFirstIndex, selectSecondIndex, selectThirdIndex = 0;

    public BaseDialog(Activity activity) {
        this.activity = activity;

        dialog = new Dialog(activity, R.style.ShareDialogStyle);
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        layout.setMinimumWidth(display.getWidth());
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.LEFT);


        initView(layout);
    }

    private void initView(View view) {

    }

    public void show() {
        if (activity == null || activity.isFinishing()) return;
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (activity == null || activity.isFinishing()) return;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
