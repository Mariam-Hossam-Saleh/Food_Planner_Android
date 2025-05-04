package com.example.food_planner.model.database.mealsdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;

@Database(entities  = {FavoriteMeal.class},version = 1)
public abstract class FavoriteMealDatabase extends RoomDatabase {
    private static FavoriteMealDatabase instance = null;
    public abstract FavoriteMealDAO getMealDAO();
    public static synchronized FavoriteMealDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteMealDatabase.class,"favorite_meal_database")
                    .build();
        }
        return instance;
    }

}
