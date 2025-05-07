package com.example.food_planner.meals.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface MealsPresenter {

    public void filterByMainIngredient(String ingredient);
    public void filterMealByCategory(String category);
    public void filterByArea(String area);
    public void addMealToFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
    public void removeMealFromFavourite(FavoriteMeal meal);
    public LiveData<List<FavoriteMeal>> getFavouriteMeals();
    public void removeMealFromCalendar(PlannedMeal meal);
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal);
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal);

}
