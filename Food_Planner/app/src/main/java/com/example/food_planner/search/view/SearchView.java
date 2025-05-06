package com.example.food_planner.search.view;

import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public interface SearchView {
    public void ShowSearchMeals(List<Meal> mealList);
    public void ShowIngredients(List<Ingredient> ingredientList);
    public void ShowCategories(List<Category> categoryList);
    public void ShowAreas(List<Area> areaList);
    public void ShowErrMsg(String error);
}
