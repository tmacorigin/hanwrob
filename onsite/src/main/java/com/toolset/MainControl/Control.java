package com.toolset.MainControl;

import android.content.Context;
import android.os.HandlerThread;

import com.toolset.internet.WebApiDemo;
import com.toolset.state.stateMachine;


public class Control extends HandlerThread {

	String TAG = "MainControl";
	final public int COMMAND_NULL	= 0;

	static public final int SEND_MESSAGE_TO_SERVER_RSP = 1;

	private stateMachine sm = new stateMachine(null);

	public Control( String name, Context ma) {
		super(name);
		WebApiDemo.getInstance(this.getLooper());
	}


}
