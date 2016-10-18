package com.tmac.onsite.service;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;

import com.toolset.dataManager.dataManager;
import com.toolset.dataManager.dmdataDemo;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServiceTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "test";

    public void testService() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.startService(new Intent(appContext, AssistService.class));
    }
}
