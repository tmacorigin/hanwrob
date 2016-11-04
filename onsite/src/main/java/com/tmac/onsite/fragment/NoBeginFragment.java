/**
 * 
 */
package com.tmac.onsite.fragment;

import java.util.ArrayList;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailNoBeginActivity;
import com.tmac.onsite.adapter.NoBeginAdapter;
import com.tmac.onsite.bean.NoBeginBean;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.utils.RefreshUtils;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullToRefreshLayout.OnRefreshListener;
import com.tmac.onsite.view.PullableListView;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.MainControl.TestControl;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dataManagerdataBase;
import com.toolset.state.WebApiII;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import de.greenrobot.event.EventBus;

/**
 * @author tmac
 */
public class NoBeginFragment extends Fragment {
	
	private PullToRefreshLayout ptrl;
	private PullableListView plv;
	private int state;
	private List<TaskBean> allList = new ArrayList<TaskBean>();
	private NoBeginAdapter adapter;
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {	
			case RefreshUtils.LOAD_SUCCESS:
				if(state == 0){
					allList.clear();	
				}
				allList.addAll((List<TaskBean>)msg.obj);
				adapter.notifyDataSetChanged();
				RefreshUtils.getResultByState(state, ptrl, true);
				break;
			case RefreshUtils.LOAD_FAIL:
				RefreshUtils.getResultByState(state, ptrl, false);
				break;
				
			default:
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("NoBeginFragment", "onCreateView");
		EventBus.getDefault().register(this);
		return inflater.inflate(R.layout.nobegin_pull_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("NoBeginFragment", "onViewCreated");
		if(TestControl.isTest){
			dataManager dm = dataManager.getInstance(getActivity());
			dm.addA_Class(TaskBean.class);
			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
			for (int index = 0;index < getDataList.size(); index ++){
				TaskBean taskBean = (TaskBean) getDataList.get(index);
				if(taskBean.getTaskState().equals("1")){
					allList.add(taskBean);
				}
			}
//			EventBus.getDefault().register(this);
//			ExpCommandE getTaskE = new ExpCommandE();
//			getTaskE.AddAProperty(new Property("mobile", ""));
//			WebApiII.getInstance(getActivity().getMainLooper()).getTaskListReq(getTaskE);
		}
		initViews(view);
		initEvents();
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("NoBeginFragment", "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("NoBeginFragment", "onResume");
		if(TestControl.isTest){
			allList.clear();
			dataManager dm = dataManager.getInstance(getActivity());
			dm.addA_Class(TaskBean.class);
			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
			for (int index = 0;index < getDataList.size(); index ++){
				TaskBean taskBean = (TaskBean) getDataList.get(index);
				if(taskBean.getTaskState().equals("1")){
					allList.add(taskBean);
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		ptrl = (PullToRefreshLayout) view.findViewById(R.id.send_pull_layout);
		plv = (PullableListView) view.findViewById(R.id.send_pull_listview);
		adapter = new NoBeginAdapter(getActivity(), allList);
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		plv.setAdapter(adapter);
		ptrl.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 0;	
				List<TaskBean> result = new ArrayList<TaskBean>();
				result.add(new TaskBean("02", "ABGh675", "宝山区共康路124号万达", "2016-03-12"));
				result.add(new TaskBean("01", "JHK6758", "宝山区共康路125号万达", "2015-12-12"));
				result.add(new TaskBean("22", "LKHIH67", "宝山区共康路126号万达", "2016-04-26"));
				result.add(new TaskBean("16", "23YUIPK", "宝山区共康路127号万达", "2016-05-19"));
				RefreshUtils.loadSucceed(result, myHandler);						
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 1;
				RefreshUtils.loadFailed(myHandler);
			}
		});
		
		plv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getActivity(), DetailNoBeginActivity.class));
				TaskBean clickBean = allList.get(position);
				if(clickBean.getReadState().equals("0")){
					Log.d("NoBeginFragment", "onItemClick");
					dataManager dm = dataManager.getInstance(getActivity());
					dm.addA_Class(TaskBean.class);
					ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
					for (int index = 0;index < getDataList.size(); index ++){
						TaskBean taskBean = (TaskBean) getDataList.get(index);
						if(taskBean.getTaskState().equals("1") && taskBean.getTaskId().equals(clickBean.getTaskId())){
							taskBean.setReadState("1");
						}
					}
					ArrayList<dataManagerdataBase> tmpList = new ArrayList<dataManagerdataBase>();
					for (int i = 0 ; i < getDataList.size(); i ++){
						tmpList.add((dataManagerdataBase) getDataList.get(i));
					}
					dm.resetdbData(TaskBean.class, tmpList);
				}
			}
		});
	}

	@Override
	public void onDestroy() {
		Log.d("NoBeginFragment", "onViewCreated");
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void onEvent(Object event) {
		assert( event instanceof ExpCommandE);
		ExpCommandE e = (ExpCommandE) event;
		String command = e.GetCommand();

		if( command.equals("GET_DATA_COMMAND") )
		{
			dataManager dm = dataManager.getInstance(getActivity());
			dm.addA_Class(TaskBean.class);
			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
			allList.clear();
			for (int index = 0;index < getDataList.size(); index ++){
				TaskBean taskBean = (TaskBean) getDataList.get(index);
				if(taskBean.getTaskState().equals("0")){
					allList.add(taskBean);
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	
}
