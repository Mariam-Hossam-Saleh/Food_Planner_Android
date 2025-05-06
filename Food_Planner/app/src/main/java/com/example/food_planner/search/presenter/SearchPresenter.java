package com.example.food_planner.search.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface SearchPresenter {
    public void searchMealByName(String mealName);
    public void getMealsByFirstLetter(String letter);
    public void getAllIngredients();
    public void getAllCategories();
    public void getAllAreas();
    public void filterByMainIngredient(String ingredient);
    public void filterMealByCategory(String category);
    public void filterByArea(String area);

}
