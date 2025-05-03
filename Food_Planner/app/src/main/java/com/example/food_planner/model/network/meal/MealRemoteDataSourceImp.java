package com.example.food_planner.model.network.meal;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.pojos.meal.MealsResponse;

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
    public void makeNetworkCallForSingleRandomMeal(MealNetworkCallback mealNetworkCallback, Boolean isSingle){
        mealService.lookupSingleRandomMeal().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
    public void makeNetworkCallForMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter) {
        mealService.listMealsByFirstLetter(letter).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
    public void makeNetworkCallToFilterMealByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient) {
        mealService.getMealsByIngredient(ingredient).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
    public void makeNetworkCallToFilterMealByCategory(MealNetworkCallback mealNetworkCallback, String category) {
        mealService.getMealsByCategory(category).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
    public void makeNetworkCallToFilterMealByArea(MealNetworkCallback mealNetworkCallback, String area) {
        mealService.getMealsByArea(area).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
        mealService.searchMealByName(mealName).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
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
    public void makeNetworkCallToFilterMealByID(MealNetworkCallback mealNetworkCallback, String mealID) {
        mealService.searchMealByID(mealID).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                mealNetworkCallback.onSuccessMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                mealNetworkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
