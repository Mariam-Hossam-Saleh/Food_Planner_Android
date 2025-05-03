package com.example.food_planner.meal_details.presenter;

import com.example.food_planner.model.pojos.meal.Meal;

public interface MealDetailsPresenter {
    public void searchMealByID(String mealID);
    public void addMealToFavourite(Meal meal);
    public void removeMealFromFavourite(Meal meal);
}
