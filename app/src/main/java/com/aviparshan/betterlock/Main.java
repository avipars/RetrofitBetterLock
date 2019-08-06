package com.aviparshan.betterlock;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aviparshan.betterlock.datamodel.AppDataModel;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

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

    private boolean isPackageInstalled(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException nef) {
            Toast.makeText(this, "Name not found", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void itemDetailClick(AppDataModel conversion) {
        launchActivity(conversion);
    }

    @Override
    public void itemDetailLongClick(AppDataModel conversion) {
        launchActivity(conversion);
    }

//    TODO: fix edgelighting
    void launchActivity(AppDataModel app) {
        if (!app.getActivity().isEmpty()) {
        Toast.makeText(this, app.getActivity() + app.getTitle(), Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(app.getPackageName(), app.getActivity()));
                startActivity(intent);
            } catch (Exception e) {

                Toast.makeText(this, e.toString() + " " + app.getPackageName(), Toast.LENGTH_SHORT).show();
                Log.e("Error Loading", "Error: " + e.toString() + app.getPackageName());
                app.setVersion("Not installed");
                recyclerAdapter.notifyDataSetChanged();

//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.samsung.android.edgelightingeffectunit", "com.samsung.android.edgelightingeffectunit.activity.EdgeLightingUnitActivity"));
//                startActivity(intent);

                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                startActivity(launchIntent);

            }
        }
        else {
            Toast.makeText(this, app.getActivity() + app.getTitle(), Toast.LENGTH_SHORT).show();

            try {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                Intent launchIntent2 = getPackageManager().getLaunchIntentForPackage("com.samsung.android.app.cocktailbarservice");

                startActivity(launchIntent);
            } catch(Exception e)
            {
                Toast.makeText(this, e.toString() + " " + app.getPackageName(), Toast.LENGTH_SHORT).show();
                Log.e("Error Loading, Empty", "Error: " + e.toString() + app.getPackageName());

                app.setVersion("Not installed");
                recyclerAdapter.notifyDataSetChanged();
            }
        }
    }
}
