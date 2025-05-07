package com.example.food_planner.utils.mutual_interfaces;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.ingredient.Ingredient;

public interface OnIngredientClickListener {
    void onIngredientClickListener(ImageView imageView, Ingredient ingredient);
}
