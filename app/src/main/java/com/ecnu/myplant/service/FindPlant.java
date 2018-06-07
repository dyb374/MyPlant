package com.ecnu.myplant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class FindPlant extends Service {

    private static final String TAG = "FindPlant";

    public FindPlant() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onCount();
        String province = intent.getStringExtra("province");
        Log.d(TAG, "onStartCommand: "+province);
        List<Plant> plants = DataSupport.findAll(Plant.class);
        int plantId = (int) (Math.random() * plants.size() + 1);
        plantId = 1;
        String plantName = null;
        for(Plant p : plants){
            if(p.getId() == plantId)
                plantName = p.getName();

        }
        ProvincePlant pp = new ProvincePlant();
        pp.setProvince(province);
        pp.setPlant(plantName);
        pp.save();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void onCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1800000);      //运行30分钟后停止
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        }).start();
    }

}
