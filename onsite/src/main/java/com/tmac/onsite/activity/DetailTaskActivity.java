/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.StatusBarUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.toolset.activity.basicActivity;
import com.toolset.activity.headerCtrl;

/**
 * @author tmac
 */
public class DetailTaskActivity extends basicActivity{

	private static final boolean DBG = true;
	private static final String TAG = "LC-DetailTaskActivity";
	private Button rob_Btn;
	private ImageView iv_back;
	private LinearLayout zdnHeaderLayout;
	private headerCtrl hc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_task);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtil.setColorDiff(this, getResources().getColor(R.color.layout_title_bg));
		StatusBarUtil.setTranslucent(this, 0);

		initViews();
		initEvents();

	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		rob_Btn = (Button) findViewById(R.id.main_button);
		//iv_back = (ImageView) findViewById(R.id.iv_back_detail);
		zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
		if( zdnHeaderLayout != null )
		{
			if (DBG) Log.d(TAG, "headerCtrl init");
			hc = new headerCtrl(zdnHeaderLayout , this);
			hc.setTitle(getResources().getString(R.string.task_detail));
		}
	}

	@Override
	public void onMenuClick(int menuId) {
		super.onMenuClick(menuId);
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		rob_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(DetailTaskActivity.this);
				builder.setMessage(R.string.detail_dialog_message)
						.setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Toast.makeText(DetailTaskActivity.this, "你的抢单要求已发送", Toast.LENGTH_SHORT).show();
							}
							
						})
						.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
										
							}
						})
						.create()
						.show();
				
				
			}
		});
		
		/*iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
