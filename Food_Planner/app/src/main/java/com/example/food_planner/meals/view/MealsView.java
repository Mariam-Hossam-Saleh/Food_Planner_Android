package com.example.food_planner.meals.view;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface MealsView {
    public void ShowMeals(List<Meal> mealList);
    public void ShowErrMsg(String error);
}
