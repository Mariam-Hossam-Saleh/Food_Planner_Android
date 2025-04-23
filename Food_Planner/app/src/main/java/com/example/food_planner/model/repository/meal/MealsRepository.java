package com.example.food_planner.model.repository.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface MealsRepository {
    public LiveData<List<Meal>> getStoredMeals();
    public void getAllMeals(NetworkCallback networkCallback);
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
}
