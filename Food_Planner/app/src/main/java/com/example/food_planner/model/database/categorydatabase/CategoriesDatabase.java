package com.example.food_planner.model.database.categorydatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.pojos.category.Category;

@Database(entities  = {Category.class},version = 1)
public abstract class CategoriesDatabase extends RoomDatabase {
    private static CategoriesDatabase instance = null;
    public abstract CategoryDAO getCategoryDAO();
    public static synchronized CategoriesDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CategoriesDatabase.class,"categories_database")
                    .build();
        }
        return instance;
    }

}