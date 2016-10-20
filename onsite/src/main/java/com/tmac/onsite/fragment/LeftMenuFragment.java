/**
 * 
 */
package com.tmac.onsite.fragment;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.UseDirectionActivity;
import com.tmac.onsite.updateversion.CheckVersionTask;
import com.tmac.onsite.updateversion.DownLoadManager;
import com.tmac.onsite.updateversion.UpdateInfo;


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
import android.widget.LinearLayout;
import android.widget.Toast;
import com.tmac.onsite.view.CommonDialog;


/**
 * @author tmac
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener, CommonDialog.OnDialogListenerInterface{

	private LinearLayout check_update;
	private LinearLayout use_direction;
	private LinearLayout exit_avai;
	private static final boolean DBG = true;
	private static final String TAG = "LC-LeftMenuFragment";
	public static final int UPDATE_NONEED = 0;
	public static final int UPDATE_NEED = 1;
	public static final int GETUPDATE_INFO_ERROR = 2;
	public static final int DOWN_ERROR = 3;
	public static final int DEFAULT_SITUATION = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_leftmenu, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		check_update = (LinearLayout) view.findViewById(R.id.update_layout);
		use_direction = (LinearLayout) view.findViewById(R.id.use_layout);
		exit_avai = (LinearLayout) view.findViewById(R.id.exit_avai);
		initEvents();
	}

	private void initEvents() {
		check_update.setOnClickListener(this);
		use_direction.setOnClickListener(this);
		exit_avai.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.update_layout:
				// 检查服务器上的apk VersionName和当前的apk 的VersioName是否一致
				CheckVersionTask cvTask = new CheckVersionTask(getActivity(), handler);
				new Thread(cvTask).start();
				break;
			case R.id.use_layout:
				startActivity(new Intent(getActivity(), UseDirectionActivity.class));
				break;
			case R.id.exit_avai:
				CommonDialog commonDialog = new CommonDialog(getActivity(), getResources().getString(R.string.menu_dialog_msg), getResources().getString(R.string.menu_dialog_ensure), getResources().getString(R.string.menu_dialog_cancel), DEFAULT_SITUATION, this);
				commonDialog.show();
				break;
			default:
				break;
		}
	}



	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case UPDATE_NONEED:
					Toast.makeText(getActivity().getApplicationContext(), "当前已是最新版本", Toast.LENGTH_LONG).show();
					break;
				case UPDATE_NEED:
					UpdateInfo info = (UpdateInfo) msg.obj;
					// 对话框通知用户升级程序
					DownLoadManager.showUpdateDialog(getActivity(), handler, info);
					break;
				case GETUPDATE_INFO_ERROR:
					// 服务器超时
					Toast.makeText(getActivity().getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
					break;
				case DOWN_ERROR:
					//下载apk失败
					Toast.makeText(getActivity().getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	private OnExitAvaiListener mListener;
	public void setOnExitAvaiListener(OnExitAvaiListener listener){
		this.mListener = listener;
	}

	@Override
	public void doConfirm(int situation) {
		mListener.onExitAvai();
	}

	public interface OnExitAvaiListener{
		void onExitAvai();
	}
}
