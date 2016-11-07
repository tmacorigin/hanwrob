/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.service.MainService;
import com.tmac.onsite.service.ServiceWorkUtils;
import com.tmac.onsite.utils.SharePreferens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.StatusBarUtil;

/**
 * @author tmac
 */
public class WelcomeActivity extends Activity {

	private SharePreferens sp;
	private static final int TIME = 20;
	private static final int GO_ACTIVATION = 0x100;
	private static final int GO_GUIDE = 0x101;
	private final String serviceName = "com.tmac.onsite.service.MainService";


	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case GO_ACTIVATION:
					go_Activation();
					break;
				case GO_GUIDE:
					go_guide();
					break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//StatusBarUtil.setTranslucent(this, 0);
		SystemClock.sleep(1500);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		sp = new SharePreferens(getApplicationContext());
		if(sp.isFirstIN()){
			handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			sp.isFirstIN(false);
		}else {
			//setContentView(R.layout.activity_welcome);
			//SystemClock.sleep(1000);
			handler.sendEmptyMessageDelayed(GO_ACTIVATION, TIME);
		}
		if(!ServiceWorkUtils.isServiceWorked(this, serviceName)){
			Intent intent = new Intent(this, MainService.class);
			intent.putExtra("launcher","unauto");
			startService(intent);
		}

	}
	
	/**
	 * 跳转到引导页面
	 */
	protected void go_guide() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, GuideActivity.class));
		finish();
	}

	/**
	 * 跳转到激活页面
	 */
	protected void go_Activation() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, ActivationActivity.class));
		finish();
	}

	
	
}
