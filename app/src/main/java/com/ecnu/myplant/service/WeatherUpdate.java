package com.ecnu.myplant.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.ecnu.myplant.MainActivity;
import com.ecnu.myplant.db.MyPlant;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.gson.Weather;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by qyy on 2018/6/9.
 */

public class WeatherUpdate extends Service {
    public static Weather weather;                                                 //每隔一天更新此实例
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
                weather = CustomWeatherReport.JSONToWeather();
                //根据天气数据修改数据库
                double temp = Double.parseDouble(weather.temp);
                double humidity = Double.parseDouble(weather.humidity.substring(0,weather.humidity.indexOf("%")));
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps){
                        int id = mp.getId();
                        MyPlant mp2 = new MyPlant();
                        int newWaterNum = (int)((double)mp.getWaterContent() * (1 - temp / 100));
                        int newFertilizerNum = (int)((double)mp.getLeafCondition() * (humidity / 100));
                        int newPestNum = (int)((double)mp.getPestsContent() * (1 - temp / 100));
                        int newSoilNum = (int)((double)mp.getSoilFertility() * (1 - temp / 500 - (100 - humidity) / 500));
                        int newLevel = (int)(((double)mp.getWaterContent() + (double)mp.getLeafCondition() + (double)mp.getPestsContent() + (double)mp.getSoilFertility()) / 400.0 * 150.0);
                        if (newLevel > mp.getLevel()) {
                            mp2.setLevel(newLevel);
                        }
                        if (p.getName().equals(mp.getPlant()) && p.getId() >= 1 && p.getId() <= 5){
                            mp2.setWaterContent(newWaterNum);
                            mp2.setLeafCondition(newFertilizerNum);
                            mp2.update(id);
                        }
                        else if (p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8) {
                            mp2.setWaterContent(newWaterNum);
                            mp2.setLeafCondition(newFertilizerNum);
                            mp2.setPestsContent(newPestNum);
                            mp2.setSoilFertility(newSoilNum);
                            mp2.update(id);
                        }
                    }
                }
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


