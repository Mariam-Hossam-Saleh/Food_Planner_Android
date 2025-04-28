package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSource;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.network.meal.MealRemoteDataSource;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsRepositoryImp implements MealsRepository {
    MealRemoteDataSource remoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static MealsRepositoryImp productsRepository = null;
    public static MealsRepositoryImp getInstance(MealRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        if(productsRepository == null){
            productsRepository = new MealsRepositoryImp(remoteDataSource, mealLocalDataSource);
        }
        return productsRepository;
    }
    private MealsRepositoryImp(MealRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        this.remoteDataSource = remoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public LiveData<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public void searchMealByName(MealNetworkCallback mealNetworkCallback, String mealName) {
        remoteDataSource.makeNetworkCallForSearchMealByName(mealNetworkCallback,mealName);
    }

    @Override
    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback) {
        remoteDataSource.makeNetworkCallForSingleRandomMeal(mealNetworkCallback);
    }

    @Override
    public void getTenRandomMeal(MealNetworkCallback mealNetworkCallback, ArrayList<Meal> meals) {
        remoteDataSource.makeNetworkCallForTenRandomMeals(mealNetworkCallback,meals);
    }

    @Override
    public void getMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter) {
        remoteDataSource.makeNetworkCallForMealsByFirstLetter(mealNetworkCallback,letter);

    }

    @Override
    public void filterByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient) {
        remoteDataSource.makeNetworkCallToFilterMealByIngredient(mealNetworkCallback,ingredient);
    }

    @Override
    public void filterByCategory(MealNetworkCallback mealNetworkCallback, String category) {
        remoteDataSource.makeNetworkCallToFilterMealByCategory(mealNetworkCallback,category);
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
