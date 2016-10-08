package com.toolset.state;

import android.util.Log;

import com.toolset.CommandParser.ExpCommandE;


/**
 * Created by wanghp1 on 2016/9/27.
 */
public class stateMachine implements stateControlInterface {

    static final int STATE_NULL    			= 0;
    static final int STATE_WAIT_REGIST 	    = 0x01;
    static final int STATE_WAIT_LOGIN 		= 0x02;
    static final int STATE_NORMAL     		= 0x04;
    static final int STATE_OUT_OF_SERVICE 	= 0x08;

    private int state         = STATE_NULL;
    private int preState     = STATE_NULL;

    @Override
    public boolean continueControl(ExpCommandE e) {

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
                {
                    //send regist

                    this.setState(stateMachine.STATE_WAIT_REGIST);
                }
                //else
                // if exist user name and password
                {
                    // send login
                    this.setState(stateMachine.STATE_WAIT_LOGIN );
                }
                //else
                {
                    //显示登陆界面
                    //stay in null , show log in ui screen
                }

                if( eventName.equals("internalLogin") )
                {
                    this.setState( stateMachine.STATE_WAIT_LOGIN );
                }

                break;
            case stateMachine.STATE_WAIT_REGIST:
                if ( eventName.equals(""))
                {
                    if( true/* regist result ok */)
                    {
                        //send login
                        this.setState(stateMachine.STATE_WAIT_LOGIN );
                    }
                    else
                    {
                        this.setState( stateMachine.STATE_NULL );
                    }
                }
                else
                {
                    ret = false;
                }
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

    private void setState( int newState )
    {
        preState = state;
        state = newState;
        Log.d( "stateMachine","setState " + newState );


    }
}
