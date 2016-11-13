package com.tmac.onsite.activity;

import com.tmac.onsite.adapter.MessageAdapter;
import com.tmac.onsite.bean.MessageBean;
import com.toolset.activity.basicActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tmac.onsite.R;
import com.toolset.activity.headerCtrl;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends basicActivity {

    private ListView msg_Lv;
    private MessageAdapter adapter;
    private List<MessageBean> allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message);
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {
        allList.add(new MessageBean("你的项目申请未被通过", "HG12757", "10-04 18:23:22"));
        allList.add(new MessageBean("你的派单已被撤销", "YH7567YT", "10-04 18:23:22"));
        allList.add(new MessageBean("你的项目申请未被通过", "KYU795YU", "10-04 18:23:22"));
        adapter = new MessageAdapter(this, allList);
        msg_Lv.setAdapter(adapter);
    }

    private void initDatas() {
        allList = new ArrayList<MessageBean>();
    }

    private void initViews() {
        hc.setTitle("消息");
        hc.setHeaderRightText("完成");
        //hc.setHeaderRightImage(R.drawable.msg_edit);
        msg_Lv = (ListView) findViewById(R.id.msgListView);
    }


    @Override
    public void onMenuClick(int menuId) {
        super.onMenuClick(menuId);
        switch (menuId){
            case R.id.headRight:
                adapter.setEditVisiable(true);
                hc.headerRight.setVisibility(View.GONE);
                hc.headerRight_tv.setVisibility(View.VISIBLE);
                break;
            case R.id.headRight_tv:
                adapter.setEditVisiable(false);
                hc.headerRight.setVisibility(View.VISIBLE);
                hc.headerRight_tv.setVisibility(View.GONE);
                break;
        }
    }
}
