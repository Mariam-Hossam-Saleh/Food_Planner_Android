package com.example.food_planner.model.database.mealsdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.pojos.meal.PlannedMeal;

@Database(entities  = {PlannedMeal.class},version = 1)
public abstract class PlannedMealDatabase extends RoomDatabase {
    private static PlannedMealDatabase instance = null;
    public abstract PlannedMealDAO getMealDAO();
    public static synchronized PlannedMealDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PlannedMealDatabase.class,"planned_meal_database")
                    .build();
        }
        return instance;
    }

}
