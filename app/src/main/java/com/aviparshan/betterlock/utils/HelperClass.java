package com.aviparshan.betterlock.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;

import com.aviparshan.betterlock.Main;
import com.aviparshan.betterlock.R;
import com.aviparshan.betterlock.datamodel.AppDataModel;

import java.io.InputStream;
import java.net.URL;

/**
 * RetrofitBetterLock
 * Created by Avi Parshan on 8/7/2019 on com.aviparshan.betterlock.utils
 */
public class HelperClass {

    public static void addShortcut (Context context, AppDataModel app)
    {
        String name = app.getTitle();
        String pac = app.getPackageName();
        String activity = app.getActivity();

        // Create an explict intent it will be used to call Our application by click on the short cut
        Intent shortcutIntent = new Intent(context, Main.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        // Create an implicit intent and assign Shortcut Application Name, Icon
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Tricing");
            /* If shortcut name is equal to the applicaion name then use
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
            */
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher_background));
        intent.putExtra("duplicate", false); //Avoid duplicate
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(intent);
    }

    public static void addShortcutToHomeScreen(Context context, AppDataModel app) {
        String name = app.getTitle();
        String pac = app.getPackageName();
        String activity = app.getActivity();

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context))
        {
            try {
                ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(context, "#1")
                        .setIntent(new Intent(context, Class.forName(activity)).setAction(Intent.ACTION_MAIN)) // !!! intent's action must be set on oreo
                        .setShortLabel("Test")
                        .setIcon(IconCompat.createWithResource(context, R.drawable.ic_android_black_24dp))
                        .build();

                ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            // Shortcut is not supported by your launcher
        }
    }

//    public static void CreateShort(Context context) {
//        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
//            final ShortcutManager shortcutManager = getSystemService(Main.class);
//
//            ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(context, shortcutId)
//                    .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
//                    .setShortLabel(label)
//                    .setIntent(new Intent(context, MainActivity.class).setAction(Intent.ACTION_MAIN))
//                    .build();
//            shortcutManager.requestPinShortcut(pinShortcutInfo, null);
//        }
//    }


    public static Drawable LoadImageFromWebURL(String url) {
        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(iStream, "src_name");
        } catch (Exception e) {
            return null;
        }}

//    @TargetApi(Build.VERSION_CODES.N_MR1)
//    public static void enableInProgressShortcut(@NonNull Context context, AppDataModel app) {
//
//        String name = app.getTitle();
//        String pac = app.getPackageName();
//        String activity = app.getActivity();
//        String picture = app.getIcon();
//
//
//        Drawable drb = LoadImageFromWebURL(picture);
//
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return;
//        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
//
//        Intent newTaskIntent = new Intent();
//        newTaskIntent.setComponent(new ComponentName(pac, activity));
//
//        newTaskIntent.setAction(Intent.ACTION_VIEW);
//        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        ShortcutInfo postShortcut
//                = new ShortcutInfo.Builder(context, "ID")
//                .setShortLabel("Test")
//                .setLongLabel("Test")
//                .setIcon(Icon.createWithResource(context, drb))
//                .setIntent(newTaskIntent)
//                .build();
//        if (shortcutManager != null) {
//            shortcutManager.addDynamicShortcuts(Collections.singletonList(postShortcut));
//        }
//    }
}
