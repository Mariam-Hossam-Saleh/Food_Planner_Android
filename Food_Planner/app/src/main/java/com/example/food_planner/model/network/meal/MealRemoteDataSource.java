package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;

public interface MealRemoteDataSource {
    void makeNetworkCallForSearchMealByName(MealNetworkCallback mealNetworkCallback, String mealName);
    void makeNetworkCallForSingleRandomMeal(MealNetworkCallback mealNetworkCallback);
    void makeNetworkCallForMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter);
    void makeNetworkCallForTenRandomMeals(MealNetworkCallback mealNetworkCallback, ArrayList<Meal> meals);

}
