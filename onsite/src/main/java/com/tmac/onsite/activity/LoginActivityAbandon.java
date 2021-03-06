package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.StatusBarUtil;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.state.WebApiII;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.toolset.activity.basicActivity;

public class LoginActivityAbandon extends basicActivity {

	private static boolean DBG = true;
	private final static String TAG = "LC-LoginActivityAbandon";
	private TextView tv_title;
	private EditText edit_user;
	private EditText edit_password;
	private Button btn_login;
	private String userStr;

	private boolean webEnable = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_abandon);
//		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
		StatusBarUtil.setColorDiff(this, getResources().getColor(R.color.layout_title_bg));
		StatusBarUtil.setTranslucent(this, 0);
		
		initViews();
		initEvents();
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tv_title = (TextView) findViewById(R.id.base_tv);
		tv_title.setText(getResources().getString(R.string.login_title));
		
		edit_user = (EditText) findViewById(R.id.user_edit);
		edit_password = (EditText) findViewById(R.id.password_edit);
		btn_login = (Button) findViewById(R.id.btn_login);
		
	}
	
	
	
	private void initEvents() {
		// TODO Auto-generated method stub
		
		edit_user.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				userStr = s.toString();
			}
		});
		
		edit_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(userStr.equals("123456") && s.toString().equals("123456")){
					if(DBG) Log.d(TAG, "onTextChanged");
					btn_login.setBackgroundResource(R.color.login_btn_unpress);
					btn_login.setEnabled(true);
				}else {
					btn_login.setBackgroundResource(R.color.login_btn_normal);
					btn_login.setEnabled(false);
				}
			}
		});
		
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(webEnable == true ) {
				ExpCommandE e = new ExpCommandE();
				e.AddAProperty(new Property("name", ""));
				e.AddAProperty(new Property("password", ""));
				WebApiII.getInstance(getMainLooper()).user_loginReq(e);
//				EventBus.getDefault().post(e);
			}
			else {
				startActivity(new Intent(LoginActivityAbandon.this, MainActivity.class));
				finish();
			}
			}
		});
		
	}

	
}
