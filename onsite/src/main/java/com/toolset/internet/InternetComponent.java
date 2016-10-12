package com.toolset.internet;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.state.stateControlInterface;
import com.toolset.util.ReflectionUtils;


public abstract class InternetComponent implements WebApiInterface {
	static public String WEBSITE_ADDRESS_BASE_NO_SEPARATOR	= "http://10.4.65.32";
	static public String WEBSITE_ADDRESS_BASE	= WEBSITE_ADDRESS_BASE_NO_SEPARATOR +"/";

	protected static InternetComponent me = null;
	private stateControlInterface sci = null;
	/* web site address define */
		//

	public static final int DOWNLOAD_AUDIO_REQ		=1;
	public static final int DOWNLOAD_AUDIO_RSP		= DOWNLOAD_AUDIO_REQ+1;

	static public ThreadTaskHandler handler = null;

	public void setLooper(Looper looper )
	{
		handler = new ThreadTaskHandler(looper);
	}

	public InternetComponent( )
	{
		handler = null;
	}

    //设置状态管理接口
	public void setStateControlInerface( stateControlInterface sci )
	{
		this.sci = sci;
	}
	public static InternetComponent getInstance(  )
	{
		 if( me != null )
		 {
			 return me;
		 }
		else
		{
			return null;
		}
	}

	class ThreadTaskHandler extends Handler {

		static public final int SEND_MESSAGE_TO_SERVER = 1;
		static public final int SEND_MESSAGE_TO_CLIENT = 2;

		public ThreadTaskHandler( Looper looper )
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {

			if(SEND_MESSAGE_TO_SERVER == msg.what )
			{
				ExpCommandE e = (ExpCommandE) msg.obj;
				xUtilsHttp.httpReq( e );
			}
			else if(SEND_MESSAGE_TO_CLIENT == msg.what )
			{
				ExpCommandE e = (ExpCommandE) msg.obj;
				responseDispatch(e);
			}
			super.handleMessage(msg);
		}


	}

	// 用来定义一个web和本地交互的api列表
	//ArrayList<webSiteCommunicateUserDefine>  webApiList = new ArrayList<webSiteCommunicateUserDefine>();

	//xxxReq => xxxRsp
	private String reqFunctionDrivedToRspRunctionName( String reqFunctionName )
	{
		return reqFunctionName.substring( 0 , reqFunctionName.length() - 3 ) + "Rsp";
	}

	private String reqFunctionDrivedToReqAddress( String reqFunctionName )
	{
		return WEBSITE_ADDRESS_BASE + reqFunctionName.substring( 0 , reqFunctionName.length() - 3 ) ;
	}

	public int commonReq( ExpCommandE e   ) {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement se = stacktrace[3];
		String methodName = se.getMethodName();
		Log.i(this.getClass().getSimpleName(), "call " + methodName);
		e.AddAExpProperty(new Property("apiFunctionName",methodName ));
		e.AddAExpProperty(new Property("rspFunctionName",  reqFunctionDrivedToRspRunctionName( methodName ) ) );
		e.AddAExpProperty(new Property("URL", reqFunctionDrivedToReqAddress(methodName)));


		if( ( sci != null) && ( false == sci.continueControl( e ) ) )
		{
			// 不允许继续进行消息传递了
			Log.d(this.getClass().getName() , "API CALL abort for state invalid, methodName = " + methodName );
			return -1;
		}
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		return 0;
	}

	public void responseDispatch( ExpCommandE e )
	{
		String rspFunctionName = (String)(e.GetExpProperty("rspFunctionName").GetPropertyContext());
		e.AddAExpProperty(new Property("apiFunctionName",rspFunctionName ));

		if( ( sci != null) && ( false == sci.continueControl( e ) ) )
		{
			// 不允许继续进行消息传递了
			Log.d(this.getClass().getName() , "API CALL abort for state invalid, methodName = " + rspFunctionName );
			return;
		}
		Class<?> param1[] = new Class<?>[1];
		param1[0] = e.getClass();

		Object param2[] = new Object[1];
		param2[0] = e;
		ReflectionUtils.invokeMethod(InternetComponent.getInstance(), rspFunctionName, param1, param2);
	}

	static public int commonRsp( ExpCommandE e   ) {

		Message msg = handler.obtainMessage();
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_CLIENT;

		msg.obj = e;   //

		handler.sendMessage(msg);
		return 0;
	}




	static public ExpCommandE packA_CommonExpCommandE_ToServer( int eventDefine , String URL )
	{
		ExpCommandE e = new ExpCommandE("SEND_MESSAGE_TO_SERVER");
		e.AddAExpProperty(new Property("EventDefine", Integer.toString(eventDefine) ) );
		e.AddAExpProperty(new Property("URL" ,URL ) );

		
		return e;
	}

	
	static public ExpCommandE packA_CommonExpCommandE_ToMainControl( String name , int eventDefine )
	{
		ExpCommandE e = new ExpCommandE(name);
		e.AddAExpProperty(new Property("EventDefine" ,Integer.toString( eventDefine) ) );
		
		return e;
	}


}
