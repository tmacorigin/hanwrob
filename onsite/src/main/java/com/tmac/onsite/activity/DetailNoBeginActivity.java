package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.LightControl;
import com.tmac.onsite.utils.MyDialog;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.utils.MyDialog.OnDialogClickListener;
import com.tmac.onsite.view.AudioPopupWindow;
import com.tmac.onsite.view.AudioPopupWindow.OnUpLoadClickListener;
import com.tmac.onsite.view.CommonDialog;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.activity.basicActivity;
import com.toolset.state.WebApiII;

import android.Manifest;
import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.R.attr.value;

/**
 * @author tmac
 */
public class DetailNoBeginActivity extends basicActivity implements OnClickListener, OnUpLoadClickListener, OnDialogClickListener, CommonDialog.OnDialogListenerInterface{
	
	private static boolean DBG = true;
	private static final String TAG = "LC-DetailNoBegin";
	private Button btn_upload_pre_img;
	private Button btn_upload_end_img;
	private Button btn_construct_finish;
	private Button btn_construct_no_finish;
	private Button btn_construct_img;
	private Button btn_record_time;
	private Button btn_check_reason;
	private AudioPopupWindow popupWindow;
	private RelativeLayout layout;
	private RelativeLayout layout_bottom_1;
	private RelativeLayout layout_bottom_2;
	private LinearLayout linearLayout;
	private ImageView iv_back;
	private ImageView upload_img;
	private ImageView window_back;
	private Animation operatingAnim;
	private static String reasonStr;
	public static final int DEFAULT_VALUE = -1;
	private static final int REQUEST_CODE = 0; 
	public static final String SHOW_REASON = "show_reason";
	public static final String RETURN_STATE = "state";
	public static final String CANNOT_REASON = "reason";
	public static final int UPLOAD_PRE = 1;
	public static final int UPLOAD_END = 2; 
	public static final int EDIT_REASON = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_detail_nobegin);
		super.onCreate(savedInstanceState);


		initViews();
		initEvents();
			
	}

	private void initViews() {
		// TODO Auto-generated method stub
		hc.setTitle(getResources().getString(R.string.task_detail));
		upload_img = (ImageView) findViewById(R.id.uploading_img);
		window_back = (ImageView) findViewById(R.id.window_background);
		btn_upload_pre_img = (Button) findViewById(R.id.upload_pre_image);
		btn_upload_end_img = (Button) findViewById(R.id.upload_end_image);
		linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
		layout = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom);
		layout_bottom_1 = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom_1);
		layout_bottom_2 = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom_2);
		btn_construct_finish = (Button) findViewById(R.id.contrust_finish);
		btn_construct_no_finish = (Button) findViewById(R.id.contrust_no_finish);
		btn_construct_img = (Button) findViewById(R.id.contrust_img_btn);
		btn_record_time = (Button) findViewById(R.id.record_time_btn);
		btn_check_reason = (Button) findViewById(R.id.check_reason_btn);
		
		//iv_back = (ImageView) findViewById(R.id.iv_back_detail);
		//popupWindow = new AudioPopupWindow(this, this);
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		
		btn_upload_pre_img.setOnClickListener(this);
		btn_upload_end_img.setOnClickListener(this);
		btn_construct_finish.setOnClickListener(this);
		btn_construct_no_finish.setOnClickListener(this);
		btn_construct_img.setOnClickListener(this);
		btn_record_time.setOnClickListener(this);
		btn_check_reason.setOnClickListener(this);
		//iv_back.setOnClickListener(this);

		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.uploading_img);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.upload_pre_image:
			Intent intent_pre = new Intent(DetailNoBeginActivity.this, UploadImgActivity.class);
			intent_pre.putExtra(RETURN_STATE, UPLOAD_PRE);
			startActivityForResult(intent_pre, REQUEST_CODE);
			break;
		case R.id.upload_end_image:
			Intent intent_end = new Intent(DetailNoBeginActivity.this, UploadImgActivity.class);
			intent_end.putExtra(RETURN_STATE, UPLOAD_END);
			startActivityForResult(intent_end, REQUEST_CODE);
			break;
		case R.id.contrust_finish:
			/*MyDialog.showDialog(this, R.string.ensure_construct_finish, R.string.waiting, R.string.ensure_finish,
				getWindow(), this, R.id.contrust_finish);*/
			CommonDialog finish_dialog = new CommonDialog(this, getResources().getString(R.string.ensure_construct_finish), getResources().getString(R.string.ensure_finish), getResources().getString(R.string.waiting),
					R.id.contrust_finish, this);
			finish_dialog.show();
			break;
		case R.id.contrust_no_finish:
			/*MyDialog.showDialog(this, R.string.ensure_construct_nofinish, R.string.try_again, R.string.cannot_finish,
					getWindow(), this, R.id.contrust_no_finish);*/
			CommonDialog no_finish_dialog = new CommonDialog(this, getResources().getString(R.string.ensure_construct_nofinish), getResources().getString(R.string.cannot_finish), getResources().getString(R.string.try_again),
					R.id.contrust_no_finish, this);
			no_finish_dialog.show();
			break;
		case R.id.contrust_img_btn:
			Intent intent1 = new Intent(DetailNoBeginActivity.this, DisplayConstructImgActivity.class);
			startActivity(intent1);
			break;
		case R.id.record_time_btn:
			// 播放录音
			popupWindow.playAudio(false);
			break;
		/*case R.id.iv_back_detail:
			finish();
			break;*/
		case R.id.check_reason_btn:
			Intent intent = new Intent(DetailNoBeginActivity.this, CannotFinishActivity.class);
			intent.putExtra(SHOW_REASON, true);
			intent.putExtra(CANNOT_REASON, reasonStr);
			startActivity(intent);
			if(DBG) Log.i(TAG, "check_reason_btn");
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int state = 0;
		if(data != null){
			state = data.getIntExtra(RETURN_STATE, DEFAULT_VALUE);
		}
		if(DBG) Log.i(TAG, "onActivityResult");
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
			if(state == UPLOAD_PRE){
				// 显示上传施工后照片的Button
				btn_upload_end_img.setVisibility(View.VISIBLE);
				btn_upload_pre_img.setVisibility(View.GONE);
				layout_bottom_1.setVisibility(View.GONE);
			}else if(state == UPLOAD_END){
				btn_upload_end_img.setVisibility(View.GONE);
				btn_upload_pre_img.setVisibility(View.GONE);
				layout_bottom_1.setVisibility(View.VISIBLE);
			}else if(state == EDIT_REASON){
				finish();
				/*layout_bottom_2.setVisibility(View.VISIBLE);
				btn_record_time.setVisibility(View.GONE);
				btn_construct_no_finish.setVisibility(View.GONE);
				btn_check_reason.setVisibility(View.VISIBLE);
				reasonStr = data.getStringExtra(CANNOT_REASON);*/
				if(DBG) Log.i(TAG, "reasonStr = " + reasonStr);
			}
		}
	}

	private final int UPLOAD_RECORD = 100;
	/**
	 * 上传录音成功的回调
	 */
	@Override
	public void onUpload(String time) {
		// TODO Auto-generated method stub
		Toast.makeText(this, getResources().getString(R.string.upload_record_success), Toast.LENGTH_SHORT).show();
		CommonDialog dialog = new CommonDialog(this, getResources().getString(R.string.ensure_upload_record), getResources().getString(R.string.ensure),
				getResources().getString(R.string.cancle), UPLOAD_RECORD, this);
		dialog.show();
		/*layout_bottom_1.setVisibility(View.GONE);
		layout_bottom_2.setVisibility(View.VISIBLE);
		btn_construct_img.setText(R.string.construct_img);
		
		btn_record_time.setText(time);*/
		
	}

	@Override
	public void onDialog(int type, int situation) {
		// TODO Auto-generated method stub
		switch (situation) {
		case R.id.contrust_finish:
			if(type == MyDialog.NEGATIVE){
				if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
						!= PackageManager.PERMISSION_GRANTED){
					ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
				}
				MyDialog.lightOff(getWindow());
				// 标题栏的高度
				int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		        int height = getResources().getDimensionPixelSize(resourceId);
				popupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
		        popupWindow.showAsDropDown(findViewById(R.id.detail_no_begin_layout), 0, -popupWindow.getHeight());
				//popupWindow.showAtLocation(findViewById(R.id.detail_no_begin_layout), Gravity.TOP, 0, 300);
		        popupWindow.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						MyDialog.lightOn(getWindow());
					}
				});
			}
			break;
		case R.id.contrust_no_finish:
			if(type == MyDialog.NEGATIVE){
				startActivityForResult(new Intent(DetailNoBeginActivity.this, CannotFinishActivity.class), REQUEST_CODE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void doConfirm(int situation) {
		switch (situation) {
			case R.id.contrust_finish:
					if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
							!= PackageManager.PERMISSION_GRANTED){
						ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
					}
					MyDialog.lightOff(getWindow());
					// 标题栏的高度
					int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
					int height = getResources().getDimensionPixelSize(resourceId);
					popupWindow = new AudioPopupWindow(this, this);
					popupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
					popupWindow.showAsDropDown(layout, 0, -popupWindow.getHeight());
					popupWindow.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss() {
							// TODO Auto-generated method stub
							MyDialog.lightOn(getWindow());
						}
					});
				break;
			case R.id.contrust_no_finish:
					startActivityForResult(new Intent(DetailNoBeginActivity.this, CannotFinishActivity.class), REQUEST_CODE);
				break;
			case UPLOAD_RECORD:
				window_back.setVisibility(View.VISIBLE);
				upload_img.setVisibility(View.VISIBLE);
				upload_img.startAnimation(operatingAnim);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
							finish();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();

				// 上传录音
				/*ExpCommandE e = new ExpCommandE();
				e.AddAProperty(new Property("name", ""));
				e.AddAProperty(new Property("password", ""));
				WebApiII.getInstance(getMainLooper()).user_loginReq(e);*/
				break;
			default:
				break;
		}
	}
}
