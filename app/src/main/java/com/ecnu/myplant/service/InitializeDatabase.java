package com.ecnu.myplant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ecnu.myplant.db.Plant;

public class InitializeDatabase extends Service {
    public InitializeDatabase() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String[] plantName = {"bamboo", "cactus", "peony", "acacia", "rose", "lily", "begonia", "magnolia"};
        for(int i = 0; i < plantName.length; i++) {
            Plant p = new Plant();
            p.setPlantId(i + 1);
            p.setName(plantName[i]);
            p.save();
        }
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
}
