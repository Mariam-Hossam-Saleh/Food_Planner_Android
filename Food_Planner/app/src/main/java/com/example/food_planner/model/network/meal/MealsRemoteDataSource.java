package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.network.NetworkCallback;

public interface MealsRemoteDataSource {
    void makeNetworkCallForSingleRandomMeal(NetworkCallback networkCallback);
    void makeNetworkCallForMealsByFirstLetter(NetworkCallback networkCallback,String letter);

}
