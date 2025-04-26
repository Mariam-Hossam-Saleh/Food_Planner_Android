package com.example.food_planner.model.database.areadatabase;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.area.Area;

import java.util.List;

public interface AreasLocalDataSource {
    void insertArea(Area area);
    void deleteArea(Area area);
    LiveData<List<Area>> getAllStoredAreas();
}
