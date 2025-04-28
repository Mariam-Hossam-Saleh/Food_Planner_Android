package com.example.food_planner.model.network.category;

import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.meal.MealNetworkCallback;

public interface CategoryRemoteDataSource {
    void makeNetworkCalltoGetAllCategories(CategoryNetworkCallback categoryNetworkCallback);
}
