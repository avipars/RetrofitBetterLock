package com.aviparshan.betterlock;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aviparshan.betterlock.datamodel.AppDataModel;
import com.aviparshan.betterlock.utils.HelperClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements RecyclerAdapter.onItemClickListener,
        RecyclerAdapter.onItemLongClickListener {
    private ProgressBar loadProgress;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<AppDataModel> appDataModels = new ArrayList<AppDataModel>();
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private static Context mContext;

    public static String getVersion(String packageName) {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
            return "0";
        }
    }

    /**
     * <p>Intent to show an applications details page in (Settings) com.android.settings</p>
     *
     * @param model The package name of the application
     * @return the intent to open the application info screen.
     */
    public static void appInfo(AppDataModel model) {
        String packageName = model.getPackageName();
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent);
    }

    /**
     * Run when user clicks uninstall app if he has a version from put of play store or is using an emulator
     */
    public static void uninstallApp(AppDataModel model) {
        String packageName = model.getPackageName();
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent);
    }

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isPackageInstalled(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;

        } catch (PackageManager.NameNotFoundException nef) {
            return false;
        }

    }

    public static String packageInstalled(Context context, AppDataModel app) {
        String pName = app.getPackageName();
        if (appInstalledOrNot(context, pName)) {
            app.setInstalled(true);
            return "Installed";
        } else {
            app.setInstalled(false);
            return "Not Installed";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void pinShortcut(AppDataModel app) {
        String pName = app.getPackageName();
        if (appInstalledOrNot(mContext, pName)) {
            ShortcutManager shortcutManager = mContext.getSystemService(ShortcutManager.class);
            //Create a ShortcutInfo object that defines all the shortcut’s characteristics

            Intent appIntent = new Intent();
            appIntent.setComponent(new ComponentName(app.getPackageName(), app.getActivity()));

//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);
//                    .setIcon(Icon.createWithResource(mContext,app.getPhoto()))
//            Bitmap bit;
//            if (app.getPhoto() == null) {
//                bit = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.mipmap.ic_launcher);
//            } else {
//                bit = app.getPhoto();
//            }
            ShortcutInfo shortcut;
            if (app.getPhoto() != null) {
                shortcut = new ShortcutInfo.Builder(mContext, app.getPackageName())
                        .setShortLabel(app.getTitle())
                        .setLongLabel(app.getDescription())
                        .setIcon(Icon.createWithBitmap(app.getPhoto()))
                        .setIntent(launchIntent(app))
                        .build();
            } else {
                shortcut = new ShortcutInfo.Builder(mContext, app.getPackageName())
                        .setShortLabel(app.getTitle())
                        .setLongLabel(app.getDescription())
                        .setIcon(Icon.createWithResource(mContext, R.mipmap.ic_launcher))
                        .setIntent(launchIntent(app))
                        .build();
            }
            assert shortcutManager != null;
            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
            //Check that the device's default launcher supports pinned shortcuts//
            if (shortcutManager.isRequestPinShortcutSupported()) {
                ShortcutInfo pinShortcutInfo = new ShortcutInfo
                        .Builder(mContext, app.getPackageName())
                        .build();
                Intent pinnedShortcutCallbackIntent =
                        shortcutManager.createShortcutResultIntent(pinShortcutInfo);
                //Get notified when a shortcut is pinned successfully
                PendingIntent successCallback = PendingIntent.getBroadcast(mContext, 0,
                        pinnedShortcutCallbackIntent, 0);
                shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
            }
        }
    }

    public static String versionChecker(AppDataModel app) {

        if (HelperClass.CompareVersions(app.getVersion(), getVersion(app.getPackageName())) == 0) {
            return "Updated";
        } else if (HelperClass.CompareVersions(app.getVersion(), getVersion(app.getPackageName())) == -1) {
            return "Not Updated";
        } else {
            return "Newer Version";
        }
    }

    public static int versionCheckerColor(AppDataModel app) {


        if (HelperClass.CompareVersions(app.getVersion(), getVersion(app.getPackageName())) == 0) {
            return ContextCompat.getColor(mContext, R.color.colorAccent);

        } else if (HelperClass.CompareVersions(app.getVersion(), getVersion(app.getPackageName())) == -1) {
            return ContextCompat.getColor(mContext, R.color.colorOrange);
        } else {
            return ContextCompat.getColor(mContext, R.color.colorAccent);
        }
    }

    private static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
//           Log.e("BetterLock", "Package not installed: " + e);
        }
        return false;
    }

    static Intent launchIntent(AppDataModel app) {

        Intent i = new Intent(mContext.getApplicationContext(), Main.class);
        i.setAction(Intent.ACTION_VIEW);
        if (!app.getActivity().isEmpty() && !app.getPackageName().isEmpty()) {
            try {
                Intent appIntent = new Intent();
                appIntent.setAction(Intent.ACTION_VIEW);
//                appIntent.setAction(Intent.ACTION_MAIN);
                appIntent.setComponent(new ComponentName(app.getPackageName(), app.getActivity()));
                return appIntent;
            } catch (Exception e) {
                Toast.makeText(mContext, e.toString() + " " + app.getPackageName(), Toast.LENGTH_SHORT).show();
                Log.e("Error Loading", "Error: " + e.toString() + app.getPackageName());
                return i;
            }
        } else {
            return i;
        }
    }

    void openActivity(int number) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(launchIntent);
    }


    @Override
    public void itemDetailClick(AppDataModel conversion) {
        launchActivity(conversion);
    }

    @Override
    public void itemDetailLongClick(AppDataModel conversion) {
//        uninstallApp(conversion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_tool);

        AppBarLayout mAppBarLayout = findViewById(R.id.appbar);
        loadProgress = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerview);

        CollapsingToolbarLayout ctl = findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = findViewById(R.id.z_toolbar);
        setSupportActionBar(myToolbar);


        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        ctl.setTitle(getString(R.string.app_name));

        ctl.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
        ctl.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);

