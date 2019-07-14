package com.androtechlayup.retrofitsample;

import com.androtechlayup.retrofitsample.datamodel.TVMazeDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/shows/1/cast")
    Call<List<TVMazeDataModel>> getHeroList();
}
