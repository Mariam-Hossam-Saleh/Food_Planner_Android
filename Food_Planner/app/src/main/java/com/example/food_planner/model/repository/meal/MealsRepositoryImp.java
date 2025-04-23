package com.example.food_planner.model.repository.meal;

import androidx.lifecycle.LiveData;
import com.example.food_planner.model.database.MealLocalDataSource;
import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.network.meal.MealsRemoteDataSource;
import com.example.food_planner.model.pojos.meal.Meal;

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
    public void getAllMeals(NetworkCallback networkCallback) {
        remoteDataSource.makeNetworkCall(networkCallback);
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
