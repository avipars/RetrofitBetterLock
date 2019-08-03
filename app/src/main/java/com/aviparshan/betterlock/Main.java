package com.aviparshan.betterlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Main extends AppCompatActivity {
    private ProgressBar loadProgress;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<AppDataModel> appDataModels = new ArrayList<AppDataModel>();
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(Main.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        APIInterface service= ServiceGenerator.getRetrofit().create(APIInterface.class);

        Call<List<AppDataModel>> call=service.getAppList();
        call.enqueue(new Callback<List<AppDataModel>>() {
            @Override
            public void onResponse(Call<List<AppDataModel>> call, Response<List<AppDataModel>> response) {
                loadProgress.setVisibility(View.GONE);
                appDataModels=response.body();
                recyclerAdapter=new RecyclerAdapter(Main.this,appDataModels);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<AppDataModel>> call, Throwable t) {
                Toast.makeText(Main.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Response", t.getMessage());
            }
        });
    }
}
