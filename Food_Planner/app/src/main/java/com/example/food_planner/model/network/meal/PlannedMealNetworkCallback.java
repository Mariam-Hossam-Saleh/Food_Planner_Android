package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface PlannedMealNetworkCallback {
    public void onSuccessPlannedMeal(List<PlannedMeal> meals);
    public void onFailurePlannedResult(String errorMSG);
}
