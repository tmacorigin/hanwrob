package com.toolset.dataManager;

import android.content.Context;

import com.toolset.dataManager.db.DBHelper;
import com.toolset.dataManager.db.DBManager;

import java.util.ArrayList;

/**
 * Created by wanghp1 on 2016/10/7.
 */
public class dataManager {
    //              tag      dataSet
   // private Map< String, ArrayList<dataManagerdataBase> > dataMap =  new HashMap< String, ArrayList<dataManagerdataBase>>();
    DBManager db ;
    //增加一个class类型的额数据进入数据管理模块
    public void addA_Class( Class dataClass )
    {
        DBHelper getDbHelper = DBManager.GetDbHelper(dataClass);

        getDbHelper.closeDB();
    }

    public void  init( Context c )
    {
        db = new DBManager(c);
    }

    public void resetdbData(Class c ,ArrayList<dataManagerdataBase> dataList )
    {

		DBHelper getDbHelper = DBManager.GetDbHelper( c );

		getDbHelper.clearData();

		for( int i = 0 ; i < dataList.size(); i++  )
		{
            getDbHelper.add(dataList.get(i) );

		}
		getDbHelper.closeDB();

    }

    public ArrayList<Object> getAll( Class c )
    {
        DBHelper getDbHelper = DBManager.GetDbHelper( c );

        ArrayList<Object> miList = getDbHelper.searchAllData();


        return miList;
    }

    //将数据存入数据库
    void flushToDb()
    {

    }
}
