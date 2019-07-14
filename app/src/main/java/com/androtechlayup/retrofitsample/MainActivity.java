package com.androtechlayup.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androtechlayup.retrofitsample.datamodel.TVMazeDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private ProgressBar loadProgress;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<TVMazeDataModel> tvMazeDataModels;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        APIInterface service= ServiceGenerator.getRetrofit().create(APIInterface.class);
        Call<List<TVMazeDataModel>> call=service.getHeroList();
        call.enqueue(new Callback<List<TVMazeDataModel>>() {
            @Override
            public void onResponse(Call<List<TVMazeDataModel>> call, Response<List<TVMazeDataModel>> response) {
                loadProgress.setVisibility(View.GONE);
                tvMazeDataModels=response.body();
                recyclerAdapter=new RecyclerAdapter(MainActivity.this,tvMazeDataModels);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<TVMazeDataModel>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });

    }
}
