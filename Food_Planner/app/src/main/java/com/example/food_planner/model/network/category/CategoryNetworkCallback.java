package com.example.food_planner.model.network.category;

import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;

import java.util.List;

public interface CategoryNetworkCallback {
    public void onSuccessCategory(List<Category> categories);
    public void onFailureCategory(String errorMSG);
}
