package com.example.food_planner.model.database.ingredientsdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;
@Dao
public interface IngredientsDAO {
    @Query("SELECT * FROM meals_table")
    public LiveData<List<Ingredient>> getAllIngredients();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertIngredient(Ingredient ingredient);
    @Delete
    public void deleteIngredient(Ingredient ingredient);
}
