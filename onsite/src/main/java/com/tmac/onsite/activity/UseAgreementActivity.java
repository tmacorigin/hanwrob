package com.tmac.onsite.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import com.toolset.activity.basicActivity;

import com.tmac.onsite.R;
import com.toolset.activity.headerCtrl;

public class UseAgreementActivity extends basicActivity {

    private LinearLayout zdnHeaderLayout;
    private headerCtrl hc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_use_agreement);
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void initViews() {
        zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
        if( zdnHeaderLayout != null )
        {
            hc = new headerCtrl(zdnHeaderLayout , this);
            hc.setTitle(getResources().getString(R.string.use_agreement_title));
        }
    }
}
