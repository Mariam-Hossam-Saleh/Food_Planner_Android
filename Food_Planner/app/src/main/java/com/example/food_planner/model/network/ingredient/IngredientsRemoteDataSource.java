package com.example.food_planner.model.network.ingredient;

import com.example.food_planner.model.network.meal.MealNetworkCallback;

public interface IngredientsRemoteDataSource {
    void makeNetworkCalltoGetAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback);
}
