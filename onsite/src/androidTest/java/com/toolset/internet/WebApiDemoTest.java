package com.toolset.internet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WebApiDemoTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "test";

    public void testWebApi() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ExpCommandE expCommandE = new ExpCommandE();
        expCommandE.AddAProperty( new Property("test" , "testObj"));


        InternetComponent webApiDemo = WebApiDemo.getInstance(appContext.getMainLooper());

        webApiDemo.registReq(expCommandE);
//        assertEquals(6, 2 + 2);
    }
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        ExpCommandE expCommandE = new ExpCommandE();
//        expCommandE.AddAProperty( new Property("test" , "testObj"));
//
//
//        WebApiDemo webApiDemo = WebApiDemo.getInstance(appContext.getMainLooper());
//
//        webApiDemo.registReq(expCommandE);
//
//
////        assertEquals("cn.bingoogolapple.badgeview.test", appContext.getPackageName());
//    }
}
