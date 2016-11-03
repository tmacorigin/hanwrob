package com.tmac.onsite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.toolset.activity.basicActivity;

import com.tmac.onsite.R;

public class DetailFinishActivity extends basicActivity implements View.OnClickListener{

    private Button btn_display_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_finish);
        super.onCreate(savedInstanceState);
        hc.setTitle(getResources().getString(R.string.finshed_task));

        btn_display_img = (Button) findViewById(R.id.contrust_img_btn);
        btn_display_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contrust_img_btn:
                startActivity(new Intent(DetailFinishActivity.this, DisplayConstructImgActivity.class));
                break;
        }
    }


}
