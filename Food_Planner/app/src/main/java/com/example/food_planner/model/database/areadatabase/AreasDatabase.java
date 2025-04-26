package com.example.food_planner.model.database.areadatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.pojos.area.Area;

@Database(entities  = {Area.class},version = 1)
public abstract class AreasDatabase extends RoomDatabase {
    private static AreasDatabase instance = null;
    public abstract AreasDAO getAreaDAO();
    public static synchronized AreasDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AreasDatabase.class,"areas_database")
                    .build();
        }
        return instance;
    }

}