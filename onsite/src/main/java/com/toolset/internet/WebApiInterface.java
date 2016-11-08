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

    public void getSpecifyTaskDetailReq(ExpCommandE e);
    public void getSpecifyTaskDetailRsp(ExpCommandE e);

    public void commitTaskResultReq(ExpCommandE e);
    public void commitTaskResultRsp(ExpCommandE e);

    public void getAlertMessageReq(ExpCommandE e);
    public void getAlertMessageRsp(ExpCommandE e);

    public void submitFileReq(ExpCommandE e);
    public void submitFileRsp(ExpCommandE e);

    public void getLastVersionReq(ExpCommandE e);
    public void getLastVersionRsp(ExpCommandE e);

}
