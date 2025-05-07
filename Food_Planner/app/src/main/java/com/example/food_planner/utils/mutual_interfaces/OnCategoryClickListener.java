package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.category.Category;

public interface OnCategoryClickListener {
    void onCategoryClickListener(ImageView imageView, Category category);
}
