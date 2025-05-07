package com.example.food_planner.model.database.mealsdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

@Dao
public interface PlannedMealDAO {
    @Query("SELECT * FROM planned_meals_table WHERE date = :date")
    public LiveData<List<PlannedMeal>> getPlannedMealByDate(String date);
    @Query("SELECT * FROM planned_meals_table")
    public LiveData<List<PlannedMeal>> getPlannedMeals();
    @Query("SELECT * FROM planned_meals_table WHERE date = :date")
    LiveData<List<PlannedMeal>> getPlannedMealsByDate(String date);

    @Query("SELECT EXISTS(SELECT 1 FROM planned_meals_table WHERE id = :id LIMIT 1)")
    LiveData<Boolean> isMealPlanned(String id);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertPlannedMeal(PlannedMeal meal);

    @Query("DELETE FROM planned_meals_table WHERE date = :date AND idMeal = :mealId")
    void deletePlannedMeal(String date, String mealId);

    @Query("DELETE FROM planned_meals_table")
    void deleteAllPlannedMeals();
}
