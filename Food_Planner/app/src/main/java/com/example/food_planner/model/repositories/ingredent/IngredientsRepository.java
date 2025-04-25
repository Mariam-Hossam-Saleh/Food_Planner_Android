package com.example.food_planner.model.repositories.ingredent;

import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;

public interface IngredientsRepository {
    public void getAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback);
}
