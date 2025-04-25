package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface MealNetworkCallback {
    public void onSuccessResult(List<Meal> meals);
    public void onFailureResult(String errorMSG);
}
