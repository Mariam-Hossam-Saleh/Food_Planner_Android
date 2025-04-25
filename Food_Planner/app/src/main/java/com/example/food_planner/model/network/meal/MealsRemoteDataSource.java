package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;

public interface MealsRemoteDataSource {
    void makeNetworkCallForSearchMealByName(NetworkCallback networkCallback, String mealName);
    void makeNetworkCallForSingleRandomMeal(NetworkCallback networkCallback);
    void makeNetworkCallForMealsByFirstLetter(NetworkCallback networkCallback,String letter);
    void makeNetworkCallForTenRandomMeals(NetworkCallback networkCallback, ArrayList<Meal> meals);

}
