package com.uratio.tools.widget;

import android.app.Activity;

/**
 * @author lang
 * @data 2020/11/5
 */
public class AddressDialog extends BaseDialog {

    private String selectFirstItem, selectSecondItem, selectThirdItem = "";
    private int selectFirstIndex, selectSecondIndex, selectThirdIndex = 0;

    public AddressDialog(Activity activity) {
        super(activity);
    }


    /**
     * 设置默认选中的省市县
     */
    public void setSelectedItem(String selectFirstItem, String selectSecondItem, String selectThirdItem) {

    }

    /**
     * 设置默认选中的省市县
     */
    public void setSelectedItem(int selectFirstIndex, int selectSecondIndex, int selectThirdIndex) {

    }

}
