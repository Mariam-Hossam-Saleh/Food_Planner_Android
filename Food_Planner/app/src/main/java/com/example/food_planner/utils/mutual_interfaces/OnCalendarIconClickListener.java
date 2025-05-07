package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface OnCalendarIconClickListener {
    void onCalendarIconClickListener(ImageView imageView, PlannedMeal meal);
}
