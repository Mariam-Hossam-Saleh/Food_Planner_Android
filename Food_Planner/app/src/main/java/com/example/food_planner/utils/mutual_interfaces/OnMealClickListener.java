package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.Meal;

public interface OnMealClickListener {
    void onMealClickListener(ImageView imageView, Meal meal);
}
