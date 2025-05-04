package com.example.food_planner.utils;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface OnCalendarIconClickListener {
    void onCalendarIconClickListener(PlannedMeal meal);
}
