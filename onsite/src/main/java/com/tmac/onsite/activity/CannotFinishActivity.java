package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.view.CommonDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.toolset.activity.basicActivity;

public class CannotFinishActivity extends basicActivity implements OnClickListener, CommonDialog.OnDialogListenerInterface{
	
	private EditText edit_reason;
	private ImageView iv_back;
	private ImageView iv_send;
	private TextView tv_show;
	private String reasonStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_cannot_finish);
		super.onCreate(savedInstanceState);

		initViews(getIntent());
		initEvents();
		
	}

	private void initViews(Intent intent) {
		// TODO Auto-generated method stub
		edit_reason = (EditText) findViewById(R.id.edit_reason);
		//iv_back = (ImageView) findViewById(R.id.cannot_finish_iv_back);
		//iv_send = (ImageView) findViewById(R.id.cannot_finish_send);
		hc.setHeaderRightImage(R.drawable.share);
		tv_show = (TextView) findViewById(R.id.tv_show_reason);
		boolean isShowReason = intent.getBooleanExtra(DetailNoBeginActivity.SHOW_REASON, false);
		String reasonString = intent.getStringExtra(DetailNoBeginActivity.CANNOT_REASON);
		if(isShowReason){
			edit_reason.setVisibility(View.GONE);
			hc.headerRight.setVisibility(View.GONE);
			tv_show.setVisibility(View.VISIBLE);
			tv_show.setText("由于时间原因,施工无法完成,抱歉!");
		}
		
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		//iv_back.setOnClickListener(this);
		//iv_send.setOnClickListener(this);
		edit_reason.addTextChangedListener(new TextWatcher() {
			
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
				reasonStr = s.toString();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		/*case R.id.cannot_finish_iv_back:
			//finish();
			break;
		case R.id.cannot_finish_send:
			*//*CommonDialog commonDialog = new CommonDialog(this, getResources().getString(R.string.ensure_send_reason), getResources().getString(R.string.ensure_send),
					getResources().getString(R.string.cancel_send), R.id.cannot_finish_send, this);
			commonDialog.show();*//*
			break;*/
		default:
			break;
		}
	}

	@Override
	public void doConfirm(int situation) {
		switch (situation) {
			case R.id.headRight:
				Intent intent = new Intent();
				intent.putExtra(DetailNoBeginActivity.RETURN_STATE, DetailNoBeginActivity.EDIT_REASON);
				intent.putExtra(DetailNoBeginActivity.CANNOT_REASON, reasonStr);
				setResult(RESULT_OK, intent);
				finish();
				break;

			default:
				break;
		}
	}

	@Override
	public void onMenuClick(int menuId) {
		super.onMenuClick(menuId);
		switch (menuId){
			case R.id.headRight:
				CommonDialog commonDialog = new CommonDialog(this, getResources().getString(R.string.ensure_send_reason), getResources().getString(R.string.ensure_send),
						getResources().getString(R.string.cancel_send), R.id.headRight, this);
				commonDialog.show();
				break;
		}
	}
}
