package com.example.food_planner.model.database.areadatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.food_planner.model.pojos.area.Area;

import java.util.List;

public class AreaLocalDataSourceImp implements AreaLocalDataSource {
    private final AreaDAO dao;
    private static AreaLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Area>> storedAreas;
    private AreaLocalDataSourceImp(Context context){
        AreaDatabase database = AreaDatabase.getInstance(context.getApplicationContext());
        dao = database.getAreaDAO();
        storedAreas = dao.getAllAreas();
    }
    public static AreaLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new AreaLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }


    @Override
    public void insertArea(Area area) {
        new Thread(() -> dao.insertArea(area)).start();
    }

    @Override
    public void deleteArea(Area area) {
        new Thread(() -> dao.deleteArea(area)).start();
    }

    @Override
    public LiveData<List<Area>> getAllStoredAreas() {
        return storedAreas;
    }
}
