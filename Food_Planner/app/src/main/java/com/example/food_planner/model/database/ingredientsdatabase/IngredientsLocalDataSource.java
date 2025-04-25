package com.example.food_planner.model.database.ingredientsdatabase;

import androidx.lifecycle.LiveData;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import java.util.List;

public interface IngredientsLocalDataSource {
    void insertIngredient(Ingredient ingredient);
    void deleteIngredient(Ingredient ingredient);
    LiveData<List<Ingredient>> getAllStoredIngredients();
}
