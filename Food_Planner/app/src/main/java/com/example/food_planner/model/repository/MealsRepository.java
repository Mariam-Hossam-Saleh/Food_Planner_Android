package com.example.food_planner.model.repository;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojo.Meal;

import java.util.List;

public interface MealsRepository {
    public LiveData<List<Meal>> getStoredMeals();
    public void getAllMeals(NetworkCallback networkCallback);
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
}
