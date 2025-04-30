package com.example.food_planner.model.database.areadatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_planner.model.pojos.area.Area;

@Database(entities  = {Area.class},version = 1)
public abstract class AreaDatabase extends RoomDatabase {
    private static AreaDatabase instance = null;
    public abstract AreaDAO getAreaDAO();
    public static synchronized AreaDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AreaDatabase.class,"areas_database")
                    .build();
        }
        return instance;
    }

}