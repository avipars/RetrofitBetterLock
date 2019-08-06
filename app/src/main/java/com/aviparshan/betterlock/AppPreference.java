package com.aviparshan.betterlock;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * RetrofitBetterLock
 * Created by Avi Parshan on 8/6/2019 on com.aviparshan.betterlock
 */
public class AppPreference {
    private static final String FILE_NAME = BuildConfig.APPLICATION_ID + ".apppreference";
    private static final String APP_GENERAL_SETTINGS = "app_general_settings";
    private final SharedPreferences preferences;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    }

    public SharedPreferences.Editor setGeneralSettings(Main appGeneralSettings) {
        return preferences.edit().putString(APP_GENERAL_SETTINGS, new Gson().toJson(appGeneralSettings));
    }

    public Main getGeneralSettings() {
        return new Gson().fromJson(preferences.getString(APP_GENERAL_SETTINGS, "{}"), Main.class);
    }
}