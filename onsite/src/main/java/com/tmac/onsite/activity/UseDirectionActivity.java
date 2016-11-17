package com.tmac.onsite.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.tmac.onsite.R;
import com.toolset.activity.basicActivity;
import com.toolset.activity.headerCtrl;

public class UseDirectionActivity extends basicActivity {

    private static final boolean DBG = true;
    private static final String TAG = "LC-UseDirectActivity";
    private LinearLayout zdnHeaderLayout;
    private headerCtrl hc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_use_direction);
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void initViews() {
        zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
        if( zdnHeaderLayout != null )
        {
            if (DBG) Log.d(TAG, "headerCtrl init");
            hc = new headerCtrl(zdnHeaderLayout , this);
        }
    }
}
