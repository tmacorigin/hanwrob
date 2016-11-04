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
        TaskBean taskBean1 = new TaskBean("001","0","上海","2016-11-26","1");
        taskBeanArrayList.add(taskBean1);
        TaskBean taskBean2 = new TaskBean("002","1","北京","2016-11-07","1");
        taskBeanArrayList.add(taskBean2);
        TaskBean taskBean3 = new TaskBean("003","2","广州","2016-11-25","1");
        taskBeanArrayList.add(taskBean3);
        TaskBean taskBean4 = new TaskBean("004","3","深圳","2016-10-24","0");
        taskBeanArrayList.add(taskBean4);
        TaskBean taskBean5 = new TaskBean("005","4","浙江","2016-10-23","1");
        taskBeanArrayList.add(taskBean5);
        TaskBean taskBean6 = new TaskBean("006","0","江苏","2016-10-22","1");
        taskBeanArrayList.add(taskBean6);
        TaskBean taskBean7 = new TaskBean("007","1","成都","2016-11-12","0");
        taskBeanArrayList.add(taskBean7);
        TaskBean taskBean8 = new TaskBean("008","2","安徽","2016-10-20","1");
        taskBeanArrayList.add(taskBean8);

        TaskBean taskBean9 = new TaskBean("009","3","合肥","2016-10-19","0");
        taskBeanArrayList.add(taskBean9);
        TaskBean taskBean10 = new TaskBean("010","4","湖南","2016-10-18","1");
        taskBeanArrayList.add(taskBean10);
        TaskBean taskBean11 = new TaskBean("011","0","湖北","2016-10-17","1");
        taskBeanArrayList.add(taskBean11);
        TaskBean taskBean12 = new TaskBean("012","1","巢湖","2016-11-10","0");
        taskBeanArrayList.add(taskBean12);
        TaskBean taskBean13 = new TaskBean("013","2","武汉","2016-10-15","1");
        taskBeanArrayList.add(taskBean13);
        TaskBean taskBean14 = new TaskBean("014","3","广东","2016-10-14","0");
        taskBeanArrayList.add(taskBean14);
        TaskBean taskBean15 = new TaskBean("015","4","杭州","2016-10-13","1");
        taskBeanArrayList.add(taskBean15);
        TaskBean taskBean16 = new TaskBean("016","0","苏州","2016-10-12","0");
        taskBeanArrayList.add(taskBean16);

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
        Intent intent = new Intent(appContext, MainService.class);
        intent.putExtra("launcher","unauto");
        appContext.startService(intent);

        while (true){}

    }
}
