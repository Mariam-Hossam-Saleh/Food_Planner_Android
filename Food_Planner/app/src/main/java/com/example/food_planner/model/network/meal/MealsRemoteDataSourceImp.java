package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

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
    public void makeNetworkCall(NetworkCallback networkCallback){
        List<Meal> result = new ArrayList<>();
//        mealService.searchMealByName().enqueue(new Callback<MealsResponce>() {
//            @Override
//            public void onResponse(@NonNull Call<MealsResponce> call, @NonNull Response<MealsResponce> response) {
//                if(response.isSuccessful()&& response.body()!=null){
//                    Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
//                    result.addAll(response.body().getMeals());
//                    networkCallback.onSuccessResult(response.body().getMeals());
//                }
//            }

//            @Override
//            public void onFailure(@NonNull Call<MealsResponce> call, @NonNull Throwable t) {
//                Log.i(TAG,"onFailure: CallBack");
//                networkCallback.onFailureResult(t.getMessage());
//                t.printStackTrace();
//
//            }
//        });
    }
    public void getDataOverNetwork(NetworkCallback networkCallback){

    }
}
