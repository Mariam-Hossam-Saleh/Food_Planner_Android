package com.example.food_planner.model.network.area;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.area.AreaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaRemoteDataSourceImp implements AreaRemoteDataSource{
    private static final String BASE_URL = "https://www.themealdb.com/";
    private static AreaRemoteDataSourceImp client = null;
    private final AreaService areaService;
    private AreaRemoteDataSourceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        areaService = retrofit.create(AreaService.class);
    }

    public static AreaRemoteDataSourceImp getInstance(){

        if(client == null){
            client = new AreaRemoteDataSourceImp();
        }
        return client;
    }
    @Override
    public void makeNetworkCalltoGetAllAreas(AreaNetworkCallback areaNetworkCallback) {
        areaService.getAllAreas().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                Log.i(TAG, "onResponse: " + response.raw());
                if (response.isSuccessful() && response.body() != null) {
                    List<Area> areas = response.body().getAreas();
                    if (areas != null) {
                        areaNetworkCallback.onSuccessArea(areas);
                    } else {
                        Log.e(TAG, "Areas list is null!");
                        areaNetworkCallback.onFailureArea("Areas not found");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful or body is null");
                    areaNetworkCallback.onFailureArea("Failed to fetch areas");
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                areaNetworkCallback.onFailureArea(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
