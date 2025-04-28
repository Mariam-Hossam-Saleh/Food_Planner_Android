package com.example.food_planner.model.repositories.category;

import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;

public interface CategoryRepository {
    public void getAllCategories(CategoryNetworkCallback categoryNetworkCallback);
}
