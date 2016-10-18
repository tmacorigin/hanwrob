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

public class AssistService extends Service {

    private static final boolean DBG = false;
    private static final String TAG = "LC-AssistService";
    private final String serviceName = "com.tmac.onsite.service.MainService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(DBG) Log.i(TAG, "onStartCommand");
        Log.d(TAG, "AssistService onStartCommand");
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
                    if(DBG) Log.d(TAG, "AssistService Run: " + System.currentTimeMillis());
                    boolean isServiceWork = ServiceWorkUtils.isServiceWorked(AssistService.this, serviceName);
                    if(!isServiceWork){
                        Intent service = new Intent(AssistService.this, MainService.class);
                        startService(service);
                        if(DBG) Log.i(TAG, "start MainService");
                    }
                }
            };
            timer.schedule(task, 0, 1000);
        }
    });

}
