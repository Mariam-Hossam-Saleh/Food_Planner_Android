package com.example.food_planner.home.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface HomePresenter {
    public void getSingleRandomMeal();
    public void getTenRandomMeals();
    public void searchMealByName(String mealName);
    public void getMealsByFirstLetter(String letter);
    public void filterByMainIngredient(String ingredient);
    public void getAllIngredients();
    public void getAllCategories();
    public void getAllAreas();
    public void filterMealByCategory(String category);
    public void filterByArea(String area);
    public void addMealToFavourite(FavoriteMeal meal);
    public void addMealToCalendar(PlannedMeal meal);
    public void removeMealFromFavourite(FavoriteMeal meal);
    public void removeMealFromCalendar(PlannedMeal meal);
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal);
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal);

}
