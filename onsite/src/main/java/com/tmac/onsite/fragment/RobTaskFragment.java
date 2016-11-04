/**
 * 
 */
package com.tmac.onsite.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailTaskActivity;
import com.tmac.onsite.adapter.RobAdapter;
import com.tmac.onsite.bean.RobBean;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.utils.FindViewById;
import com.tmac.onsite.utils.RefreshUtils;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullableListView;
import com.tmac.onsite.view.PullToRefreshLayout.OnRefreshListener;
import com.toolset.CommandParser.ExpCommandE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import de.greenrobot.event.EventBus;

import com.toolset.CommandParser.Property;
import com.toolset.MainControl.TestControl;
import com.toolset.dataManager.dataManager;
import com.toolset.state.WebApiII;
import com.toolset.state.dataBean.TelNumInfo;

/**
 * @author tmac
 */
public class RobTaskFragment extends Fragment {

	private static final boolean DBG = true;
	private static final String TAG = "LC-RobTaskFragment";

	private PullToRefreshLayout pull_layout;
	private PullableListView pull_listview;
	private List<TaskBean> allList = new ArrayList<TaskBean>();
	private RobAdapter adapter;
	private int state;
	private Handler myHandler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
            //成功加载
            case RefreshUtils.LOAD_SUCCESS:
                if (state == 0) {
                    allList.clear();
                }
                allList.addAll((List<TaskBean>) msg.obj);
                adapter.notifyDataSetChanged();
                RefreshUtils.getResultByState(state, pull_layout, true);
                break;
            //加载失败
            case RefreshUtils.LOAD_FAIL:
                RefreshUtils.getResultByState(state, pull_layout, false);
                break;
                	
            default:
                break;
        }
		};
	};

	public RobTaskFragment(){

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(DBG) Log.d(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		return inflater.inflate(R.layout.fragment_rob_task, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initViews(view);
		if(TestControl.isTest){
			dataManager dm = dataManager.getInstance(getActivity());
			dm.addA_Class(TaskBean.class);
			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
			for (int index = 0;index < getDataList.size(); index ++){
				TaskBean taskBean = (TaskBean) getDataList.get(index);
				if(taskBean.getTaskState().equals("0")){
					allList.add(taskBean);
				}
			}
//			ExpCommandE getTaskE = new ExpCommandE();
//			getTaskE.AddAProperty(new Property("mobile", ""));
//			WebApiII.getInstance(getActivity().getMainLooper()).getTaskListReq(getTaskE);
		}else {
			initDatas();
		}
		initEvents();
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		pull_layout = (PullToRefreshLayout) view.findViewById(R.id.ptrl);
		pull_listview = (PullableListView) view.findViewById(R.id.plv);
//		allList = new ArrayList<RobBean>();
		adapter = new RobAdapter(getActivity(), allList);
	}
	
	private void initDatas() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd");
        String date = format.format(new Date());
		/*allList.add(new RobBean("CJG23423", "上海市宝山区", date));
		allList.add(new RobBean("CJG78787", "上海市宝山区", date));
		allList.add(new RobBean("CJG09090", "上海市宝山区", date));*/
		allList.add(new TaskBean("CJG23423", "0", "上海市宝山区", date));
		allList.add(new TaskBean("CJG78787", "1", "上海市宝山区", date));
		allList.add(new TaskBean("CJG09090", "3", "上海市宝山区", date));
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		pull_listview.setAdapter(adapter);
		pull_layout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state=0;
				List<TaskBean> result = new ArrayList<TaskBean>();
				TaskBean n = new TaskBean("HGJ98989", "0","上海市浦东西区", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                result.add(n);
                RefreshUtils.loadSucceed(result, myHandler);
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 1;
				RefreshUtils.loadFailed(myHandler);
			}
		});
		
		pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), DetailTaskActivity.class));
			}
		});
		
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void onEvent(Object event) {
		if(DBG) Log.d(TAG, "getDataList = ");
		assert( event instanceof ExpCommandE);
		ExpCommandE e = (ExpCommandE) event;
		String command = e.GetCommand();

		if( command.equals("GET_DATA_COMMAND") )
		{
			dataManager dm = dataManager.getInstance(getActivity());
			dm.addA_Class(TaskBean.class);
			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
			for (int index = 0;index < getDataList.size(); index ++){
				TaskBean taskBean = (TaskBean) getDataList.get(index);
				if(taskBean.getTaskState().equals("0")){
					allList.add(taskBean);
				}
			}
			adapter.notifyDataSetChanged();
			if(DBG) Log.d(TAG, "getDataList = " + getDataList.toString());
		}
	}

}
