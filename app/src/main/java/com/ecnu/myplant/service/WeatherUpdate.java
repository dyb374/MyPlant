package com.ecnu.myplant.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.ecnu.myplant.MainActivity;

import java.util.Date;

/**
 * Created by qyy on 2018/6/9.
 */

public class WeatherUpdate extends Service {

    public AlarmManager alarmManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //真机测试加注释语句
                // String city = MainActivity.city.replace("市","");
                CustomWeatherReport.getWeatherJSON("上海");               //真机用city替代
                CustomWeatherReport.getSimpleWeatherJSON();
                MainActivity.weather = CustomWeatherReport.JSONToWeather();
            }
        }).start();
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int five = 24 * 60 * 60 * 1000;                                        // 一天
        long triggerAtTime = SystemClock.elapsedRealtime() + five;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0 ,i ,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

}


