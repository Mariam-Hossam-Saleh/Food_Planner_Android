package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;
import com.example.food_planner.model.database.MealLocalDataSource;
import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.network.meal.MealsRemoteDataSource;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsRepositoryImp implements MealsRepository {
    MealsRemoteDataSource remoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static MealsRepositoryImp productsRepository = null;
    public static MealsRepositoryImp getInstance(MealsRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        if(productsRepository == null){
            productsRepository = new MealsRepositoryImp(remoteDataSource, mealLocalDataSource);
        }
        return productsRepository;
    }
    private MealsRepositoryImp(MealsRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        this.remoteDataSource = remoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }


    public LiveData<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public void searchMealByName(NetworkCallback networkCallback, String mealName) {
        remoteDataSource.makeNetworkCallForSearchMealByName(networkCallback,mealName);
    }

    @Override
    public void getSingleRandomMeal(NetworkCallback networkCallback) {
        remoteDataSource.makeNetworkCallForSingleRandomMeal(networkCallback);
    }

    @Override
    public void getTenRandomMeal(NetworkCallback networkCallback, ArrayList<Meal> meals) {
        remoteDataSource.makeNetworkCallForTenRandomMeals(networkCallback,meals);
    }

    @Override
    public void getMealsByFirstLetter(NetworkCallback networkCallback, String letter) {
        remoteDataSource.makeNetworkCallForMealsByFirstLetter(networkCallback,letter);

    }

    @Override
    public void insertMeal(Meal meal) {
        mealLocalDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealLocalDataSource.deleteMeal(meal);
    }
}
