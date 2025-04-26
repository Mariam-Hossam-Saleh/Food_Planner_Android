package com.example.food_planner.model.network.ingredient;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface IngredientsNetworkCallback {
    public void onSuccessIngredient(List<Ingredient> meals);
    public void onFailureIngredient(String errorMSG);
}
