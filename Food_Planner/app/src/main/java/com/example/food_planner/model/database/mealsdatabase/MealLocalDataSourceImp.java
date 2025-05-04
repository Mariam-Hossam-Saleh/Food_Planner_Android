package com.example.food_planner.model.database.mealsdatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource {
    private final FavoriteMealDAO favoriteMealDAO;
    private final PlannedMealDAO plannedMealDAO;
    private static MealLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<FavoriteMeal>> storedFavoriteMeals;
//    private final LiveData<List<PlannedMeal>> storedPlannedMeals;
    private MealLocalDataSourceImp(Context context){
        FavoriteMealDatabase favoriteMealDatabase = FavoriteMealDatabase.getInstance(context.getApplicationContext());
        PlannedMealDatabase plannedMealDatabase = PlannedMealDatabase.getInstance(context.getApplicationContext());
        favoriteMealDAO = favoriteMealDatabase.getMealDAO();
        plannedMealDAO = plannedMealDatabase.getMealDAO();
        storedFavoriteMeals = favoriteMealDAO.getAllFavoriteMeals();
//        storedPlannedMeals = plannedMealDAO.getPlannedMealByDate();
    }
    public static MealLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new MealLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }

    @Override
    public void insertFavoriteMeal(FavoriteMeal meal) {
        new Thread(() -> favoriteMealDAO.insertFavoriteMeal(meal)).start();
    }

    @Override
    public void deleteFavoriteMeal(FavoriteMeal meal) {
        new Thread(() -> favoriteMealDAO.deleteFavoriteMeal(meal)).start();
    }

    @Override
    public LiveData<List<FavoriteMeal>> getStoredFavoriteMeals() {
        return storedFavoriteMeals;
    }

    @Override
    public void insertPlannedMeal(PlannedMeal meal) {
        new Thread(() -> plannedMealDAO.insertPlannedMeal(meal)).start();
    }

    @Override
    public void deletePlannedMeal(PlannedMeal meal) {
        new Thread(() -> plannedMealDAO.deletePlannedMeal(meal.date,meal.plannedMealID)).start();
    }

    /// /////////////////////////////////////////////////////////////////////////////
    @Override
    public LiveData<List<PlannedMeal>> getStoredPlannedMeals() { return null; }
}
