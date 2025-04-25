package com.example.food_planner.model.network.meal;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.MealsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImp implements MealRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/";
    private static MealRemoteDataSourceImp client = null;
    private final MealService mealService;
    private MealRemoteDataSourceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealRemoteDataSourceImp getInstance(){

        if(client == null){
            client = new MealRemoteDataSourceImp();
        }
        return client;
    }

    @Override
    public void makeNetworkCallForSingleRandomMeal(MealNetworkCallback mealNetworkCallback){
        List<Meal> result = new ArrayList<>();
        mealService.lookupSingleRandomMeal().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getMeals());
                mealNetworkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                mealNetworkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }
    @Override
    public void makeNetworkCallForTenRandomMeals(MealNetworkCallback mealNetworkCallback, ArrayList<Meal> meals) {
        final int totalCalls = 10;
        final AtomicInteger completedCalls = new AtomicInteger(0); // to handle the asynchronous network calls

        for (int i = 0; i < totalCalls; i++) {
            mealService.lookupSingleRandomMeal().enqueue(new Callback<MealsResponse>() {
                @Override
                public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        synchronized (meals) {
                            meals.add(response.body().getMeals().get(0));
                        }
                    }

                    if (completedCalls.incrementAndGet() == totalCalls) {
                        mealNetworkCallback.onSuccessResult(meals);
                    }
                }

                @Override
                public void onFailure(Call<MealsResponse> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    if (completedCalls.incrementAndGet() == totalCalls) {
                        mealNetworkCallback.onFailureResult(t.getMessage());
                        t.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void makeNetworkCallForMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter) {
        List<Meal> result = new ArrayList<>();
        mealService.listMealsByFirstLetter(letter).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getMeals());
                mealNetworkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                mealNetworkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallForSearchMealByName(MealNetworkCallback mealNetworkCallback, String mealName) {
        List<Meal> result = new ArrayList<>();
        mealService.searchMealByName(mealName).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getMeals());
                mealNetworkCallback.onSuccessResult(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                mealNetworkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void getDataOverNetwork(MealNetworkCallback mealNetworkCallback){

    }
}
