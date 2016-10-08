package com.toolset.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.toolset.basicStruct.networkStatusEvent;

import de.greenrobot.event.EventBus;


public class NetworkReceiver extends BroadcastReceiver {
	private static final String TAG = "SystemEventReceiver";
	private Context context;
	private static boolean gprsConnect;
	private static boolean wifiConnect;
	private boolean registed = false;

	public NetworkReceiver(Context context)
	{
		this.context = context;

		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mobNetInfo.isConnected() )
		{// unconnect network
			gprsConnect = true;
		}
		else
		{
			gprsConnect = false;
		}
		if(wifiNetInfo.isConnected())
		{
			wifiConnect = true;
		}
		else
		{
			wifiConnect = false;
		}


		regist();


	}

	@Override
	protected void finalize() throws Throwable {
		unregist();
		super.finalize();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mobNetInfo.isConnected() )
		{// unconnect network
			gprsConnect = true;
		}
		else
		{
			gprsConnect = false;
		}
		if(wifiNetInfo.isConnected())
		{
			wifiConnect = true;
		}
		else
		{
			wifiConnect = false;
		}

		//send message via BusEvent

		EventBus.getDefault().post( new networkStatusEvent(gprsConnect,wifiConnect) );  // dispatch message to anyone who care about it

	}

	static public boolean isConnect()
	{
		if( gprsConnect || wifiConnect )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	static boolean isGprsConnect()
	{
		return  gprsConnect;
	}

	static boolean isWifiConnect()
	{
		return wifiConnect;
	}

	public void regist()
	{
		if( !registed )
		{
			Log.d("NetworkReceiver", "registerReceiver");
			IntentFilter intentfilter = new IntentFilter();
			intentfilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(this, intentfilter);
			registed = true;
		}
	}
	public void unregist()
	{
		if( registed )
		{
			Log.d("NetworkReceiver","unregisterReceiver"  );

			context.unregisterReceiver(this);
			registed = false;
		}
	}
}