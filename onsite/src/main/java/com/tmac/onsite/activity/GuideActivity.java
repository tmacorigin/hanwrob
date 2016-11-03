/**
 * 
 */
package com.tmac.onsite.activity;

import java.util.ArrayList;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.adapter.ViewPagerAdapter;
import com.tmac.onsite.utils.StatusBarUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class GuideActivity extends Activity implements OnPageChangeListener{

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	//private int[] ids = { R.id.iv1, R.id.iv2 };
	private Button start_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		//StatusBarUtil.setTranslucent(this, 0);
		initViews();
		//initDots();

	}
	
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.one, null));
		views.add(inflater.inflate(R.layout.two, null));

		vpAdapter = new ViewPagerAdapter(views, this);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		
		start_btn = (Button) views.get(1).findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(GuideActivity.this, ActivationActivity.class);
				startActivity(i);
				finish();
			}
		});

		vp.setOnPageChangeListener(this);
	}

	/*private void initDots() {
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) findViewById(ids[i]);
		}
	}*/

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		/*for (int i = 0; i < ids.length; i++) {
			if (arg0 == i) {
				//dots[i].setImageResource(R.drawable.login_point_selected);
			} else {
				//dots[i].setImageResource(R.drawable.login_point);
			}
		}*/
	}
	
}
