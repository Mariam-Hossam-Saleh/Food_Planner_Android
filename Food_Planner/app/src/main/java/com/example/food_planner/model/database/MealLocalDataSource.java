package com.example.food_planner.model.database;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojo.Meal;

import java.util.List;

public interface MealLocalDataSource {
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    LiveData<List<Meal>> getAllStoredMeals();
}
