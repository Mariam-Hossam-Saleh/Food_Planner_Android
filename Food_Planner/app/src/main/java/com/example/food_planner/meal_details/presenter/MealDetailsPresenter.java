package com.example.food_planner.meal_details.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface MealDetailsPresenter {
    public void searchMealByID(String mealID);
    public void addMealToFavourite(FavoriteMeal meal);
    public void removeMealFromFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal);
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal);
}
