package com.tmac.onsite.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import de.greenrobot.event.EventBus;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tmac.onsite.R;
import com.tmac.onsite.adapter.MainViewPageAdapter;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.fragment.CancleFragment;
import com.tmac.onsite.fragment.FinishedFragment;
import com.tmac.onsite.fragment.LeftMenuFragment;
import com.tmac.onsite.fragment.NoBeginFragment;
import com.tmac.onsite.fragment.NoFinishedFragment;
import com.tmac.onsite.fragment.RobTaskFragment;
import com.tmac.onsite.fragment.SendTaskFragment;
import com.tmac.onsite.fragment.SendTaskFragmentUpdate;
import com.tmac.onsite.inter_face.UnreadInfoCallBack;
import com.tmac.onsite.utils.FindViewById;
import com.tmac.onsite.view.NoScrollViewPager;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.MainControl.TestControl;
import com.toolset.dataManager.dataManager;
import com.toolset.location.MyLocationListener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends SlidingFragmentActivity implements OnClickListener, LeftMenuFragment.OnExitAvaiListener{

	private static final boolean DBG = true;
	private static final String TAG = "LC-MainActivity";
	//private ViewPager vp;
	private NoScrollViewPager vp;
	private List<Fragment> fragmentList;
	private RadioGroup rg;
	private BGABadgeRadioButton rb_rob_task;
	private BGABadgeRadioButton rb_send_task;
	private FrameLayout settings;
	private FrameLayout messages;
	private ImageView iv_set_tip;
	private ImageView iv_msg_tip;
	private RobTaskFragment robTaskFragment = new RobTaskFragment();
	private SendTaskFragmentUpdate sendTaskFragment = new SendTaskFragmentUpdate();

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener(this);

	private int unReadInfo = 0;
	private boolean isCreate = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
//		ExpCommandE getTaskE = new ExpCommandE();
//		getTaskE.AddAProperty(new Property("mobile", ""));
//		WebApiII.getInstance(getMainLooper()).getTaskListReq(getTaskE);
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
		}
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
		}
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener( myListener );    //注册监听函数
		initLocation();
		mLocationClient.start();
		//robTaskFragment = new RobTaskFragment();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
		//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		setContentView(R.layout.activity_main);
		//StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);

		/*SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(false);
		tintManager.setNavigationBarTintEnabled(false);*/

		NoBeginFragment.setUnreadInfoCallBack(mUnreadInfoCallBack);
		FinishedFragment.setUnreadInfoCallBack(mUnreadInfoCallBack);
		NoFinishedFragment.setUnreadInfoCallBack(mUnreadInfoCallBack);
		CancleFragment.setUnreadInfoCallBack(mUnreadInfoCallBack);
		SendTaskFragment.setUnreadInfoCallBack(mUnreadInfoCallBack);

		// 添加SlidingMenu
		addLeftMenu();
		// 初始化View
		initViews();
		if(!TestControl.isTest){
			initEvents();
		}

	}

	private UnreadInfoCallBack mUnreadInfoCallBack = new UnreadInfoCallBack() {
		@Override
		public void getUnreadInfoNum(int infoNum) {
			unReadInfo = infoNum;
			if(unReadInfo != 0){
				rb_send_task.showTextBadge(unReadInfo + "");
			}else{
				rb_send_task.hiddenBadge();
			}
		}
	};

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	
	private void initViews() {
		// TODO Auto-generated method stub
		settings = (FrameLayout) findViewById(R.id.layout_settings);
		messages = (FrameLayout) findViewById(R.id.layout_message);
		iv_set_tip = (ImageView) findViewById(R.id.img_settings_red_tips);
		iv_msg_tip = (ImageView) findViewById(R.id.img_message_red_tips);
		rg = FindViewById.getView(this, R.id.rg);
		rb_rob_task = FindViewById.getView(this, R.id.rb_get_task);
		rb_send_task = FindViewById.getView(this, R.id.rb_send_task);
		
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(robTaskFragment);
        fragmentList.add(sendTaskFragment);
        
        //vp = (ViewPager) findViewById(R.id.viewpager);
		vp = (NoScrollViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(new MainViewPageAdapter(getSupportFragmentManager(), fragmentList));
		if(!TestControl.isTest){
			vp.setCurrentItem(0);
		}
	}
	
	private void initEvents() {

		settings.setOnClickListener(this);
		messages.setOnClickListener(this);
		
//		// 设置底部RadioButton中气泡提示的数量
//		rb_rob_task.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.drawable.badge_view));
//		rb_rob_task.showTextBadge("5");

		rb_send_task.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.drawable.badge_view));
		rb_send_task.hiddenBadge();
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_get_task:
					vp.setCurrentItem(0);
					// 隐藏提示				
