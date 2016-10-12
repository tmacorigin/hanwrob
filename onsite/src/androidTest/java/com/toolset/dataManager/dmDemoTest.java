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
        ArrayList<dmdataDemo> dmdataList = new ArrayList<dmdataDemo>();
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
        dataManager dm = new dataManager(appContext );
        dm.addA_Class( dmdataDemo.class );

        dm.resetdbData(dmdataDemo.class, dmdataList);
        ArrayList<Object> getdataList = dm.getAll(dmdataDemo.class);
        assertEquals(dmdataList.size(),getdataList.size());
        for (int i = 0; i < getdataList.size(); i ++){
            dmdataDemo tempDmDataDemo = (dmdataDemo) getdataList.get(i);
            assertEquals(dmdataList.get(i).name,tempDmDataDemo.name);
            assertEquals(dmdataList.get(i).data1,tempDmDataDemo.data1);
            assertEquals(dmdataList.get(i).data2,tempDmDataDemo.data2);
        }
    }
}
