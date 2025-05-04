package com.example.food_planner.utils;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;

public interface OnFavIconClickListener {
    void onFavIconClickListener(ImageView imageView, FavoriteMeal meal, boolean favState);
}
