package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;

public interface MealRemoteDataSource {
    void makeNetworkCallForSearchMealByName(MealNetworkCallback mealNetworkCallback, String mealName);
    void makeNetworkCallForSingleRandomMeal(MealNetworkCallback mealNetworkCallback,Boolean isSingle);
    void makeNetworkCallForMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter);
    void makeNetworkCallToFilterMealByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient);
    void makeNetworkCallToFilterMealByCategory(MealNetworkCallback mealNetworkCallback, String category);
    void makeNetworkCallToFilterMealByArea(MealNetworkCallback mealNetworkCallback, String area);
}
