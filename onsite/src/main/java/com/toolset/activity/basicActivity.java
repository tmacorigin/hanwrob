package com.toolset.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import de.greenrobot.event.EventBus;
import com.tmac.onsite.R;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.Network.NetworkReceiver;

/*
	实际使用的时候要集成basicActitity 并且在集成的activity中的layout文件中增加下面的内容
	<include layout="@layout/base_header"></include>

	此类提供了 1. eventBus的通信手段，通过重载 onEvent 累实现消息的获得  2. 统一的头部结构 ，而已重载onMenuClick 来获取点击动作

	@Override
	public void onMenuClick(int menuId) {
		switch( menuId )
		{
			case R.id.navigationButton:
				showNavigation();
				break;
			default:
				break;
		}
	}
* */
public class basicActivity extends Activity implements headerCtrl.menuStateChange {

	private static final boolean DBG = true;
	private static final String TAG = "LC-basicActivity";
	private LinearLayout zdnHeaderLayout = null;
	protected headerCtrl hc = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		EventBus.getDefault().register(this);

		super.onCreate(savedInstanceState);

		zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
		if( zdnHeaderLayout != null )
		{
			if (DBG) Log.d(TAG, "headerCtrl init");
			hc = new headerCtrl( zdnHeaderLayout , this );
		}



	}

	@Override
	protected void onResume() {
		super.onResume();
		if(NetworkReceiver.isConnect()){
			hc.setIsVisiable(false);
		}else {
			hc.setIsVisiable(true);
		}
	}

	@Override
	protected void onDestroy() {

		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	/*public void onEvent(Object event) {

		*//*
		if( event instanceof networkStatusEvent)
		{
			networkStatusEvent e = (networkStatusEvent)event;

			if( (false == e.getwifiConnect()) &&  ( false == e.getGprsConnect() ))
			{

			}
				//Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show();
		}
		*//*

	}*/

	@Override
	public void onMenuClick(int menuId) {
		switch( menuId )
		{

			case R.id.headBack:
				onBackPressed();
				break;

			default:
				break;
		}
	}

	@Override
	public void menuFragmentClick() {

	}

	public void onEvent(Object event) {
		if(DBG) Log.d(TAG, "getDataList................");
		assert( event instanceof ExpCommandE);
		ExpCommandE e = (ExpCommandE) event;
		String command = e.GetCommand();
		if( command.equals("NET_DISCONNECT") )
		{
			if(DBG) Log.d(TAG, "NET_DISCONNECT");
			hc.setIsVisiable(true);
		}
		if( command.equals("NET_CONNECT") )
		{
			if(DBG) Log.d(TAG, "NET_CONNECT");
			hc.setIsVisiable(false);
		}

	}
}