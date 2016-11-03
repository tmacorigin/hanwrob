package com.tmac.onsite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tmac.onsite.R;
import com.toolset.activity.basicActivity;

public class DetailCannotFinishActivity extends basicActivity implements View.OnClickListener{

    private Button btn_display_img;
    private Button btn_cat_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_cannot_finish);
        super.onCreate(savedInstanceState);
        hc.setTitle(getResources().getString(R.string.cannot_finshed_task));

        btn_display_img = (Button) findViewById(R.id.contrust_img_btn);
        btn_cat_reason = (Button) findViewById(R.id.check_reason_btn);
        btn_display_img.setOnClickListener(this);
        btn_cat_reason.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contrust_img_btn:
                startActivity(new Intent(DetailCannotFinishActivity.this, DisplayConstructImgActivity.class));
                break;
            case R.id.check_reason_btn:
                startActivity(new Intent(DetailCannotFinishActivity.this, CannotFinishActivity.class));
                break;
        }
    }


}
