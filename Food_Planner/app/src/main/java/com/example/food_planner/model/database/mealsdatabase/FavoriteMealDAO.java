package com.example.food_planner.model.database.mealsdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

@Dao
public interface FavoriteMealDAO {
    @Query("SELECT * FROM favorite_meals_table")
    public LiveData<List<FavoriteMeal>> getAllFavoriteMeals();
    @Query("SELECT * FROM favorite_meals_table WHERE id = :id")
    LiveData<FavoriteMeal> getFavoriteMealById(String id);
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals_table WHERE id = :id LIMIT 1)")
    LiveData<Boolean> isMealFavorite(String id);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertFavoriteMeal(FavoriteMeal meal);
    @Delete
    public void deleteFavoriteMeal(FavoriteMeal meal);
    @Query("DELETE FROM favorite_meals_table")
    void deleteAllFavoriteMeals();
    @Query("DELETE FROM favorite_meals_table WHERE id = :id")
    void deleteFavoriteMealById(String id);
}
