package com.example.food_planner.utils;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.ingredient.Ingredient;

public interface OnIngredientClickListener {
    void onIngredientClickListener(ImageView imageView, Ingredient ingredient);
}
