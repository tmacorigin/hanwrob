package com.tmac.onsite.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by user on 16/10/8.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final String TAG = "LC-BroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "receiver boot completed");
        context.startService(new Intent(context, MainService.class));
    }
}
