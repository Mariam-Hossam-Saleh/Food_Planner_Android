package com.example.food_planner.home.presenter;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;

public interface HomePresenter {
    public void getSingleRandomMeal();
    public void getTenRandomMeals(ArrayList<Meal> meals);
    public void searchMealByName(String mealName);
    public void getMealsByFirstLetter(String letter);
    public void filterByMainIngredient(String ingredient);
    public void getAllIngredients();
    public void addMealToFavourite(Meal meal);
    public void removeMealFromFavourite(Meal meal);

}
