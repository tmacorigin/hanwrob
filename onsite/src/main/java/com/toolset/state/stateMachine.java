package com.toolset.state;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.tmac.onsite.activity.ActivationActivity;
import com.tmac.onsite.activity.MainActivity;
import com.tmac.onsite.bean.TaskBean;
import com.toolset.CommandParser.CommandE;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dataManagerdataBase;
import com.toolset.dataManager.dmdataDemo;
import com.toolset.internet.WebApiDemo;
import com.toolset.state.dataBean.TelNumInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * Created by wanghp1 on 2016/9/27.
 */
public class stateMachine implements stateControlInterface {

    static final int STATE_NULL    			= 0;
    static final int STATE_WAIT_LOGIN 		= 0x01;
    static final int STATE_NORMAL     		= 0x02;
    static final int STATE_OUT_OF_SERVICE 	= 0x04;

    private int state         = STATE_NULL;
    private int preState     = STATE_NULL;

    private Context mContext = null;

    public stateMachine(Context mContext){
        EventBus.getDefault().register(this);
        this.mContext = mContext;
    }

    public boolean mainControl( ExpCommandE e )
    {
        boolean ret = true;
        String eventName  = null;
        String userDataStr = (String) e.getUserData();
        if(e.GetExpProperty("rspFunctionName") != null){
            eventName = (String)(e.GetExpProperty("rspFunctionName").GetPropertyContext());
        }
        if( eventName == null )
        {
            if(e.GetExpProperty("internalMessageName") != null){
                eventName = (String)(e.GetExpProperty("internalMessageName").GetPropertyContext());
            }
        }
        Log.d(this.getClass().getName() , "eventName = " +eventName  );
        if (eventName != null) {
            if (eventName.equals("loginRequest")) {
                //send regist
                dataManager dm = dataManager.getInstance(mContext);
                dm.addA_Class(TelNumInfo.class);
                ArrayList<dataManagerdataBase> telNumInfoList = new ArrayList<dataManagerdataBase>();


                TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
                String imei = mTm.getDeviceId();
                String imsi = mTm.getSubscriberId();


                String tel = (String) (e.GetProperty("phone").GetPropertyContext());
                String passWord = (String) (e.GetProperty("password").GetPropertyContext());
                TelNumInfo telNumInfo = new TelNumInfo(tel, passWord, imei, imsi);
                telNumInfoList.add(telNumInfo);
                dm.resetdbData(TelNumInfo.class, telNumInfoList);

            }
            switch (state) {
                case stateMachine.STATE_NULL:
                    if (eventName.equals("startUp")) {
                        //send regist
                        dataManager dm = dataManager.getInstance(mContext);
                        dm.addA_Class(TelNumInfo.class);
                        ArrayList<Object> getDataList = null;
//                        ArrayList<Object> getDataList = dm.getAll(TelNumInfo.class);

                        if (getDataList == null || getDataList.size() == 0) {
                            /*Intent intent = new Intent(mContext, ActivationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);*/
                            if(userDataStr.equals("unauto")) {
                                Intent intent = new Intent(mContext, ActivationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }else{
                                setState(stateMachine.STATE_NULL);
                            }
                        } else {
                            TelNumInfo telNumInfo = (TelNumInfo) getDataList.get(0);
                            TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
                            String imei = mTm.getDeviceId();
                            String imsi = mTm.getSubscriberId();


                            if ((imei != null)
                                    && (imei.equals(telNumInfo.getImei()))
                                    && (imsi != null)
                                    && (imsi.equals(telNumInfo.getImsi()))
                                    ) {
                                ExpCommandE login = new ExpCommandE();
                                login.AddAProperty(new Property("phone", telNumInfo.getTel()));
                                login.AddAProperty(new Property("password", telNumInfo.getPassWord()));

                                //start trans activity
                                if(userDataStr.equals("unauto")) {
                                    WebApiII.getInstance(mContext.getMainLooper()).user_loginReq(login);
                                    setState(stateMachine.STATE_WAIT_LOGIN);
//                                    Intent intent = new Intent(mContext, ActivationActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mContext.startActivity(intent);
                                }else{
                                    setState(stateMachine.STATE_NULL);
                                }
                            } else {
                                //start login ACTIVITY
                                if(userDataStr.equals("unauto")) {
                                    Intent intent = new Intent(mContext, ActivationActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                }else{
                                    setState(stateMachine.STATE_NULL);
                                }
                            }
                            //
                        }

//
                    }
                    if(eventName.equals("loginRequest")){
                        setState(stateMachine.STATE_WAIT_LOGIN);
                    }


                    break;

                case stateMachine.STATE_WAIT_LOGIN:
                    if (eventName.equals("user_loginRsp")) {
                        String reState = (String) (e.GetProperty("HTTP_REQ_RSP").GetPropertyContext());
                        String successValue = null;
                        try {
                            JSONObject jsonObject = new JSONObject(reState);
                            successValue = jsonObject.getString("success");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        if(successValue != null) {
                            if (successValue.equals("1")) {
                                setState(stateMachine.STATE_NORMAL);
//                                dataManager dm = dataManager.getInstance(mContext);
//                                dm.addA_Class(TelNumInfo.class);
//                                ArrayList<Object> getDataList = dm.getAll(TelNumInfo.class);
//                                TelNumInfo telNumInfo = (TelNumInfo) getDataList.get(0);

                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);

                                ExpCommandE getTaskE = new ExpCommandE();
                                e.AddAProperty(new Property("mobile", ""));
                                WebApiII.getInstance(mContext.getMainLooper()).getTaskListReq(getTaskE);
                                //start normal ACTIVITY

                            } else if (successValue.equals("0")) {
                                setState(stateMachine.STATE_NULL);
                                //start login ACTIVITY
                            }
                        }
                    }
                    break;
                case stateMachine.STATE_NORMAL:
                    if (eventName.equals("netConnect")) {
                        Log.d( "stateMachine","netConnect  + STATE_NORMAL" );
                        String reState = (String) (e.GetProperty("netState").GetPropertyContext());
                        if (reState.equals("disconnect")) {
                            Log.d( "stateMachine","disconnect" );
                            setState(stateMachine.STATE_OUT_OF_SERVICE);
                        }
                    }
                    if (eventName.equals("getTaskListRsp")) {
                        robotScanRspHandle(e);
                    }
                    break;
                case stateMachine.STATE_OUT_OF_SERVICE:
                    if (eventName.equals("netConnect")) {
                        Log.d( "stateMachine","netConnect  + STATE_OUT_OF_SERVICE" );
                        String reState = (String) (e.GetProperty("netState").GetPropertyContext());
                        if (reState.equals("connect")) {
                            Log.d( "stateMachine","connect" );
                            setState(stateMachine.STATE_NORMAL);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private void robotScanRspHandle(ExpCommandE e) {
        int status = -1;
        String rep = (String) e.GetPropertyContext("HTTP_REQ_RSP");
        JSONObject json_obj = null;
        try {
            json_obj = new JSONObject(rep);
            if (json_obj != null) status = json_obj.getInt("status");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        if (status == 0) {
            dataManager dm = dataManager.getInstance(mContext);
            dm.addA_Class(TaskBean.class);
            ArrayList<Object> originData = dm.getAll(TaskBean.class);

            ArrayList<dataManagerdataBase> robotList = new ArrayList<dataManagerdataBase>();
            JSONArray robotsArray = null;
            try {
                String taskStr = json_obj.getString("tasks");
                robotsArray = new JSONArray(taskStr);
                for (int i = 0; i < robotsArray.length(); i++) {
                    JSONObject obj = (JSONObject) robotsArray.get(i);

                    String taskId = obj.getString("taskId");
                    String taskState = obj.getString("taskState");
                    String preformAddress = obj.getString("preformAddress");
                    String finishedTime = obj.getString("finishedTime");
                    String robState = obj.getString("robState");
                    String readState = "0";
                    if(originData != null && originData.size() > 0){
                        for (int k = 0; k < originData.size(); k ++){
                            TaskBean tmpBean = (TaskBean)originData.get(k);
                            if(taskId.equals(tmpBean.getTaskId())){
                                readState = tmpBean.getReadState();
                            }
                        }
                    }
                    robotList.add(new TaskBean(taskId, taskState, preformAddress, finishedTime, robState, readState));

                }

                dm.resetdbData(TaskBean.class, robotList);

                ExpCommandE expCommandE = new ExpCommandE("GET_DATA_COMMAND");
                EventBus.getDefault().post(expCommandE);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

    }


    @Override
    public boolean flowPermissionControl(ExpCommandE e) {

        boolean ret = true;
        String eventName =  (String)(e.GetExpProperty("apiFunctionName").GetPropertyContext());
        if( eventName == null )
        {
            eventName =  (String)(e.GetExpProperty("internalMessageName").GetPropertyContext());

        }
        Log.d(this.getClass().getName() , "eventName = " +eventName  );

        switch( state )
        {
            case stateMachine.STATE_NULL:
                //if need send reqist req

                break;

            case stateMachine.STATE_WAIT_LOGIN:
                break;
            case stateMachine.STATE_NORMAL:
                break;
            case stateMachine.STATE_OUT_OF_SERVICE:
                break;
            default:
                break;
        }
        return true;
    }

    public void setState( int newState )
    {
        preState = state;
        state = newState;
        Log.d( "stateMachine","setState " + newState );


    }

    public void onEvent(Object event) {
    }
}
