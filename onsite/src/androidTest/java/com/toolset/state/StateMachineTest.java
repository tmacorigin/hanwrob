package com.toolset.state;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;

import com.toolset.CommandParser.ExpCommandE;
import com.toolset.CommandParser.Property;
import com.toolset.Network.NetworkReceiver;
import com.toolset.internet.InternetComponent;
import com.toolset.internet.WebApiDemo;

import de.greenrobot.event.EventBus;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StateMachineTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "test";

    public void testWebApi() {
//        EventBus.getDefault().register(this);
        Context appContext = InstrumentationRegistry.getTargetContext();
        stateMachine sm = new stateMachine( appContext );
        sm.setState(stateMachine.STATE_NORMAL);
//        ExpCommandE e = new ExpCommandE("STATE_CONTROL_COMMAND");
//        e.AddAExpProperty(new Property("internalMessageName", "netConnect"));
//        e.AddAProperty(new Property("netState", "disconnect"));
//        EventBus.getDefault().post(e);
    }
}
