package com.example.food_planner.home.view;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface HomeView {
    public void ShowMeals(List<Meal> mealList);
    public void ShowIngredients(List<Ingredient> ingredientList);
    public void ShowErrMsg(String error);
}
