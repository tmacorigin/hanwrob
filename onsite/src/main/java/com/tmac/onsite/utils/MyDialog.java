package com.tmac.onsite.utils;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailNoBeginActivity;
import com.tmac.onsite.activity.UploadImgActivity;

public class MyDialog {

	private static final int DEFAULT_SITUATION = -1;
	public static final int POSITIVE = 0;
	public static final int NEGATIVE = 1;
	public static boolean isLight = true;
	private static final String TAG = "LC-MyDialog";
	private static boolean DBG = true;
	private static boolean isNegative;


	public static void showDialog(Context context, int message, int positiveStr, int negativeStr,
								  final Window window, final OnDialogClickListener listener){
		lightOff(window);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						lightOn(window);
					}
				})
				.setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						listener.onDialog(NEGATIVE, DEFAULT_SITUATION);
					}

				})
				.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				})
				.create()
				.show();
	}

	public static void showDialog(Context context, int message, int positiveStr, int negativeStr, 
			final Window window, final OnDialogClickListener listener, final int situation){
		lightOff(window);
		isNegative = false;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						if(situation != R.id.contrust_finish
								|| (situation == R.id.contrust_finish && !isNegative)){
							lightOn(window);
						}
					}
				})
				.setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						listener.onDialog(POSITIVE, situation);
					}
					
				})
				.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						listener.onDialog(NEGATIVE, situation);
						isNegative = true;
					}
				})
				.create()
				.show();
	}
	
	public static void lightOn(Window window) {
		// TODO Auto-generated method stub
		if(!isLight){
			if(DBG) Log.d(TAG, "lightOn");
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.alpha = 1.0f;
			window.setAttributes(lp);
			isLight = true;
		}
	}
	
	
	public static void lightOff(Window window){
		if(isLight){
			if(DBG) Log.d(TAG, "lightOff");
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.alpha = 0.5f;
			window.setAttributes(lp);
			isLight = false;
		}
	}
	
	public interface OnDialogClickListener{
		void onDialog(int type, int situation);
	}

}
