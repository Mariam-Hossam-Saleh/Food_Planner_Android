package com.example.food_planner.favourite.view;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface FavouriteView {
    public void ShowMeals(List<Meal> mealList);
    public void ShowErrMsg(String error);
}
