package com.tmac.onsite.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by user on 16/10/8.
 */

public class ServiceWorkUtils {

    // 判断service是否在运行状态
    public static boolean isServiceWorked(Context context, String serviceName){
        ActivityManager mManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) mManager.getRunningServices(Integer.MAX_VALUE);
        for(int i = 0; i < runningService.size(); i++){
            if(runningService.get(i).service.getClassName().toString().equals(serviceName)){
                return true;
            }
        }
        return false;
    }

}
