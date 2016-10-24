package com.toolset.internet;

import android.util.Log;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.MainControl.TestControl;
import com.toolset.Network.NetworkReceiver;

import java.io.File;



/**
 * Provides utility methods for communicating with the server.
 */
final public class xUtilsHttp {
    /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";
   
    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    

    private xUtilsHttp() {
    }

    public static TestSetInterface mTestSetInterface = null;
    public static void setTestSetInterface(TestSetInterface testSetInterface){
        mTestSetInterface = testSetInterface;
    }

    /**
     * Connects to the server,
     * @return String request response (or null)
     */
    
    //CommandE 0 位置必须是URL 地址
    //需要上传文件的是 uploadFile不为空，否则为null
    public static void httpReq( ExpCommandE command ) {

    	RequestParams params = new RequestParams();
    	
    	ExpCommandE e = command;
        final ExpCommandE reponse = new ExpCommandE( (String) (command.GetExpProperty("rspFunctionName").GetPropertyContext()) );

		reponse.setUserData(e.getUserData());
        reponse.AddAExpProperty( command.GetExpProperty("rspFunctionName") );
        
        String url = (String) command.GetExpPropertyContext("URL");

        Log.d("HTTP", "httpReq : " );
        for( int i = 0 ; i < command.GetExpPropertyNum() ; i++ )
        {
        	Log.d("HTTP", "GetExpProperty:" + command.GetExpProperty(i).GetPropertyName() + " " + command.GetExpProperty(i).GetPropertyContext() );
        }
        for( int i = 0 ; i < command.GetPropertyNum() ; i++ )
        {
        	Log.d("HTTP", "GetProperty:" + command.GetProperty(i).GetPropertyName() + " " + command.GetProperty(i).GetPropertyContext() );
        }



        if(TestControl.isTest)
        {
            ResultStruct data = mTestSetInterface.getReqData();
            //call call interface => String data
             reponse.AddAProperty(new Property("HTTP_REQ_RSP", data.getData()));
             reponse.AddAProperty(new Property("STATUS", data.getStauts()));
             InternetComponent.getInstance().commonRsp(reponse);
        }
        else {

            for (int i = 0; i < command.GetPropertyNum(); i++) {
                String PropertyName = command.GetProperty(i).GetPropertyName();
                Object PropertyContext = command.GetProperty(i).GetPropertyContext();
                if (PropertyContext instanceof String) {
                    params.addBodyParameter(PropertyName, (String) PropertyContext);
                } else if (PropertyContext instanceof File) {
                    Log.e("HTTP", "this is a file " + PropertyContext);
                    params.addBodyParameter(PropertyName, (File) PropertyContext);
                } else {
                    Log.e("HTTP", "no support context type " + PropertyContext.getClass().getSimpleName());
                }

            }


            if (NetworkReceiver.isConnect()) {


                if (Integer.parseInt((String) ((ExpCommandE) command).GetExpPropertyContext("EventDefine")) == InternetComponent.DOWNLOAD_AUDIO_REQ) {
                    String s = null;
                    try {
                        // 同步方法，获取服务器端返回的流
                        HttpUtils http = new HttpUtils();
                        ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST, url,
                                params);
                        byte out[] = new byte[(int) (responseStream.getContentLength())];
                        responseStream.read(out);

                        Log.d("HTTP", "httpReqRsp  stream data return  ");
                        reponse.AddAProperty(new Property("HTTP_REQ_RSP", out));
                        reponse.AddAProperty(new Property("STATUS", "0"));
                        InternetComponent.getInstance().commonRsp(reponse);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        reponse.AddAProperty(new Property("HTTP_REQ_RSP", "error"));
                        reponse.AddAProperty(new Property("STATUS", "2"));
                        InternetComponent.getInstance().commonRsp(reponse);
                    }
                } else {

                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST, url, params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                    if (isUploading) {

                                    } else {

                                    }
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    //ExpCommandE
                                    Log.d("HTTP", "httpReqRsp  result: " + responseInfo.result);
                                    reponse.AddAProperty(new Property("HTTP_REQ_RSP", responseInfo.result));
                                    reponse.AddAProperty(new Property("STATUS", "0"));

                                    InternetComponent.getInstance().commonRsp(reponse);


                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Log.d("HTTP", "httpReqRsp : error");
                                    reponse.AddAProperty(new Property("HTTP_REQ_RSP", "error"));
                                    reponse.AddAProperty(new Property("STATUS", "1"));
                                    InternetComponent.getInstance().commonRsp(reponse);
                                }
                            });
                }
            } else {
                Log.d("xUtilsHttp", "no network");

                reponse.AddAProperty(new Property("HTTP_REQ_RSP", "error"));
                reponse.AddAProperty(new Property("STATUS", "2"));
                InternetComponent.getInstance().commonRsp(reponse);
            }
        }
        
        
    }
    
   

}
