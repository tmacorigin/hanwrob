package com.toolset.state;

import android.os.Looper;
import android.util.Log;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.internet.InternetComponent;

import org.json.JSONException;
import org.json.JSONObject;

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
        ExpCommandE expCommandE = new ExpCommandE("STATE_CONTROL_COMMAND");
        expCommandE.AddAExpProperty(new Property("internalMessageName","loginRequest"));
        expCommandE.AddAProperty(new Property("phone", ""));
        expCommandE.AddAProperty(new Property("password", ""));
        EventBus.getDefault().post(expCommandE);

        this.commonReq(e);
    }

    public void onEvent(Object event) {

    }

    @Override
    public void user_loginRsp(ExpCommandE e) {

//        ExpCommandE pe = e.clone();
        e.SetCommand("STATE_CONTROL_COMMAND");
        EventBus.getDefault().post(e);
    }

    @Override
    public void getTaskListReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void getTaskListRsp(ExpCommandE e) {
//        ExpCommandE pe = e.clone();
        e.SetCommand("STATE_CONTROL_COMMAND");
        EventBus.getDefault().post(e);
    }

    @Override
    public void updateLocationReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void updateLocationRsp(ExpCommandE e) {
//        String reState = (String) (e.GetProperty("HTTP_REQ_RSP").GetPropertyContext());
//        String successValue = null;
//        try {
//            JSONObject jsonObject = new JSONObject(reState);
//            successValue = jsonObject.getString("status");
//            if (successValue.equals("0")){
//
//            }else{
//
//            }
//        } catch (JSONException e1) {
//            e1.printStackTrace();
//        }
    }

    @Override
    public void tryToShootTaskReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void tryToShootTaskRsp(ExpCommandE e) {

    }

    @Override
    public void getSpecifyTaskDetailReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void getSpecifyTaskDetailRsp(ExpCommandE e) {

    }

    @Override
    public void commitTaskResultReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void commitTaskResultRsp(ExpCommandE e) {

    }

    @Override
    public void getAlertMessageReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void getAlertMessageRsp(ExpCommandE e) {

    }

    @Override
    public void submitFileReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void submitFileRsp(ExpCommandE e) {

    }

    @Override
    public void getLastVersionReq(ExpCommandE e) {
        this.commonReq(e);
    }

    @Override
    public void getLastVersionRsp(ExpCommandE e) {

    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }
}
