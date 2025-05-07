package com.example.food_planner.utils;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.Meal;

public interface OnMealClickListener {
    void onMealClickListener(ImageView imageView, Meal meal);
}
