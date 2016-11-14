package com.tmac.onsite.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmac.onsite.R;
import com.tmac.onsite.fragment.ConstructEndFragment;
import com.tmac.onsite.fragment.ConstructPreFragment;
import com.tmac.onsite.utils.StatusBarUtil;
import com.toolset.CommandParser.ExpCommandE;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class DisplayConstructImgActivity extends FragmentActivity implements View.OnClickListener{

    private static final boolean DBG = true;
    private static final String TAG = "LC-Display";
    private ImageView iv_back;
    private TextView textView;
    private TextView netHint_tv;
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private Fragment preFragment;
    private Fragment endFragment;
    private static final String[] TITLE = new String[]{"施工前图片", "施工后图片"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_construct_img);
        EventBus.getDefault().register(this);
        StatusBarUtil.setColorDiff(this, getResources().getColor(R.color.layout_title_bg));
        StatusBarUtil.setTranslucent(this, 0);

        iv_back = (ImageView) findViewById(R.id.base_iv_back);
        textView = (TextView) findViewById(R.id.base_tv);
        indicator = (TabPageIndicator) findViewById(R.id.tabPageIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        netHint_tv = (TextView) findViewById(R.id.network_hint_img);
        textView.setText(R.string.construct_img);
        preFragment = new ConstructPreFragment();
        endFragment = new ConstructEndFragment();
        list.add(preFragment);
        list.add(endFragment);

        FragmentPagerAdapter adapter = new TabPagerIndicatorAdapter(getSupportFragmentManager());
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

        iv_back.setOnClickListener(this);

    }


    class TabPagerIndicatorAdapter extends FragmentPagerAdapter{

        public TabPagerIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Fragment fragment = new ConstructPreFragment();
            return list.get(position);
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
        switch (v.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            default:
                break;
        }
    }


    public void onEvent(Object event) {
        assert( event instanceof ExpCommandE);
        ExpCommandE e = (ExpCommandE) event;
        String command = e.GetCommand();

        if( command.equals("NET_DISCONNECT") )
        {
            if(DBG) Log.d(TAG, "NET_DISCONNECT");
            netHint_tv.setVisibility(View.VISIBLE);
        }
        if( command.equals("NET_CONNECT") )
        {
            if(DBG) Log.d(TAG, "NET_CONNECT");
            netHint_tv.setVisibility(View.GONE);
        }

    }


}
