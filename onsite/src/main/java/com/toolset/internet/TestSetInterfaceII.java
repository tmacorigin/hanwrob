package com.toolset.internet;

import java.util.ArrayList;

/**
 * Created by pactera on 2016/10/27.
 */
public class TestSetInterfaceII implements TestSetInterface {
    private int index = 0;
    private ArrayList<ResultStruct> dataList = null;
    @Override
    public ResultStruct getReqData() {
        ResultStruct value = null;
        if(index == 0){
            value = dataList.get(index ++);
        }else{
            value = dataList.get(index);
        }
        return value;
    }

    @Override
    public void setReqDataList(ArrayList<ResultStruct> dataList) {
        this.dataList = dataList;
    }
}
