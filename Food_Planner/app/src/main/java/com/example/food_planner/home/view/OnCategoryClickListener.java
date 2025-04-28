package com.example.food_planner.home.view;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.meal.Meal;

public interface OnCategoryClickListener {
    void onCategoryClickListener(ImageView imageView, Category category);
}
