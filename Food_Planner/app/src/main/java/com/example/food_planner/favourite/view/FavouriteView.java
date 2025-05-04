package com.example.food_planner.favourite.view;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface FavouriteView {
    public void ShowFavoriteMeals(List<FavoriteMeal> mealList);
    public void ShowErrMsg(String error);
}
