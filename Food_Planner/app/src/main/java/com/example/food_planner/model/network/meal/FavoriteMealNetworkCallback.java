package com.example.food_planner.model.network.meal;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface FavoriteMealNetworkCallback {
    public void onSuccessFavoriteMeal(List<FavoriteMeal> meals);
    public void onFailureFavoriteResult(String errorMSG);
}
