package com.example.food_planner.model.database.mealsdatabase;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface MealLocalDataSource {
    void insertFavoriteMeal(FavoriteMeal meal);
    void deleteFavoriteMeal(FavoriteMeal meal);
    LiveData<List<FavoriteMeal>> getStoredFavoriteMeals();
    void insertPlannedMeal(PlannedMeal meal);
    void deletePlannedMeal(PlannedMeal meal);
    LiveData<List<PlannedMeal>> getStoredPlannedMeals();
}
