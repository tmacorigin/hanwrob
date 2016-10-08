package com.toolset.state;


import com.toolset.CommandParser.ExpCommandE;

/**
 * Created by wanghp1 on 2016/9/30.
 */
public interface stateControlInterface {

    //消息是否允许继续传递
    boolean continueControl(ExpCommandE e);
}
