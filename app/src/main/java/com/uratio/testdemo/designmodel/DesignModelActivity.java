package com.uratio.testdemo.designmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.uratio.testdemo.R;
import com.uratio.testdemo.designmodel.agency.DynamicProxy;
import com.uratio.testdemo.designmodel.agency.Peter;
import com.uratio.testdemo.designmodel.agency.ProxyInterface;
import com.uratio.testdemo.designmodel.observer.AndroidObservable;
import com.uratio.testdemo.designmodel.observer.PersonObserver;
import com.uratio.testdemo.designmodel.observer.WeatherObservable;
import com.uratio.testdemo.designmodel.observer.WeatherObserver;
import com.uratio.testdemo.designmodel.tactics.BeijingAverageRent;
import com.uratio.testdemo.designmodel.tactics.CalculationAverageRent;
import com.uratio.testdemo.designmodel.tactics.ShenzhenAverageRent;

import java.lang.reflect.Proxy;

public class DesignModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_model);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.observer:
                PersonObserver xiaoMing = new PersonObserver("xiaoMing");
                PersonObserver xiaoDong = new PersonObserver("xiaoDong");
                PersonObserver xiaoHong = new PersonObserver("xiaoHong");

                // 添加到集合里。
                AndroidObservable android = new AndroidObservable();
                android.addObserver(xiaoMing);
                android.addObserver(xiaoDong);
                android.addObserver(xiaoHong);
                // 通知
                android.postNewVersion("android O updated!");
                android.postNewVersion("android P updated!");
                break;
            case R.id.observer2:
                WeatherObservable weatherObservable = new WeatherObservable();
                weatherObservable.add(xiaoDong2);
                weatherObservable.add(xiaoMing2);

                weatherObservable.rain();
                weatherObservable.sun();
                weatherObservable.typhoon();
                break;
            case R.id.agency:
                Peter peter = new Peter();
                ProxyInterface proxyPeter =
                        (ProxyInterface) Proxy.newProxyInstance(peter.getClass().getClassLoader(),
                                peter.getClass().getInterfaces(), new DynamicProxy(peter));
                proxyPeter.choiceBetterHouse();
                proxyPeter.buyHouse();
                break;
            case R.id.tactics:
                ShenzhenAverageRent shenzhenAverageRent = new ShenzhenAverageRent();
                BeijingAverageRent beijingAverageRent = new BeijingAverageRent();
                CalculationAverageRent calculationAverageRent = new CalculationAverageRent();
                
                calculationAverageRent.setCalculation(shenzhenAverageRent);
                int shenzhen = calculationAverageRent.getAverageRent();

                calculationAverageRent.setCalculation(beijingAverageRent);
                int beijing = calculationAverageRent.getAverageRent();

                Log.e("Strategy","shenzhen average rent  = " + shenzhen);
                Log.e("Strategy","beijing average rent  = " + beijing);
                break;
        }
    }

    private WeatherObserver xiaoDong2 = new WeatherObserver() {
        @Override
        public void typhoon() {
            Log.e("小东", "吹台风，要放假了。哈哈！");
        }

        @Override
        public void sun() {
            Log.e("小东", "天晴了！");
        }

        @Override
        public void rain() {
            Log.e("小东", "下雨了，带伞");
        }
    };

    private WeatherObserver xiaoMing2 = new WeatherObserver() {

        @Override
        public void sun() {
            Log.e("小明", "天晴了！");
        }

        @Override
        public void rain() {
            Log.e("小明", "下雨了，带伞");
        }
    };
}
