/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.R.drawable;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.PhoneEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class ActivationActivity extends Activity{

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
		setContentView(R.layout.activity_activation);
		StatusBarUtil.setTranslucent(this, 0);
		//StatusBarUtil.setColor(this, R.color.base_bg_grey);
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
					Log.d(TAG, "afterTextChanged");
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
				char one = phone.getText().toString().charAt(0);
				char two = phone.getText().toString().charAt(1);
				String on = one + "";
				String tw = two + "";
				// Toast.makeText(getApplicationContext(), one+"", 1).show();
				if (on.contains("1") && "358".contains(tw)) {
					Toast.makeText(getApplicationContext(), "请注意查收短信！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ActivationActivity.this, IdentifyActivity.class);
					intent.putExtra(INTENT_NAME, phone.getText().toString());
					startActivity(intent);
				} else {
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
	
}
