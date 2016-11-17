/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.TaskBean;
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

import com.tmac.onsite.view.CommonDialog;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.activity.basicActivity;
import com.toolset.activity.headerCtrl;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dataManagerdataBase;
import com.toolset.state.WebApiII;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * @author tmac
 */
public class DetailTaskActivity extends basicActivity implements CommonDialog.OnDialogListenerInterface {

	private static final boolean DBG = true;
	private static final String TAG = "LC-DetailTaskActivity";
	private Button rob_Btn;
	String tarStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//EventBus.getDefault().register(this);
		setContentView(R.layout.activity_detail_task);
		super.onCreate(savedInstanceState);
		tarStr = getIntent().getStringExtra("tarTaskId");
		initViews();
		initEvents();

	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		rob_Btn = (Button) findViewById(R.id.main_button);
		hc.setTitle(getResources().getString(R.string.task_detail));
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
				/*AlertDialog.Builder builder = new AlertDialog.Builder(DetailTaskActivity.this);
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
						.show();*/
				CommonDialog commonDialog = new CommonDialog(DetailTaskActivity.this, getResources().getString(R.string.detail_dialog_message), getResources().getString(R.string.ensure), getResources().getString(R.string.cancle),
						0, DetailTaskActivity.this);
				commonDialog.show();
				
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
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	public void doConfirm(int situation) {
		Toast.makeText(DetailTaskActivity.this, "你的抢单要求已发送", Toast.LENGTH_SHORT).show();
		if(tarStr != null){
//			dataManager dm = dataManager.getInstance(this);
//			dm.addA_Class(TaskBean.class);
//			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
//			for (int index = 0;index < getDataList.size(); index ++){
//				TaskBean taskBean = (TaskBean) getDataList.get(index);
//				if(taskBean.getTaskState().equals("0") && taskBean.getTaskId().equals(tarStr)){
//					taskBean.setTaskState("1");
//				}
//			}
//			ArrayList<dataManagerdataBase> tmpList = new ArrayList<dataManagerdataBase>();
//			for (int i = 0 ; i < getDataList.size(); i ++){
//				tmpList.add((dataManagerdataBase) getDataList.get(i));
//			}
//			dm.resetdbData(TaskBean.class, tmpList);
			ExpCommandE getTaskE = new ExpCommandE();
			getTaskE.AddAProperty(new Property("mobile", ""));
			getTaskE.AddAProperty(new Property("taskId", tarStr));
			WebApiII.getInstance(getMainLooper()).getTaskListReq(getTaskE);
		}
	}

	public void onEvent(Object event){

	}

}
