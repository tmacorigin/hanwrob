package com.tmac.onsite.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.location.MyLocationListener;
import com.toolset.state.stateMachine;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 16/10/8.
 */

public class MainService extends Service {

    private static final boolean DBG = false;
    private static final String TAG = "LC-MainService";
    private final String serviceName = "com.tmac.onsite.service.AssistService";
    private stateMachine  sm = null;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DBG) Log.d(TAG, "onStartCommand");
        Log.d(TAG, "MainService onStartCommand");
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

    @Override
    public void onCreate() {
        Log.i(TAG, "MainService onCreate");
//        System.loadLibrary("locSDK7");
        EventBus.getDefault().register(this);
        sm = new stateMachine( this );
        ExpCommandE expCommandE = new ExpCommandE("startUp");
        expCommandE.AddAExpProperty(new Property("internalMessageName", "startUp"));
        sm.mainControl(expCommandE );
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        mLocationClient.start();
        super.onCreate();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "MainService onDestroy");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(Object event) {

        assert( event instanceof  ExpCommandE );
        ExpCommandE e = (ExpCommandE) event;
        String command = e.GetCommand();

        if( command.equals("STATE_CONTROL_COMMAND") )
        {
            sm.mainControl(e);
        }

    }
}
