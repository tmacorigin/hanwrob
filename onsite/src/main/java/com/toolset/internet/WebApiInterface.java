package com.toolset.internet;


import com.toolset.CommandParser.CommandE;
import com.toolset.CommandParser.ExpCommandE;

/**
 * Created by wanghp1 on 2016/9/29.
 */
public interface WebApiInterface {
    public void registReq(ExpCommandE e);
    public void registRsp(ExpCommandE e);

    public void user_loginReq(ExpCommandE e);
    public void user_loginRsp(ExpCommandE e);

    public void getTaskListReq(ExpCommandE e);
    public void getTaskListRsp(ExpCommandE e);

    public void updateLocationReq(ExpCommandE e);
    public void updateLocationRsp(ExpCommandE e);

    public void tryToShootTaskReq(ExpCommandE e);
    public void tryToShootTaskRsp(ExpCommandE e);


}
