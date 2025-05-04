package com.example.food_planner.model.database.mealsdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meals_table")
    public LiveData<List<Meal>> getAllFavoriteMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFavoriteMeal(Meal meal);
    @Delete
    public void deleteFavoriteMeal(Meal meal);
}
