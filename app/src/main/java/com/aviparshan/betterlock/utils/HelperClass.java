package com.aviparshan.betterlock.utils;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * RetrofitBetterLock
 * Created by Avi Parshan on 8/7/2019 on com.aviparshan.betterlock.utils
 */
public class HelperClass extends Application {

    static Context mContext;
    public HelperClass (Context context)
    {
        mContext = context;
    }

    public HelperClass() {
    }

    public static int CompareVersions(String version1, String version2) {
        String[] string1Vals = version1.split("\\.");
        String[] string2Vals = version2.split("\\.");

        int length = Math.max(string1Vals.length, string2Vals.length);

        for (int i = 0; i < length; i++) {
            Integer v1 = (i < string1Vals.length) ? Integer.parseInt(string1Vals[i]) : 0;
            Integer v2 = (i < string2Vals.length) ? Integer.parseInt(string2Vals[i]) : 0;

            //Making sure Version1 bigger than version2
            if (v1 > v2) {
                return 1;
            }
            //Making sure Version1 smaller than version2
            else if (v1 < v2) {
                return -1;
            }
        }

        //Both are equal
        return 0;
    }

    public boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

}
