package com.tmac.onsite.updateversion;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tmac.onsite.fragment.LeftMenuFragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 16/10/10.
 */

public class CheckVersionTask implements Runnable {

    private static final String TAG = "LC-CheckVersionTask";
    private Context mContext;
    private Handler handler;
    private UpdateInfo info;
    InputStream is;

    public CheckVersionTask(Context context, Handler handler){
        this.mContext = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            String path = "http://172.18.18.164:8080/UpdateVersion/updateServlet";
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if(conn.getResponseCode() == 200){
                is = conn.getInputStream();
            }
            Log.i(TAG, "is = " + is);
            info = UpdataInfoParser.getUpdataInfo(is);
            if(info.getVersion().equals(getVersionName())){
                Log.i(TAG, "版本号相同");
                Message msg = new Message();
                msg.what = LeftMenuFragment.UPDATE_NONEED;
                handler.sendMessage(msg);
            }else {
                Log.i(TAG, "版本号不同");
                Message msg = new Message();
                msg.what = LeftMenuFragment.UPDATE_NEED;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            Log.i(TAG, "exception = " + e.getMessage());
            Message msg = new Message();
            msg.what = LeftMenuFragment.GETUPDATE_INFO_ERROR;
            handler.sendMessage(msg);
            e.printStackTrace();
        }

    }

    // 获取当前app的VersionName
    private String getVersionName() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            // 参数一：包名 参数二：flag 0表示获取版本信息
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "versionName = " + packageInfo.versionName);
        return packageInfo.versionName;
    }

}
