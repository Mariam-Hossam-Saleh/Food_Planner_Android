package com.example.food_planner.model.network;

import com.example.food_planner.model.pojo.MealsResponce;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("api/json/v1/1/random.php")
    Call<MealsResponce> getMeals();

}
