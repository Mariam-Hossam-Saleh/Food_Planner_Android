package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;

public interface OnFavIconClickListener {
    void onFavIconClickListener(ImageView imageView, FavoriteMeal meal);
}
