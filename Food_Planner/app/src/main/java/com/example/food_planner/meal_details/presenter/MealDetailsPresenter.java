package com.example.food_planner.meal_details.presenter;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface MealDetailsPresenter {
    public void searchMealByID(String mealID);
    public void addMealToFavourite(FavoriteMeal meal);
    public void removeMealFromFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
}
