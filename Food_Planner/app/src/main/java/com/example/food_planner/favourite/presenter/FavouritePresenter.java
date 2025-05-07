package com.example.food_planner.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface FavouritePresenter {
    public LiveData<List<FavoriteMeal>> getFavouriteMeals();
    public void removeMealFromFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal);
}
