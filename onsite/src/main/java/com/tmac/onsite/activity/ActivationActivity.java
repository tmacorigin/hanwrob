/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.R.drawable;
import com.tmac.onsite.utils.AppManager;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.PhoneEditText;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscodeListener;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class ActivationActivity extends Activity{

	private static final boolean DBG = true;
	private static final String TAG = "LC-ActivationActivity";
	private EditText phone;
	private TextView rob_agreement;
	private ImageView clear;
	private Button activate;
	private LinearLayout back;
	public static final String INTENT_NAME = "phoneNum";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		}
		setContentView(R.layout.activity_activation);
		AppManager.getAppManager().addActivity(this);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			StatusBarUtil.setTranslucent(this, 0);
		}
		SMSSDK.getInstance().initSdk(this);
		SMSSDK.getInstance().setDebugMode(true);
		//StatusBarUtil.setTranslucent(this);
		//StatusBarUtil.setColor(this, android.R.color.transparent);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		Log.d(TAG, "width = " + screenWidth + "/ height = " + screenHeight);
		
		initView();
		setEditChanged();
		setListener();
	}

	
	public void initView() {
		phone = (EditText) findViewById(R.id.home_phone);
		rob_agreement = (TextView) findViewById(R.id.rob_agreement);
		clear = (ImageView) findViewById(R.id.home_clear);
		activate = (Button) findViewById(R.id.home_activate);
		back = (LinearLayout) findViewById(R.id.home_back);
		Log.d(TAG, "button size = " + activate.getTextSize());
	}

	public void setEditChanged() {

		phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				int i = phone.getText().toString().trim().length();
				if (i > 0) {
					clear.setVisibility(View.VISIBLE);
					if (i == 11) {
						activate.setBackgroundResource(drawable.btn_orange_bg);
						activate.setEnabled(true);
					} else {
						activate.setBackgroundResource(drawable.btn_gray_bg);
						activate.setEnabled(false);
					}
				} else {
					clear.setVisibility(View.GONE);

				}

			}
		});

	}

	public void setListener() {

		clear.setOnClickListener(ls);
		rob_agreement.setOnClickListener(ls);
		activate.setOnClickListener(ls);
		back.setOnClickListener(ls);

	}

	private OnClickListener ls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_clear:
				phone.setText("");
				break;
			case R.id.rob_agreement:
				startActivity(new Intent(ActivationActivity.this, UseAgreementActivity.class));
				break;
			case R.id.home_activate:
				//getSMSCode(phone.getText().toString());
				/*char one = phone.getText().toString().charAt(0);
				char two = phone.getText().toString().charAt(1);
				String on = one + "";
				String tw = two + "";
				if (on.contains("1") && "3578".contains(tw)) {
					Toast.makeText(getApplicationContext(), "请注意查收短信！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ActivationActivity.this, IdentifyActivity.class);
					intent.putExtra(INTENT_NAME, phone.getText().toString());
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "请正确输入手机号码！",
							Toast.LENGTH_SHORT).show();
				}*/
				if(isMobileNum(phone.getText().toString())){
					Toast.makeText(getApplicationContext(), "请注意查收短信！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ActivationActivity.this, IdentifyActivity.class);
					intent.putExtra(INTENT_NAME, phone.getText().toString());
					startActivity(intent);
				}else {
					Toast.makeText(getApplicationContext(), "请正确输入手机号码！",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.home_back:

				finish();
				break;

			}

		}
	};

	private void getSMSCode(final String phone) {
		SMSSDK.getInstance().getSmsCodeAsyn(phone, 1 + "", new SmscodeListener() {
			@Override
			public void getCodeSuccess(String s) {
				if(DBG) Log.d(TAG, "getCodeSuccess = " + s);
				Toast.makeText(getApplicationContext(), "请注意查收短信！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ActivationActivity.this, IdentifyActivity.class);
				intent.putExtra(INTENT_NAME, phone);
				startActivity(intent);
			}

			@Override
			public void getCodeFail(int errCode, String errMsg) {
				if(DBG) Log.d(TAG, "getCodeFail = " + errCode + "/" + errMsg);
				if(errCode == 3002){
					Toast.makeText(getApplicationContext(), "请正确输入手机号码！",
							Toast.LENGTH_SHORT).show();
				}else if(errCode == 2998){
					Toast.makeText(getApplicationContext(), "网络错误!",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "获取验证码失败!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// 判断手机号码是否正确
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		if(DBG) Log.d(TAG, "m.matches() = " + m.matches());
		return m.matches();
	}
}
