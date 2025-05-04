package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface MealsRepository {
    public LiveData<List<FavoriteMeal>> getStoredFavoriteMeals();
    public void searchMealByName(MealNetworkCallback mealNetworkCallback, String mealName);
    public void searchMealByID(MealNetworkCallback mealNetworkCallback, String mealID);
    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback,Boolean isSingle);
    public void getMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter);
    public void filterByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient);
    public void filterByCategory(MealNetworkCallback mealNetworkCallback, String category);
    public void filterByArea(MealNetworkCallback mealNetworkCallback, String area);
    public void insertFavoriteMeal(FavoriteMeal meal);
    public void deleteFavoriteMeal(FavoriteMeal meal);
    public void insertPlannedMeal(PlannedMeal meal);
    public void deletePlannedMeal(PlannedMeal meal);
}
