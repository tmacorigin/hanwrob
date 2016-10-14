package com.toolset.state;

import android.os.Looper;
import android.util.Log;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.internet.InternetComponent;

import de.greenrobot.event.EventBus;


/**
 * Created by wanghp1 on 2016/9/29.
 */
public class WebApiII extends  InternetComponent{

    //必须先调用此函数

    public WebApiII(){
        EventBus.getDefault().register(this);
    }

    public static InternetComponent getInstance(  Looper looper  )
    {
        if( me != null )
        {
            return me;
        }
        else
        {
            me = new WebApiII();
            me.setLooper(looper);
            return me;
        }
    }

    public static InternetComponent getInstance(   )
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

    static public String WEBSITE_ADDRESS_registReq = WEBSITE_ADDRESS_BASE + "media/";
    @Override
    public void registReq(ExpCommandE e) {

        this.commonReq(e);
    }

    @Override
    public void registRsp(ExpCommandE e) {
        Log.d("gfds", "registRsp: hwq");
    }

    @Override
    public void user_loginReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void user_loginRsp(ExpCommandE e) {

        ExpCommandE pe = e.clone();
        pe.setCmd("STATE_CONTROL_COMMAND");
        EventBus.getDefault().post(pe);
    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }
}
