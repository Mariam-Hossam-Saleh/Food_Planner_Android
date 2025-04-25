package com.example.food_planner.model.network.ingredient;

import com.example.food_planner.model.pojos.ingredient.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientsService {
    @GET("api/json/v1/1/list.php?i=list")
    Call<IngredientResponse> getAllIngredients();
}
