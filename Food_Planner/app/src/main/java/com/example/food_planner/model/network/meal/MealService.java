package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MealService {
    @GET("api/json/v1/1/search.php")
    Call<MealsResponse> searchMealByName(@Query("s") String name);
    @GET("api/json/v1/1/search.php")
    Call<MealsResponse> listMealsByFirstLetter(@Query("f") String letter);
    @GET("api/json/v1/1/lookup.php")
    Call<MealsResponse> lookupFullMealByID(@Query("i") String id);
    @GET("api/json/v1/1/random.php")
    Call<MealsResponse> lookupSingleRandomMeal();
    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);
}
