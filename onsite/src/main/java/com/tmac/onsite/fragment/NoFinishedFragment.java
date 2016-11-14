/**
 * 
 */
package com.tmac.onsite.fragment;

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

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailCannotFinishActivity;
import com.tmac.onsite.activity.DetailFinishActivity;
import com.tmac.onsite.adapter.CannotFinishedAdapter;
import com.tmac.onsite.bean.NoBeginBean;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.inter_face.UnreadInfoCallBack;
import com.tmac.onsite.utils.RefreshUtils;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullableListView;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.MainControl.TestControl;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dataManagerdataBase;
import com.toolset.state.WebApiII;
import com.toolset.state.dataBean.TelNumInfo;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.toolset.MainControl.TestControl.DATA_SOURCE;
import static com.toolset.MainControl.TestControl.DB_SOURCE;
import static com.toolset.MainControl.TestControl.INTERNET_SOURCE;
import static com.toolset.MainControl.TestControl.TEST_SOURCE;

/**
 * @author tmac
 */
public class NoFinishedFragment extends Fragment {
	private static final boolean DBG = true;
	private static final String TAG = "LC-NoFinishedFragment";

	private PullToRefreshLayout ptrl;
	private PullableListView plv;
	private int state;
	private List<TaskBean> allList = new ArrayList<TaskBean>();
	private CannotFinishedAdapter adapter;
	private static UnreadInfoCallBack mUnreadInfoCallBack = null;
	private int unreadInfoNum = 0;
	public static boolean isNoFinishedSend = false;
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
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(DBG) Log.d(TAG, "onCreate");
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(DBG) Log.d(TAG, "onCreateView");
		//EventBus.getDefault().register(this);
		return inflater.inflate(R.layout.cannot_finished_pull_layout, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(DBG) Log.d(TAG, "onViewCreated");
//		if(TestControl.isTest){
//			dataManager dm = dataManager.getInstance(getActivity());
//			dm.addA_Class(TaskBean.class);
//			ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
//			for (int index = 0;index < getDataList.size(); index ++){
//				TaskBean taskBean = (TaskBean) getDataList.get(index);
//				if(taskBean.getTaskState().equals("3")){
//					allList.add(taskBean);
//				}
//			}
//			for (int i = 0; i < allList.size(); i ++){
//				if(allList.get(i).getReadState().equals("0")){
//					unreadInfoNum ++;
//				}
//			}
//			mUnreadInfoCallBack.getUnreadInfoNum(unreadInfoNum);
//			EventBus.getDefault().register(this);
//			ExpCommandE getTaskE = new ExpCommandE();
//			getTaskE.AddAProperty(new Property("mobile", ""));
//			WebApiII.getInstance(getActivity().getMainLooper()).getTaskListReq(getTaskE);
//		}else {
//			allList.add(new TaskBean("02", "ABGh675", "宝山区共康路124号万达", "2016-03-12", "0", "0"));
//		}
		initViews(view);
		initEvents();
	}


	private void initViews(View view) {
		// TODO Auto-generated method stub
		ptrl = (PullToRefreshLayout) view.findViewById(R.id.cannot_finished_pull_layout);
		plv = (PullableListView) view.findViewById(R.id.cannot_finished_pull_listview);
		adapter = new CannotFinishedAdapter(getActivity(), allList);
	}

	public static void setUnreadInfoCallBack(UnreadInfoCallBack unreadInfoCallBack){
		mUnreadInfoCallBack = unreadInfoCallBack;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(DBG) Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		if(DBG) Log.d(TAG, "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		if(DBG) Log.d(TAG, "onResume");
		if(!TestControl.isTest){
			getListData();
			if(isNoFinishedSend) {
			unreadInfoNum = 0;
				for (int i = 0; i < allList.size(); i++) {
					if (allList.get(i).getReadState().equals("0")) {
						unreadInfoNum++;
					}
				}
				mUnreadInfoCallBack.getUnreadInfoNum(unreadInfoNum);
			}
		}else {
			allList.add(new TaskBean("02", "ABGh675", "宝山区共康路124号万达", "2016-03-12", "0", "0"));
			adapter.notifyDataSetChanged();
		}
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		plv.setAdapter(adapter);
		ptrl.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				if(DATA_SOURCE == INTERNET_SOURCE){
					ExpCommandE getTaskE = new ExpCommandE();
					dataManager dm = dataManager.getInstance(getActivity());
					dm.addA_Class(TelNumInfo.class);
					ArrayList<Object> getDataList = dm.getAll(TelNumInfo.class);
					getTaskE.AddAProperty(new Property("mobile", ""));
					WebApiII.getInstance(getActivity().getMainLooper()).getTaskListReq(getTaskE);
				}else if(DATA_SOURCE == TEST_SOURCE){
					state = 0;
					List<TaskBean> result = new ArrayList<TaskBean>();
					result.add(new TaskBean("2016.03.13", "ABGh675", "宝山区共康路124号万达", "2016-03-12", "0", "0"));
					result.add(new TaskBean("2016.03.13", "JHK6758", "宝山区共康路125号万达", "2015-12-12", "0", "0"));
					result.add(new TaskBean("2016.03.13", "LKHIH67", "宝山区共康路126号万达", "2016-04-26", "0", "0"));
					result.add(new TaskBean("2016.03.13", "23YUIPK", "宝山区共康路127号万达", "2016-05-19", "0", "0"));
					RefreshUtils.loadSucceed(result, myHandler);
				}else if(DATA_SOURCE == DB_SOURCE){
					getListData();
					RefreshUtils.getResultByState(state, ptrl, true);
				}
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 1;
				RefreshUtils.loadFailed(myHandler);
			}
		});

		plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), DetailCannotFinishActivity.class));
				TaskBean clickBean = allList.get(position);
				if(clickBean.getReadState().equals("0")){
					dataManager dm = dataManager.getInstance(getActivity());
					dm.addA_Class(TaskBean.class);
					ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
					for (int index = 0;index < getDataList.size(); index ++){
						TaskBean taskBean = (TaskBean) getDataList.get(index);
						if(taskBean.getTaskState().equals("3") && taskBean.getTaskId().equals(clickBean.getTaskId())){
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

	private void getListData(){
		allList.clear();
		dataManager dm = dataManager.getInstance(getActivity());
		dm.addA_Class(TaskBean.class);
		ArrayList<Object> getDataList = dm.getAll(TaskBean.class);
		for (int index = 0;index < getDataList.size(); index ++){
			TaskBean taskBean = (TaskBean) getDataList.get(index);
			if(taskBean.getTaskState().equals("3")){
				allList.add(taskBean);
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		if(DBG) Log.d(TAG, "onPause");
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void onEvent(Object event) {
		Log.d("NoFinishedFragment", "onEvent");
		assert( event instanceof ExpCommandE);
		ExpCommandE e = (ExpCommandE) event;
		String command = e.GetCommand();

		if( command.equals("GET_DATA_COMMAND") )
		{
			getListData();
			RefreshUtils.getResultByState(state, ptrl, true);
		}
	}

}
