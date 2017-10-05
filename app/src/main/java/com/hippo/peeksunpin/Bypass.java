/* Copyright (c) 2017 Hippothon
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package com.hippo.peeksunpin;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Bypass implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.keek"))
            return;
        Log.d("PeeksUnpin", "Loaded inside peeks");

        Class<?> targetClass = XposedHelpers.findClass("com.peeks.common.b.b", lpparam.classLoader);
        Method[] pinningMethods = XposedHelpers.findMethodsByExactParameters(targetClass, boolean.class, HttpsURLConnection.class, Set.class);
        Log.d("PeeksUnpin", "Found " + pinningMethods.length + " possible methods");

        if (pinningMethods.length == 1) {
            Method pinningMethod = pinningMethods[0];
            Log.d("PeeksUnpin", "Using method: " + pinningMethod.getName());

            XposedBridge.hookMethod(pinningMethod, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return true;
                }
            });
        }
    }
}
