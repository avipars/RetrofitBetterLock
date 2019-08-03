package com.aviparshan.betterlock;

import com.aviparshan.betterlock.datamodel.AppDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
//    @GET("/shows/1/cast")
//    Call<List<TVMazeDataModel>> getHeroList();

//    @GET("/shows.json")
//    Call<List<TVMazeDataModel>> getHeroList();

    @GET("betterlock.json")
    Call<List<AppDataModel>> getAppList();
}
