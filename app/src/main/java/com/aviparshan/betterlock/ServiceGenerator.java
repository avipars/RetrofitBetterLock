package com.aviparshan.betterlock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit retrofit;
    private static Gson gson;
    static OkHttpClient okHttpClient;

    public static Boolean hasNetwork(Context context) {
        Boolean isConnected = false; // Initial Value
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            isConnected = true;
        }
        return isConnected;
    }

    public static Retrofit getCacheEnabledRetrofit(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1024))
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (hasNetwork(context))
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        else
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(CONSTANTS.BASE_URL)
                .build();

        return retrofit;
    }


    public static Retrofit getRetrofit() {
        gson=new GsonBuilder()
                .create();

        if (retrofit==null){


            retrofit=new Retrofit.Builder()
                        .baseUrl(CONSTANTS.BASE_URL)
//                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }

}
