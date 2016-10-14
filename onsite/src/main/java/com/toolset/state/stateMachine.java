package com.toolset.state;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dmdataDemo;
import com.toolset.internet.WebApiDemo;
import com.toolset.state.dataBean.TelNumInfo;

import java.util.ArrayList;


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
        this.mContext = mContext;
    }

    public boolean mainControl( ExpCommandE e )
    {

        boolean ret = true;
        String eventName =  (String)(e.GetExpProperty("rspFunctionName").GetPropertyContext());
        if( eventName == null )
        {
            eventName =  (String)(e.GetExpProperty("internalMessageName").GetPropertyContext());

        }
        Log.d(this.getClass().getName() , "eventName = " +eventName  );

        switch( state )
        {
            case stateMachine.STATE_NULL:
            if( eventName.equals("startUp") )
            {
                //send regist
                dataManager dm = new dataManager( mContext );
                dm.addA_Class( TelNumInfo.class );
                ArrayList<Object> getDataList = dm.getAll(TelNumInfo.class);

                if( getDataList == null || getDataList.size() == 0 )
                {
                    //start login ACTIVITY
                }
                else
                {
                    TelNumInfo telNumInfo = (TelNumInfo) getDataList.get(0);
                    TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
                    String imei = mTm.getDeviceId();
                    String imsi = mTm.getSubscriberId();


                    if(  ( imei != null )
                            &&(imei.equals( telNumInfo.getImei()))
                            &&(imsi!=null)
                            &&(imsi.equals( telNumInfo.getImsi()))
                            )
                    {
                        ExpCommandE login = new ExpCommandE();
                        login.AddAProperty( new Property( "phone",telNumInfo.getTel()));
                        login.AddAProperty( new Property( "password",telNumInfo.getPassWord()));
                        WebApiDemo.getInstance().user_loginReq(login);
                        setState(stateMachine.STATE_WAIT_LOGIN );
                        //start trans activity
                    }

                    //
                }

//
            }


            break;

            case stateMachine.STATE_WAIT_LOGIN:
                if( eventName.equals("user_loginRsp") )
                {
                    String reState =  (String)(e.GetProperty("success").GetPropertyContext());
                    if(reState.equals("1")){
                        setState(stateMachine.STATE_NORMAL );
                        //start normal ACTIVITY
                    }else if(reState.equals("0")){
                        setState(stateMachine.STATE_NULL );
                        //start login ACTIVITY
                    }
                }
                break;
            case stateMachine.STATE_NORMAL:
                if( eventName.equals("netConnect") ){
                    String reState =  (String)(e.GetProperty("netState").GetPropertyContext());
                    if(reState.equals("disconnect")){
                        setState(stateMachine.STATE_OUT_OF_SERVICE );
                    }
                }
                break;
            case stateMachine.STATE_OUT_OF_SERVICE:
                if( eventName.equals("netConnect") ){
                    String reState =  (String)(e.GetProperty("netState").GetPropertyContext());
                    if(reState.equals("connect")){
                        setState(stateMachine.STATE_NORMAL );
                    }
                }
                break;
            default:
                break;
        }
        return true;
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
}
