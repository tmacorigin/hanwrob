package com.toolset.internet;

import android.os.Looper;
import android.util.Log;

import com.toolset.CommandParser.CommandE;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;


/**
 * Created by wanghp1 on 2016/9/29.
 */
public class WebApiDemo extends  InternetComponent{

    //必须先调用此函数

    public static InternetComponent getInstance(  Looper looper  )
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
    }

    @Override
    public void getTaskListReq(ExpCommandE e) {

    }

    @Override
    public void getTaskListRsp(ExpCommandE e) {

    }


}
