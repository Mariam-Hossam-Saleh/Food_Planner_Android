package com.example.food_planner.model.database.areadatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.area.Area;

import java.util.List;

@Dao
public interface AreaDAO {
    @Query("SELECT * FROM areas_table")
    public LiveData<List<Area>> getAllAreas();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertArea(Area area);
    @Delete
    public void deleteArea(Area area);
}
