/**
 * 
 */
package com.tmac.onsite.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DisplayConstructImgActivity;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.inter_face.UnreadInfoCallBack;
import com.tmac.onsite.utils.DimenConvert;
import com.tmac.onsite.utils.GetWH;
import com.toolset.dataManager.dataManager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tmac
 */
public class SendTaskFragmentUpdate extends Fragment implements OnClickListener{

	private static final boolean DBG = true;
	private static final String TAG = "LC-SendTaskFrag";
	private NoBeginFragment noBeginFragment;
	private FinishedFragment finishedFragment;
	private NoFinishedFragment noFinishedFragment;
	private CancleFragment cancleFragment;
	private List<Fragment> fragList;
	private TabPageIndicator indicator;
	private ViewPager viewPager;
	private static final String[] TITLE = new String[]{"还未施工", "已经完成", "未能完成", "已经撤销"};


	private static UnreadInfoCallBack mUnreadInfoCallBack = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(DBG) Log.d(TAG, "onCreateView");
		// create ContextThemeWrapper from the original Activity Context with the custom theme
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.SendStyledIndicators);

		// clone the inflater using the ContextThemeWrapper
		LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
		return localInflater.inflate(R.layout.fragment_send_task_update, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(DBG) Log.d(TAG, "onViewCreated");
		initViews(view);
		initDatas();
		initEvents();
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		indicator = (TabPageIndicator) view.findViewById(R.id.send_tabPageIndicator);
		viewPager = (ViewPager) view.findViewById(R.id.send_viewpager);
		noBeginFragment = new NoBeginFragment();
		noFinishedFragment = new NoFinishedFragment();
		finishedFragment = new FinishedFragment();
		cancleFragment = new CancleFragment();
		fragList = new ArrayList<Fragment>();
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		fragList.add(noBeginFragment);
		fragList.add(noFinishedFragment);
		fragList.add(finishedFragment);
		fragList.add(cancleFragment);

	}
	
	private void initEvents() {
		FragmentPagerAdapter adapter = new TabPagerIndicatorAdapter(getActivity().getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		indicator.setViewPager(viewPager);
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//Toast.makeText(getApplicationContext(), TITLE[position], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	class TabPagerIndicatorAdapter extends FragmentPagerAdapter{

		public TabPagerIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			//Fragment fragment = new ConstructPreFragment();
			return fragList.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position % TITLE.length];
		}

		@Override
		public int getCount() {
			return TITLE.length;
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public static void setUnreadInfoCallBack(UnreadInfoCallBack unreadInfoCallBack){
		mUnreadInfoCallBack = unreadInfoCallBack;
	}

	private void getUnreadInfo(String tag){
		dataManager dm = dataManager.getInstance(getActivity());
		dm.addA_Class(TaskBean.class);
		ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
		int num = 0;
		for (int index = 0;index < getDataList.size(); index ++){
			TaskBean taskBean = (TaskBean) getDataList.get(index);
			if(taskBean.getTaskState().equals(tag) && taskBean.getReadState().equals("0")){
				num ++;
			}
		}
		mUnreadInfoCallBack.getUnreadInfoNum(num);
	}



}
