package com.tmac.onsite.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 16/10/8.
 */

public class MainService extends Service {

    private static final boolean DBG = false;
    private static final String TAG = "LC-MainService";
    private final String serviceName = "com.tmac.onsite.service.AssistService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DBG) Log.d(TAG, "onStartCommand");
        thread.start();
        return START_STICKY;
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(DBG) Log.d(TAG, "MainService Run: " + System.currentTimeMillis());
                    boolean isServiceWork = ServiceWorkUtils.isServiceWorked(MainService.this, serviceName);
                    if(!isServiceWork){
                        Intent service = new Intent(MainService.this, AssistService.class);
                        startService(service);
                        if(DBG) Log.i(TAG, "start AssistService");
                    }
                }
            };
            timer.schedule(task, 0, 1000);
        }
    });


}
