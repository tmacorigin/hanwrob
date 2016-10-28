package com.toolset.internet;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmac.onsite.bean.TaskBean;
import com.tmac.onsite.service.MainService;
import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InternetApiTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "test";

    public void testInternerApi() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ObjectMapper mapper = new ObjectMapper();
        TestSetInterfaceII testSetInterfaceII = new TestSetInterfaceII();
        ArrayList<ResultStruct> resultStructArrayList = new ArrayList<ResultStruct>();
        Map<String,String> dataMap = new HashMap<String, String>();
        dataMap.put("success","1");
        dataMap.put("status","0");
        try {
            String loginJson =  mapper.writeValueAsString(dataMap);
            ResultStruct resultStruct1 = new ResultStruct();
            resultStruct1.setWhich(0);
            resultStruct1.setStatus("0");
            resultStruct1.setData(loginJson);
            resultStructArrayList.add(resultStruct1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<TaskBean> taskBeanArrayList = new ArrayList<TaskBean>();
        TaskBean taskBean1 = new TaskBean("001","0","hhhh","20161026");
        taskBeanArrayList.add(taskBean1);
        TaskBean taskBean2 = new TaskBean("002","1","wwww","20161027");
        taskBeanArrayList.add(taskBean2);

        String json = null;
        try {
            json =  mapper.writeValueAsString(taskBeanArrayList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String,String> taskMap = new HashMap<String, String>();
        taskMap.put("status", "0");
        taskMap.put("tasks", json);
        try {
            String TaskJson =  mapper.writeValueAsString(taskMap);
            ResultStruct resultStruct2 = new ResultStruct();
            resultStruct2.setWhich(1);
            resultStruct2.setStatus("0");
            resultStruct2.setData(TaskJson);
            resultStructArrayList.add(resultStruct2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        testSetInterfaceII.setReqDataList(resultStructArrayList);
        xUtilsHttp.setTestSetInterface(testSetInterfaceII);

        appContext.startService(new Intent(appContext, MainService.class));

        while (true){}

    }
}
