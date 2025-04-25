package com.example.food_planner.model.database.ingredientsdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.database.mealsdatabase.MealDAO;
import com.example.food_planner.model.pojos.meal.Meal;

@Database(entities  = {Meal.class},version = 1)
public abstract class IngredientsDatabase extends RoomDatabase {
    private static IngredientsDatabase instance = null;
    public abstract IngredientsDAO getIngredientDAO();
    public static synchronized IngredientsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),IngredientsDatabase.class,"ingredients_database")
                    .build();
        }
        return instance;
    }

}