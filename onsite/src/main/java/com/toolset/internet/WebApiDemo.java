package com.toolset.internet;

import android.os.Looper;

import com.toolset.CommandParser.CommandE;


/**
 * Created by wanghp1 on 2016/9/29.
 */
public class WebApiDemo extends  InternetComponent{

    //必须先调用此函数
    static WebApiDemo me = null;

    public static WebApiDemo getInstance(  Looper looper  )
    {
        if( me != null )
        {
            return me;
        }
        else
        {
            me = new WebApiDemo();
            me.setLooper(looper);
            return me;
        }
    }

    public static WebApiDemo getInstance(   )
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
    public void registReq(CommandE e) {

    }

    @Override
    public void registRsp(CommandE e) {

    }


}
