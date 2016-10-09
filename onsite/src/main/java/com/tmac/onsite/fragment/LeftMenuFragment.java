/**
 * 
 */
package com.tmac.onsite.fragment;

import com.tmac.onsite.R;
import com.tmac.onsite.updateversion.DownLoadManager;
import com.tmac.onsite.updateversion.UpdataInfoParser;
import com.tmac.onsite.updateversion.UpdateInfo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tmac
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener{

	private String localversionName;
	private UpdateInfo info;
	private TextView check_update;
	private static final String TAG = "LC-LeftMenuFragment";
	private static final int UPDATE_NONEED = 0;
	private static final int UPDATE_NEED = 1;
	private static final int GETUPDATE_INFO_ERROR = 2;
	private static final int DOWN_ERROR = 3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_leftmenu, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		check_update = (TextView) view.findViewById(R.id.check_update);
		initEvents();
	}

	private void initEvents() {
		check_update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.check_update:
				// 获取本地的VersionName
				localversionName = getVersionName();
				CheckVersionTask cvTask = new CheckVersionTask();
				new Thread(cvTask).start();
				break;

			default:
				break;
		}
	}


	// 获取当前app的VersionName
	private String getVersionName() {
		PackageManager packageManager = getActivity().getPackageManager();
		PackageInfo packageInfo = null;
		try {
			// 参数一：包名 参数二：flag 0表示获取版本信息
			packageInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		Log.i(TAG, "versionName = " + packageInfo.versionName);
		return packageInfo.versionName;
	}

	// 检查服务器上的apk VersionName和当前的apk 的VersioName是否一致
	public class CheckVersionTask implements Runnable{
		InputStream is;

		@Override
		public void run() {
			try {
				String path = "http://172.18.18.164:8080/UpdateVersion/updateServlet";
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				if(conn.getResponseCode() == 200){
					is = conn.getInputStream();
				}
				Log.i(TAG, "is = " + is);
				info = UpdataInfoParser.getUpdataInfo(is);
				if(info.getVersion().equals(localversionName)){
					Log.i(TAG, "版本号相同");
					Message msg = new Message();
					msg.what = UPDATE_NONEED;
					handler.sendMessage(msg);
				}else {
					Log.i(TAG, "版本号不同");
					Message msg = new Message();
					msg.what = UPDATE_NEED;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				Log.i(TAG, "exception = " + e.getMessage());
				Message msg = new Message();
				msg.what = GETUPDATE_INFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}

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
					// 对话框通知用户升级程序
					showUpdateDialog();
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

	/**
	 *  弹出对话框的步骤：
	 *  1.创建alertDialog的builder.
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮
	 *  3.通过builder 创建一个对话框
	 *  4.对话框show()出来
	 */
	private void showUpdateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("版本更新");
		builder.setMessage(info.getDescription());
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载APK
				downLoadApk();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}


	/**
	 * 从服务器下载更新版的APK
	 */
	private void downLoadApk(){

		// 进度条对话框
		final ProgressDialog pd;
		pd = new ProgressDialog(getActivity());
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);
					sleep(3000);
					installApk(file);
					// 结束掉进度条对话框
					pd.dismiss();
				} catch (Exception e) {
					Log.i(TAG, "download error = " + e.getMessage());
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装APK
	private void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}



}
