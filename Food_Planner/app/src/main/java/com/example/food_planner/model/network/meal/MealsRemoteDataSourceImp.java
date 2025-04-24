package com.example.food_planner.model.network.meal;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.MealsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImp implements MealsRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/";
    private static MealsRemoteDataSourceImp client = null;
    private final MealService mealService;
    private MealsRemoteDataSourceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealsRemoteDataSourceImp getInstance(){

        if(client == null){
            client = new MealsRemoteDataSourceImp();
        }
        return client;
    }

    @Override
    public void makeNetworkCallForSingleRandomMeal(NetworkCallback networkCallback){
        List<Meal> result = new ArrayList<>();
        mealService.lookupSingleRandomMeal().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getMeals());
                networkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallForMealsByFirstLetter(NetworkCallback networkCallback, String letter) {
        List<Meal> result = new ArrayList<>();
        mealService.listMealsByFirstLetter(letter).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getMeals());
                networkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void getDataOverNetwork(NetworkCallback networkCallback){

    }
}
