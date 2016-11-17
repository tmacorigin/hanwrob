package com.tmac.onsite.activity;

import java.util.ArrayList;
import java.util.List;

import me.tmac.photopicker.PhotoPicker;
import me.tmac.photopicker.PhotoPreview;

import com.tmac.onsite.utils.MyDialog;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.activity.basicActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmac.onsite.R;
import com.tmac.onsite.adapter.RecyclerViewAdapter;
import com.tmac.onsite.inter_face.RecyclerItemClickListener;
import com.tmac.onsite.inter_face.RecyclerItemClickListener.OnItemClickListener;
import com.tmac.onsite.utils.LightControl;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.CommonDialog;
import com.toolset.state.WebApiII;

public class UploadImgActivity extends basicActivity implements CommonDialog.OnDialogListenerInterface{
	
	private static final String TAG = "LC-UploadImgActivity";
	private static boolean DBG = true;
	private ImageView btn_add_img;
	private Button btn_send_img;
	private TextView tvView;
	private ListView mListView;
	private ImageView upload_img;
	private ImageView btn_add_no_img;
	private ImageView window_back;
	private ArrayList<String> selectedPhotos;
	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	private int value;
	private Animation operatingAnim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_upload_img);
		super.onCreate(savedInstanceState);
		selectedPhotos = new ArrayList<String>();
		adapter = new RecyclerViewAdapter(this, selectedPhotos);
		initViews(getIntent());

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		initEvents();
	}
	
	private void initViews(Intent intent) {
		// TODO Auto-generated method stub
		btn_add_img = (ImageView) findViewById(R.id.add_img_btn);
		upload_img = (ImageView) findViewById(R.id.uploading_img);
		btn_add_no_img = (ImageView) findViewById(R.id.add_no_img_btn);
		window_back = (ImageView) findViewById(R.id.window_background);
		//btn_send_img = (Button) findViewById(R.id.send_img_btn);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		//tvView = (TextView) findViewById(R.id.upload_tv_title);
		value = intent.getIntExtra(DetailNoBeginActivity.RETURN_STATE, DetailNoBeginActivity.DEFAULT_VALUE);

		if(value == DetailNoBeginActivity.UPLOAD_PRE){
			if (DBG) Log.d(TAG, "setTitle");
			hc.setTitle(getResources().getString(R.string.upload_pre_img));
		}else {
			hc.setTitle(getResources().getString(R.string.upload_end_img));
		}
		hc.setHeaderRightImage(R.drawable.upload);
	}


	private void initEvents() {
		// TODO Auto-generated method stub
		btn_add_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(UploadImgActivity.this, SelectImgActivity.class));
				PhotoPicker.builder()
					.setPhotoCount(20)
					.setShowCamera(true)
					.setSelected(selectedPhotos)
					.start(UploadImgActivity.this);
			}
		});

		btn_add_no_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoPicker.builder()
						.setPhotoCount(20)
						.setShowCamera(true)
						.setSelected(selectedPhotos)
						.start(UploadImgActivity.this);
			}
		});
		
		
		/*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(UploadImgActivity.this,
				new OnItemClickListener() {
					
					@Override
					public void onItemClick(View view, int position) {
						// TODO Auto-generated method stub
						PhotoPreview.builder()
								.setPhotos(selectedPhotos)
								.setCurrentItem(position)
								.start(UploadImgActivity.this);
					}
				}));*/
		
		/*btn_send_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//LightControl.lightOff(getWindow());
				// TODO Auto-generated method stub
				*//*AlertDialog.Builder builder = new AlertDialog.Builder(UploadImgActivity.this);
				builder.setMessage(R.string.upload_message)
						.setPositiveButton(R.string.wait_upload, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
							
						})
						.setNegativeButton(R.string.ensure_upload, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.putExtra(DetailNoBeginActivity.RETURN_STATE, value);
								setResult(RESULT_OK, intent);
								finish();		
							}
						})
						.setOnDismissListener(new OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface dialog) {
								// TODO Auto-generated method stub
								LightControl.lightOn(getWindow());
							}
						})
						.create()
						.show();*//*
				CommonDialog commonDialog = new CommonDialog(UploadImgActivity.this, getResources().getString(R.string.upload_message),
						getResources().getString(R.string.ensure_upload), getResources().getString(R.string.wait_upload), 0, UploadImgActivity.this);
				commonDialog.show();
			}
		});*/
		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.uploading_img);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

			List<String> photos = null;
			String cam_path = null;
			if (data != null) {
				photos = data
						.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
				cam_path = data.getStringExtra("callback_path");
			}

			if (photos != null) {
				selectedPhotos.clear();
				selectedPhotos.addAll(photos);
			}

			if(photos.size() > 0){
				btn_add_no_img.setVisibility(View.GONE);
				btn_add_img.setVisibility(View.VISIBLE);
			}

			if(cam_path != null){
				selectedPhotos.add(cam_path);
			}
			adapter.notifyDataSetChanged();

		}
	}


	@Override
	public void doConfirm(int situation) {
		window_back.setVisibility(View.VISIBLE);
		//MyDialog.lightOff(getWindow());
		upload_img.setVisibility(View.VISIBLE);
		upload_img.startAnimation(operatingAnim);
		new Thread(new Runnable() {
			@Override
			public void run() {
				SystemClock.sleep(2000);
				Intent intent = new Intent();
				intent.putExtra(DetailNoBeginActivity.RETURN_STATE, value);
				setResult(RESULT_OK, intent);
				finish();
			}
		}).start();

		// 上传图片
		/*ExpCommandE e = new ExpCommandE();
		e.AddAProperty(new Property("name", ""));
		e.AddAProperty(new Property("password", ""));
		WebApiII.getInstance(getMainLooper()).user_loginReq(e);*/

	}

	@Override
	public void onMenuClick(int menuId) {
		super.onMenuClick(menuId);
		switch (menuId){
			case R.id.headRight:
				CommonDialog commonDialog = new CommonDialog(UploadImgActivity.this, getResources().getString(R.string.upload_message),
						getResources().getString(R.string.ensure_upload), getResources().getString(R.string.wait_upload), 0, UploadImgActivity.this);
				commonDialog.show();
				break;
		}
	}
}
