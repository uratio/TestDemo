package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uratio.pikers.common.LineConfig;
import com.uratio.pikers.entity.City;
import com.uratio.pikers.entity.County;
import com.uratio.pikers.entity.Province;
import com.uratio.pikers.listeners.OnItemPickListener;
import com.uratio.pikers.listeners.OnLinkageListener;
import com.uratio.pikers.listeners.OnMoreItemPickListener;
import com.uratio.pikers.listeners.OnMoreWheelListener;
import com.uratio.pikers.listeners.OnSingleWheelListener;
import com.uratio.pikers.util.ConvertUtils;
import com.uratio.pikers.util.DateDeal;
import com.uratio.pikers.util.DateUtils;
import com.uratio.pikers.util.LogUtils;
import com.uratio.pikers.util.TimeUtils;
import com.uratio.pikers.widget.AddressPicker;
import com.uratio.pikers.widget.AddressPicker2;
import com.uratio.pikers.widget.CarNumberPicker;
import com.uratio.pikers.widget.DatePicker;
import com.uratio.pikers.widget.DateRangePicker;
import com.uratio.pikers.widget.DateTimePicker;
import com.uratio.pikers.widget.LinkagePicker;
import com.uratio.pikers.widget.NumberPicker;
import com.uratio.pikers.widget.SinglePicker;
import com.uratio.pikers.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PickersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickers);
    }

    private String provinceStr = "河北省", cityStr = "唐山市", countyStr = "开平区";


    /**
     * 车牌
     */
    public void CarNumber(View view) {
        CarNumberPicker picker = new CarNumberPicker(this);
        picker.setWheelModeEnable(false);
        picker.setWeightEnable(true);
        picker.setGravity(Gravity.BOTTOM);
        picker.setColumnWeight(0.5f,0.5f,1);
        picker.setTextSize(18);
        picker.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setCanLoop(true);
        picker.setOffset(3);
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {
            @Override
            public void onItemPicked(String s1, String s2, String s3) {
                s3 = !TextUtils.isEmpty(s3) ? ",item3: "+s3 : "";
                showToast("item1: "+s1 +",item2: "+s2+ s3);
            }
        });
        picker.setOnMoreWheelListener(new OnMoreWheelListener() {
            @Override
            public void onFirstWheeled(int index, String item) {
                Loge(item + ":" + picker.getSelectedSecondItem());

            }

            @Override
            public void onSecondWheeled(int index, String item) {
                Loge(picker.getSelectedFirstItem() + ":" + item);
            }

            @Override
            public void onThirdWheeled(int index, String item) {

            }
        } );
        picker.show();
    }

    /**
     * 日期范围选择
     */
    public void onDateRangePicker(View view) {
        final DateRangePicker picker = new DateRangePicker(this);
        picker.setWheelModeEnable(true);
        picker.setTopLineVisible(false);
        LineConfig lineConfig = new LineConfig();
        lineConfig.setColor(ContextCompat.getColor(this, R.color.line_graycd));
        lineConfig.setDividerType(LineConfig.DividerType.FILL);
        picker.setLineConfig(lineConfig);
        picker.setGravity(Gravity.BOTTOM);
//        picker.setDateRangeStart(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
//            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));//09:00
//        picker.setBackgroundRes(com.framework.base.R.drawable.bg_white_radius12);
//        picker.setWheelModeEnable(true);
//        picker.setWeightEnable(true);
        picker.setOuterLabelEnable(false);
        picker.setSelectedTextColor(ContextCompat.getColor(this, R.color.btn_orange));//前四位值是透明度
        picker.setUnSelectedTextColor(ContextCompat.getColor(this, R.color.material_gray));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //调整为当前时间
        picker.setSelectedItem(year, month, day);
        picker.setOnDateTimePickListener(new DateRangePicker.OnYearMonthDayRangePickListener() {
            @Override
            public void onDateTimePicked(String year, String monthStart, String dayStart, String endYear, String monthEnd, String dayEnd) {
                String startDate=year + "-" + monthStart + "-" + dayStart+" 00:00:00";
                String endDate=endYear + "-" + monthEnd + "-" + dayEnd+" 23:59:59";
                long sTime = TimeUtils.string2Date(startDate, DateDeal.YYYY_MM_dd_HH_MM_SS).getTime();
                long eTime = TimeUtils.string2Date(endDate, DateDeal.YYYY_MM_dd_HH_MM_SS).getTime();
                if(eTime<sTime){
                    showToast("结束日期不能小于开始日期");
                }else{
                    showToast(year+"年"+monthStart+"月"+dayStart+"日-"+endYear+"年"+monthEnd+"月"+dayEnd+"日");
                }
                picker.dismiss();
            }
        });
        picker.show();
    }

    /**
     * 年月日选择
     */
    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setTopPadding(15);
        picker.setRangeStart(2016, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(2050, 10, 14);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setGravity(Gravity.BOTTOM);
        picker.setWheelModeEnable(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 年月日时间选择
     */
    public void onYearMonthDayTimePicker(View view) {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setActionButtonTop(true);
        picker.setDateRangeStart(2017, 1, 1);
        picker.setDateRangeEnd(2025, 11, 11);
        picker.setSelectedItem(2018,6,16,0,0);
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
        picker.setCanLinkage(false);
        picker.setTitleText("请选择");
        picker.setStepMinute(5);
        picker.setGravity(Gravity.BOTTOM);
        picker.setWeightEnable(true);
        picker.setCanceledOnTouchOutside(true);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
        config.setVisible(true);//线不显示 默认显示
        picker.setLineConfig(config);
        picker.setOuterLabelEnable(true);
//        picker.setLabel(null,null,null,null,null);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    /**
     * 年月选择
     */
    public void onYearMonthPicker(View view) {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        picker.setWheelModeEnable(false);
        picker.setGravity(Gravity.BOTTOM);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));//设置弹窗宽度
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(2017, 9);
        picker.setCanLinkage(true);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                showToast(year + "-" + month);
            }
        });
        picker.show();
    }

    /**
     * 月日选择
     */
    public void onMonthDayPicker(View view) {
        DatePicker picker = new DatePicker(this, DatePicker.MONTH_DAY);
        picker.setWheelModeEnable(true);
        picker.setGravity(Gravity.BOTTOM);//弹框居中
        picker.setCanLoop(false);
        picker.setWeightEnable(true);
        picker.setCanLinkage(true);
        LineConfig lineConfig = new LineConfig();
        lineConfig.setColor(Color.GREEN);
        picker.setLineConfig(lineConfig);
//        picker.setLineColor(Color.BLACK);
        picker.setRangeStart(5, 1);
        picker.setRangeEnd(12, 31);
        picker.setSelectedItem(10, 14);
        picker.setOnDatePickListener(new DatePicker.OnMonthDayPickListener() {
            @Override
            public void onDatePicked(String month, String day) {
                showToast(month + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 时间选择
     */
    public void onTimePicker(View view) {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setWheelModeEnable(true);
        picker.setGravity(Gravity.BOTTOM);//弹框居中
        picker.setRangeStart(9, 0);//09:00
        picker.setRangeEnd(18, 0);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                showToast(hour + ":" + minute);
            }
        });
        picker.show();
    }

    /**
     * 单项选择
     */
    public void onOptionPicker(View view) {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i<10; i++){
            String s = "";
            if(i<10){
                s = "0"+i;
            }else{
                s = i+"";
            }
            list.add(s);
        }
//        String[] ss = (String[]) list.toArray();
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setWheelModeEnable(false);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setTextSize(18);
        picker.setSelectedIndex(2);
        //启用权重 setWeightWidth 才起作用
        picker.setLabel("分");
        picker.setItemWidth(100);
//        picker.setWeightEnable(true);
//        picker.setWeightWidth(1);
        picker.setOuterLabelEnable(true);
        picker.setSelectedTextColor(Color.GREEN);//前四位值是透明度
        picker.setUnSelectedTextColor(Color.BLACK);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {
                showToast("index=" + index + ", item=" + item);
            }
        });
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    /**
     * 二三级联动选择
     */
    public void onLinkagePicker(View view) {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

            @Override
            public boolean isOnlyTwo() {
                return true;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("12");
                firstList.add("24");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                ArrayList<String> secondList = new ArrayList<>();
                for (int i = 1; i <= (firstIndex == 0 ? 12 : 24); i++) {
                    String str = DateUtils.fillZero(i);
//                    if (firstIndex == 0) {
//                        str += "￥";
//                    } else {
//                        str += "$";
//                    }
                    secondList.add(str);
                }
                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                return null;
            }

        };
        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setWheelModeEnable(false);
        picker.setCanLoop(false);
        picker.setLabel("小时制", "点");
        picker.setSelectedIndex(0, 8);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                showToast(first + "-" + second);
            }
        });
        picker.show();
    }

    /**
     * 星座选择
     */
    public void onConstellationPicker(View view) {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        SinglePicker<String> picker = new SinglePicker<>(this,
                isChinese ? new String[]{
                        "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
                        "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                });
        picker.setCanLoop(false);//不禁用循环
        picker.setWheelModeEnable(true);
        picker.setTopBackgroundColor(0xFFEEEEEE);
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);
        picker.setTitleTextSize(12);
        picker.setCancelTextColor(0xFF33B5E5);
        picker.setCancelTextSize(13);
        picker.setSubmitTextColor(0xFF33B5E5);
        picker.setSubmitTextSize(13);
        picker.setSelectedTextColor(0xFFEE0000);
        picker.setUnSelectedTextColor(0xFF999999);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
//        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(7);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    /**
     * 数字选择（如身高、体重、年龄）
     */
    public void onNumberPicker(View view) {
        NumberPicker picker = new NumberPicker(this);
        picker.setWheelModeEnable(true);
        picker.setWidth(picker.getScreenWidthPixels() / 2);
        picker.setCanLoop(false);
        picker.setLineVisible(false);
        picker.setOffset(2);//偏移量
        picker.setRange(145, 200, 1);//数字范围
        picker.setSelectedItem(172);
        picker.setLabel("厘米");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                showToast("index=" + index + ", item=" + item.intValue());
            }
        });
        picker.show();
    }

    /**
     * 省市县选择（方式一）
     */
    public void ProvinceCityCountry(View view) {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker picker = new AddressPicker(this, data);
            picker.setCanLoop(false);
            picker.setHideProvince(false);
            picker.setWheelModeEnable(false);
            picker.setGravity(Gravity.BOTTOM);
            picker.setOffset(1);
            picker.setSelectedItem(provinceStr, cityStr, countyStr);
            picker.setOnLinkageListener(new OnLinkageListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    showToast("province : " + province + ", city: " + city + ", county: " + county);
                    provinceStr = province.getAreaName();
                    cityStr = city.getAreaName();
                    countyStr = county.getAreaName();
                }
            });
            picker.show();
        } catch (Exception e) {
            showToast(LogUtils.toStackTraceString(e));
        }
    }

    /**
     * 省市县选择（方式二）
     */
    public void ProvinceCityCountry2(View view) {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker2 picker = new AddressPicker2(this, data);
            picker.setCanLoop(false);
            picker.setHideProvince(false);
            picker.setWheelModeEnable(false);
            picker.setGravity(Gravity.BOTTOM);
            picker.setOffset(2);
            picker.setSelectedItem(provinceStr, cityStr, countyStr);
            picker.setOnLinkageListener(new OnLinkageListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    showToast("province : " + province + ", city: " + city + ", county: " + county);
                    provinceStr = province.getAreaName();
                    cityStr = city.getAreaName();
                    countyStr = county.getAreaName();
                }
            });
            picker.show();
        } catch (Exception e) {
            showToast(LogUtils.toStackTraceString(e));
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void Loge(String msg) {
        Log.e("data", msg);
    }
}
