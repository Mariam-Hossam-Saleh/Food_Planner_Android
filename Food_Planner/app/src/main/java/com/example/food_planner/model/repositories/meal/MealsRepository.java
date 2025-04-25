package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface MealsRepository {
    public LiveData<List<Meal>> getStoredMeals();
    public void searchMealByName(NetworkCallback networkCallback, String mealName);
    public void getSingleRandomMeal(NetworkCallback networkCallback);
    public void getTenRandomMeal(NetworkCallback networkCallback, ArrayList<Meal> meals);
    public void getMealsByFirstLetter(NetworkCallback networkCallback, String letter);
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
}
