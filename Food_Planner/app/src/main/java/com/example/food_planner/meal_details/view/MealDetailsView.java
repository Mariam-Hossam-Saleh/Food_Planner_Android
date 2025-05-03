package com.example.food_planner.meal_details.view;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface MealDetailsView {
    public void ShowMealDetails(List<Meal> mealList);
    public void ShowIngredients(List<Ingredient> ingredientList);
    public void ShowErrMsg(String error);
}
