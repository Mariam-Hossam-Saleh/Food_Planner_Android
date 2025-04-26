package com.example.food_planner.home.view;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

public interface OnIngredientClickListener {
    void onIngredientClickListener(ImageView imageView, Ingredient ingredient);
}
