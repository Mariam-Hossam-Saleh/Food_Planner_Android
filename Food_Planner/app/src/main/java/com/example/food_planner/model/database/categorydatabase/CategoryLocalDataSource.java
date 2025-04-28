package com.example.food_planner.model.database.categorydatabase;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

public interface CategoryLocalDataSource {
    void insertCategory(Category category);
    void deleteCategory(Category category);
    LiveData<List<Category>> getAllStoredCategories();
}
