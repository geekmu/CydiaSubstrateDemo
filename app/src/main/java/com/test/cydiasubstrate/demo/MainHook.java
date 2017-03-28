package com.test.cydiasubstrate.demo;

import android.os.Bundle;
import android.util.Log;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/28.
 */

public class MainHook {
    static void initialize() {
        MS.hookClassLoad("android.telephony.TelephonyManager",
                new MS.ClassLoadHook() {

                    @Override
                    public void classLoaded(Class<?> resources) {
                        Method getDeviceId;
                        System.out.println("getDeviceId resources:" + resources.getName());
                        try {
                            getDeviceId = resources.getMethod("getDeviceId", new Class[]{});
                        } catch (NoSuchMethodException e) {
                            getDeviceId = null;
                        }

                        if (getDeviceId != null) {
                            final MS.MethodPointer old = new MS.MethodPointer();
                            MS.hookMethod(resources, getDeviceId, new MS.MethodHook() {
                                public Object invoked(Object resources, Object... args)
                                        throws Throwable
                                {
                                    String imei = (String) old.invoke(resources, args);
                                    imei = "9999999999999";
                                    return imei;
                                }
                            }, old);
                        }
                    }
                });
    }

}
