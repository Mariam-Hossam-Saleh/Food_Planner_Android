package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

public interface SetIconsStatus {
    void setHeartStatus(ImageView imageView, FavoriteMeal meal);
    void setCalendarStatus(ImageView imageView, PlannedMeal meal);
}
