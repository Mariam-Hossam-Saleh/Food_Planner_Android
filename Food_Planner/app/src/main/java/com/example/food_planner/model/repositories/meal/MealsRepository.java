package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface MealsRepository {
    public LiveData<List<Meal>> getStoredMeals();
    public void searchMealByName(MealNetworkCallback mealNetworkCallback, String mealName);
    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback,Boolean isSingle);
    public void getMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter);
    public void filterByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient);
    public void filterByCategory(MealNetworkCallback mealNetworkCallback, String category);
    public void filterByArea(MealNetworkCallback mealNetworkCallback, String area);
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
}
