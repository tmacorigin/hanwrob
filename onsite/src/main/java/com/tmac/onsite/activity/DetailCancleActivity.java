package com.tmac.onsite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tmac.onsite.R;
import com.toolset.activity.basicActivity;

public class DetailCancleActivity extends basicActivity implements View.OnClickListener{

    private Button btn_display_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_cancle);
        super.onCreate(savedInstanceState);
        hc.setTitle(getResources().getString(R.string.task_abondoned));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }


}
