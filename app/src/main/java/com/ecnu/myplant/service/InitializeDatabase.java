package com.ecnu.myplant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ecnu.myplant.db.Plant;

public class InitializeDatabase extends Service {

    private static final String TAG = "InitializeDatabase";
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
            p.setName(plantName[i]);
            p.save();
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + TAG);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
