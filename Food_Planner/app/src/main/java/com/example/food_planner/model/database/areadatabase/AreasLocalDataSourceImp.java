package com.example.food_planner.model.database.areadatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.food_planner.model.pojos.area.Area;

import java.util.List;

public class AreasLocalDataSourceImp implements AreasLocalDataSource {
    private final AreasDAO dao;
    private static AreasLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Area>> storedAreas;
    private AreasLocalDataSourceImp(Context context){
        AreasDatabase database = AreasDatabase.getInstance(context.getApplicationContext());
        dao = database.getAreaDAO();
        storedAreas = dao.getAllAreas();
    }
    public static AreasLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new AreasLocalDataSourceImp(context);
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