//        myToolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //works perfectly

        mContext = this; //static intents
//        int mNoOfColumns = 1;
//        gridLayoutManager = new GridLayoutManager(Main.this, mNoOfColumns);
//        recyclerView.setLayoutManager(gridLayoutManager);

//        recyclerView.setAdapter(recyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(Main.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true); //performance helper
        recyclerView.setClipToPadding(false); //for Android Q Navigation
        recyclerView.setMotionEventSplittingEnabled(false); //fixes a bug where if user clicks multiple things at the same time, the app would crash
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);

        APIInterface service = ServiceGenerator.getRetrofit().create(APIInterface.class);

        Call<List<AppDataModel>> call = service.getAppList();
        call.enqueue(new Callback<List<AppDataModel>>() {
            @Override
            public void onResponse(Call<List<AppDataModel>> call, Response<List<AppDataModel>> response) {
                loadProgress.setVisibility(View.GONE);
                appDataModels = response.body();
                recyclerAdapter = new RecyclerAdapter(Main.this, appDataModels, Main.this, Main.this);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<AppDataModel>> call, Throwable t) {
                Toast.makeText(Main.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Response", t.getMessage());
            }
        });

    }

    void launchActivity(AppDataModel app) {
        if (!app.getActivity().isEmpty() && !app.getPackageName().isEmpty()) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(app.getPackageName(), app.getActivity()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, e.toString() + " " + app.getPackageName(), Toast.LENGTH_SHORT).show();
                Log.e("Error Loading", "Error: " + e.toString() + app.getPackageName());
                app.setVersion("Not installed");
                recyclerAdapter.notifyDataSetChanged();
//                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
//                startActivity(launchIntent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static void createToast(String message, int duration) {
        Toast.makeText(mContext, message, duration).show();
    }
}