//					rb_rob_task.showTextBadge("12");
					rb_send_task.hiddenBadge();
					break;
				case R.id.rb_send_task:
					vp.setCurrentItem(1);
					rb_rob_task.hiddenBadge();
					if(!TestControl.isTest && isCreate){
						getListData();
						isCreate = false;
					}
					if(unReadInfo != 0){
						rb_send_task.showTextBadge(unReadInfo + "");
					}else{
						rb_send_task.hiddenBadge();
					}

					break;
				default:
					break;
				}
			}
		});
		
		// viewpager的PageChange事件
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// 处理与RadioGroup的关联
				BGABadgeRadioButton rb = (BGABadgeRadioButton) rg.getChildAt(position);
				rb.setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int position) {
				
			}
		});

	}

	/**
	 * 添加左边的SlidingMenu
	 */
	private void addLeftMenu() {
		// TODO Auto-generated method stub
		FrameLayout left = new FrameLayout(this);
		LeftMenuFragment leftMenuFragment = new LeftMenuFragment();
		leftMenuFragment.setOnExitAvaiListener(this);
		left.setId("LEFT".hashCode());
		setBehindContentView(left);
		getSupportFragmentManager().
		beginTransaction().
		replace("LEFT".hashCode(), leftMenuFragment)
		.commit();
					
		// 设置SlidingMenu的常用属性
		SlidingMenu sm = getSlidingMenu();
		// 1.设置阴影的宽度
		sm.setShadowWidth(20);
		// 2.设置阴影图片
		//sm.setShadowDrawable(R.drawable.showder);
		//3.滑动过程中透明度的控制(淡入淡出的效果)
		sm.setFadeEnabled(true);
		//4.淡入淡出的透明度效果的调整(调整透明度最小的值)
		sm.setFadeDegree(0.1f);
		//5.设置SlidingMenu距离右侧的距离大小
        sm.setBehindOffset((int) getResources().getDimension(R.dimen.x360));
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.layout_settings:
				toggle();
				break;
			case R.id.layout_message:
				startActivity(new Intent(MainActivity.this, MessageActivity.class));
				break;
			default:
				break;
		}
	}

	@Override
	public void onExitAvai() {
		if(DBG) Log.d(TAG, "Exit MainActivity");
		finish();
	}

	@Override
	protected void onDestroy() {

		mLocationClient.stop();
		super.onDestroy();
	}
	private void getListData(){
		dataManager dm = dataManager.getInstance(this);
		dm.addA_Class(TaskBean.class);
		ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
		unReadInfo = 0;
		for (int index = 0;index < getDataList.size(); index ++){
			TaskBean taskBean = (TaskBean) getDataList.get(index);
			if(taskBean.getTaskState().equals("1") && taskBean.getReadState().equals("0")){
				unReadInfo ++;
			}
		}
	}

	public void onEvent(Object event) {
		if(DBG) Log.d(TAG, "getDataList................");
		assert( event instanceof ExpCommandE);
		ExpCommandE e = (ExpCommandE) event;
		String command = e.GetCommand();

		if( command.equals("GET_DATA_COMMAND") )
		{
			getListData();
			if(TestControl.isTest){
				vp.setCurrentItem(0);
				// 初始化事件
				initEvents();
				EventBus.getDefault().unregister(this);
			}
		}
	}


}