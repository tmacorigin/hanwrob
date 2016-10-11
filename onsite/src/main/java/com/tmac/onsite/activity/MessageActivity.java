package com.tmac.onsite.activity;

import com.tmac.onsite.adapter.MessageAdapter;
import com.tmac.onsite.bean.MessageBean;
import com.toolset.activity.basicActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tmac.onsite.R;
import com.toolset.activity.headerCtrl;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends basicActivity {

    private LinearLayout zdnHeaderLayout;
    private headerCtrl hc;
    private ListView msg_Lv;
    private MessageAdapter adapter;
    private List<MessageBean> allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
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
        zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
        if(zdnHeaderLayout != null){
            hc = new headerCtrl(zdnHeaderLayout, this);
            hc.setTitle("消息");
            hc.setHeaderRightImage(R.drawable.msg_edit);
        }

        msg_Lv = (ListView) findViewById(R.id.msgListView);
    }


}
