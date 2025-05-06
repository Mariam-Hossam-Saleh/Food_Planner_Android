package com.example.food_planner.meals.presenter;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface MealsPresenter {

    public void filterByMainIngredient(String ingredient);
    public void filterMealByCategory(String category);
    public void filterByArea(String area);
    public void addMealToFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
    public void removeMealFromFavourite(FavoriteMeal meal);
    public void removeMealFromCalendar(PlannedMeal meal);

}
