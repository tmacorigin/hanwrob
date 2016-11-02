package com.toolset.dataManager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.internet.InternetComponent;
import com.toolset.internet.WebApiDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class dmDemoTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "test";

    public void testWebApi() {
        ArrayList<dataManagerdataBase> dmdataList = new ArrayList<dataManagerdataBase>();
        dmdataDemo dmdataDemo1 = new dmdataDemo("h", "1", "1");
        dmdataList.add(dmdataDemo1);
        dmdataDemo dmdataDemo2 = new dmdataDemo("w", "1", "1");
        dmdataList.add(dmdataDemo2);
        dmdataDemo dmdataDemo3 = new dmdataDemo("q", "1", "1");
        dmdataList.add(dmdataDemo3);
        dmdataDemo dmdataDemo4 = new dmdataDemo("s", "1", "1");
        dmdataList.add(dmdataDemo4);
        dmdataDemo dmdataDemo5 = new dmdataDemo("y", "1", "1");
        dmdataList.add(dmdataDemo5);


        Context appContext = InstrumentationRegistry.getTargetContext();
        dataManager dm = dataManager.getInstance(appContext );
        dm.addA_Class( dmdataDemo.class );
        dm.resetdbData(dmdataDemo.class, dmdataList);
        dataManager dm1 = dataManager.getInstance(appContext );
        dm1.addA_Class( dmdataDemo.class );
        ArrayList<Object> getdataList = dm1.getAll(dmdataDemo.class);
        assertEquals(dmdataList.size(),getdataList.size());
        for (int i = 0; i < getdataList.size(); i ++){
            dmdataDemo tempDmDataDemo = (dmdataDemo) getdataList.get(i);
            dmdataDemo tempDmDataDemo1 = (dmdataDemo) dmdataList.get(i);
            assertEquals(tempDmDataDemo1.name,tempDmDataDemo.name);
            assertEquals(tempDmDataDemo1.data1,tempDmDataDemo.data1);
            assertEquals(tempDmDataDemo1.data2,tempDmDataDemo.data2);
        }
    }
}
