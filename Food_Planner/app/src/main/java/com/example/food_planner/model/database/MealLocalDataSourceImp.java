package com.example.food_planner.model.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource {
    private final MealDAO dao;
    private static MealLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Meal>> storedMeals;
    private MealLocalDataSourceImp(Context context){
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        dao = database.getMealDAO();
        storedMeals = dao.getAllMeals();
    }
    public static MealLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new MealLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(() -> dao.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(() -> dao.deleteMeal(meal)).start();
    }

    @Override
    public LiveData<List<Meal>> getAllStoredMeals() {
        return storedMeals;
    }
}
