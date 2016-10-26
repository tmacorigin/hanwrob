package com.tmac.onsite.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.MainActivity;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.PhoneEditText;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.MainControl.TestControl;
import com.toolset.state.WebApiII;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText password;
    private ImageView clear;
    private Button login;
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this, 0);

        initViews();
        initEvents();

    }

    private void initViews() {
        password = (EditText) findViewById(R.id.login_password);
        clear = (ImageView) findViewById(R.id.login_clear);
        login = (Button) findViewById(R.id.login);
        back = (LinearLayout) findViewById(R.id.login_back);
    }

    private void initEvents() {
        clear.setOnClickListener(this);
        login.setOnClickListener(this);
        back.setOnClickListener(this);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = password.getText().toString().trim().length();
                if (length > 0) {
                    clear.setVisibility(View.VISIBLE);
                    if (length >= 6) {
                        login.setBackgroundResource(R.drawable.btn_orange_bg);
                        login.setEnabled(true);
                    } else {
                        login.setBackgroundResource(R.drawable.btn_gray_bg);
                        login.setEnabled(false);
                    }
                } else {
                    clear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                // 验证密码是否正确
				if(TestControl.isTest) {
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					finish();
				}
				else {
					ExpCommandE e = new ExpCommandE();
					e.AddAProperty(new Property("name", ""));
					e.AddAProperty(new Property("password", ""));
					WebApiII.getInstance(getMainLooper()).user_loginReq(e);
//				EventBus.getDefault().post(e);
				}
                break;
            case R.id.login_back:
                finish();
                break;
            case R.id.login_clear:
                password.setText(null);
                break;
        }
    }
}

