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
        TaskBean taskBean1 = new TaskBean("001","0","上海","20161026");
        taskBeanArrayList.add(taskBean1);
        TaskBean taskBean2 = new TaskBean("002","1","北京","20161027");
        taskBeanArrayList.add(taskBean2);
        TaskBean taskBean3 = new TaskBean("003","2","广州","20161025");
        taskBeanArrayList.add(taskBean3);
        TaskBean taskBean4 = new TaskBean("004","3","深圳","20161024");
        taskBeanArrayList.add(taskBean4);
        TaskBean taskBean5 = new TaskBean("005","4","浙江","20161023");
        taskBeanArrayList.add(taskBean5);
        TaskBean taskBean6 = new TaskBean("006","0","江苏","20161022");
        taskBeanArrayList.add(taskBean6);
        TaskBean taskBean7 = new TaskBean("007","1","成都","20161021");
        taskBeanArrayList.add(taskBean7);
        TaskBean taskBean8 = new TaskBean("008","2","安徽","20161020");
        taskBeanArrayList.add(taskBean8);

        TaskBean taskBean9 = new TaskBean("009","3","合肥","20161019");
        taskBeanArrayList.add(taskBean9);
        TaskBean taskBean10 = new TaskBean("010","4","湖南","20161018");
        taskBeanArrayList.add(taskBean10);
        TaskBean taskBean11 = new TaskBean("011","0","湖北","20161017");
        taskBeanArrayList.add(taskBean11);
        TaskBean taskBean12 = new TaskBean("012","1","巢湖","20161016");
        taskBeanArrayList.add(taskBean12);
        TaskBean taskBean13 = new TaskBean("013","2","武汉","20161015");
        taskBeanArrayList.add(taskBean13);
        TaskBean taskBean14 = new TaskBean("014","3","广东","20161014");
        taskBeanArrayList.add(taskBean14);
        TaskBean taskBean15 = new TaskBean("015","4","杭州","20161013");
        taskBeanArrayList.add(taskBean15);
        TaskBean taskBean16 = new TaskBean("016","0","苏州","20161012");
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

        appContext.startService(new Intent(appContext, MainService.class));

        while (true){}

    }
}
