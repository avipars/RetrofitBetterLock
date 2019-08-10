package com.aviparshan.betterlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aviparshan.betterlock.datamodel.AppDataModel;

import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_tool);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        final Toolbar tool = findViewById(R.id.toolbar);
        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        setSupportActionBar(tool);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        tool.setTitleTextColor(Color.parseColor("#FFFFFF")); //works perfectly

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

    void openActivity(int number) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(launchIntent);
    }

//    public boolean isPackageExisted(String targetPackage){
//        List packages;
//        PackageManager pm;
//
//        pm = getPackageManager();
//        packages = pm.getInstalledApplications(0);
//        for (ApplicationInfo packageInfo : packages) {
//            if(packageInfo.packageName.equals(targetPackage))
//                return true;
//        }
//        return false;
//    }

    public static boolean isPackageInstalled(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException nef) {
            Toast.makeText(mContext, "Name not found", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    public static String packageInstalled(AppDataModel app)
    {
        String pName = app.getPackageName();
        if(appInstalledOrNot(mContext, pName))
        {
            app.setInstalled(true);
            return "Installed";
        }
        else {
            app.setInstalled(false);
            return "Not Installed";
        }
    }

    public static String versionChecker(AppDataModel app)
    {
        if(app.getInstalled()) {
            if(CompareVersions(app.getVersion(),getVersion(app.getPackageName())) == 0)
            {
                return "Updated";
            }
            else if(CompareVersions(app.getVersion(),getVersion(app.getPackageName())) == -1)
            {
                return "Not Updated";
            }
            else
            {
                return "Updated";
            }
        }
        else
        {
            return "Not Installed";
        }
    }

    public static int CompareVersions(String version1, String version2)
    {
        String[] string1Vals = version1.split("\\.");
        String[] string2Vals = version2.split("\\.");

        int length = Math.max(string1Vals.length, string2Vals.length);

        for (int i = 0; i < length; i++)
        {
            Integer v1 = (i < string1Vals.length)?Integer.parseInt(string1Vals[i]):0;
            Integer v2 = (i < string2Vals.length)?Integer.parseInt(string2Vals[i]):0;

            //Making sure Version1 bigger than version2
            if (v1 > v2)
            {
                return 1;
            }
            //Making sure Version1 smaller than version2
            else if(v1 < v2)
            {
                return -1;
            }
        }

        //Both are equal
        return 0;
    }

    public static String getVersion(String packageName)
    {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
            return "0";
        }
    }

    public static boolean isPackageInstalled(Context context ,String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BetterLock", "Package not installed: " +e);
        }
        return false;
    }

    @Override
    public void itemDetailClick(AppDataModel conversion) {
        launchActivity(conversion);
    }

    @Override
    public void itemDetailLongClick(AppDataModel conversion) {
//        uninstallApp(conversion);
    }


    /**
     * <p>Intent to show an applications details page in (Settings) com.android.settings</p>
     *
     * @param model   The package name of the application
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
    public static void deleteApp(AppDataModel model) {
        String packageName = model.getPackageName();
        Intent intent = new Intent(Intent.ACTION_DELETE);
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


    /**
     * <p>Intent to show an applications details page in (Settings) com.android.settings</p>
     *
     * @param packageName   The package name of the application
     * @return the intent to open the application info screen.
     */
    public static void appInfo(String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent);
    }

    /**
    * Run when user clicks uninstall app if he has a version from put of play store or is using an emulator
    */
     public static void deleteApp(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
         mContext.startActivity(intent);
    }

    void launchActivity(AppDataModel app) {
         if(app.getInstalled()) {
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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.info:
                appInfo(this.getPackageName());
                return true;
            case R.id.unin:
                deleteApp(this.getPackageName());
                return true;
            default:
                return super.onContextItemSelected(item);
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

}
