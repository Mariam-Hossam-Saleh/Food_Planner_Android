package com.example.food_planner.model.network.category;

import com.example.food_planner.model.network.meal.MealNetworkCallback;

public interface CategoryRemoteDataSource {
    void makeNetworkCall(MealNetworkCallback mealNetworkCallback);
}
