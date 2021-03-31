package com.uratio.pikers.listeners;

import com.uratio.pikers.entity.City;
import com.uratio.pikers.entity.County;
import com.uratio.pikers.entity.Province;

/**
 * @author matt
 * blog: addapp.cn
 */

public interface OnLinkageListener {
    /**
     * 选择地址
     *
     * @param province the province
     * @param city    the city
     * @param county   the county ，if {@code hideCounty} is true，this is null
     */
    void onAddressPicked(Province province, City city, County county);
}
