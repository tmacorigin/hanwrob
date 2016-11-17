package com.toolset.MainControl;

import android.content.Context;

import com.tmac.onsite.bean.TaskBean;
import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dataManagerdataBase;
import com.toolset.state.dataBean.TelNumInfo;

import java.util.ArrayList;

/**
 * Created by pactera on 2016/10/27.
 */
public class TestControl {

    public static boolean isTest = false;
    public static final int INTERNET_SOURCE = 1;
    public static final int DB_SOURCE = 2;
    public static final int TEST_SOURCE = 3;
    public static final int DATA_SOURCE = DB_SOURCE;


    public static void saveDataToDB(Context mContext){
        ArrayList<dataManagerdataBase> taskBeanArrayList = new ArrayList<dataManagerdataBase>();
        TaskBean taskBean1 = new TaskBean("h001","0","上海","2016-11-26","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean1);
        TaskBean taskBean2 = new TaskBean("h002","1","北京","2016-11-07","0","0","2016-10-19");
        taskBeanArrayList.add(taskBean2);
        TaskBean taskBean3 = new TaskBean("h003","2","广州","2016-11-25","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean3);
        TaskBean taskBean4 = new TaskBean("h004","3","深圳","2016-10-24","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean4);
        TaskBean taskBean5 = new TaskBean("h005","4","浙江","2016-10-23","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean5);
        TaskBean taskBean6 = new TaskBean("h006","0","江苏","2016-10-22","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean6);
        TaskBean taskBean7 = new TaskBean("h007","1","成都","2016-11-12","0","0","2016-10-19");
        taskBeanArrayList.add(taskBean7);
        TaskBean taskBean8 = new TaskBean("h008","2","安徽","2016-10-20","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean8);

        TaskBean taskBean9 = new TaskBean("h009","3","合肥","2016-10-19","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean9);
        TaskBean taskBean10 = new TaskBean("h010","4","湖南","2016-10-18","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean10);
        TaskBean taskBean11 = new TaskBean("h011","0","湖北","2016-10-17","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean11);
        TaskBean taskBean12 = new TaskBean("h012","1","巢湖","2016-11-10","0","0","2016-10-19");
        taskBeanArrayList.add(taskBean12);
        TaskBean taskBean13 = new TaskBean("h013","2","武汉","2016-10-15","1","1","2016-10-19");
        taskBeanArrayList.add(taskBean13);
        TaskBean taskBean14 = new TaskBean("h014","3","广东","2016-10-14","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean14);
        TaskBean taskBean15 = new TaskBean("h015","4","杭州","2016-10-13","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean15);
        TaskBean taskBean16 = new TaskBean("h016","0","苏州","2016-10-12","1","0","2016-10-19");
        taskBeanArrayList.add(taskBean16);

        dataManager dm = dataManager.getInstance(mContext);
        dm.addA_Class(TaskBean.class);
        dm.resetdbData(TaskBean.class, taskBeanArrayList);
    }
}
