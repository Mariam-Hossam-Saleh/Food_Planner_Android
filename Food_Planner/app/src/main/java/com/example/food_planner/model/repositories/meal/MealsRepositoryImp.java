package com.example.food_planner.model.repositories.meal;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSource;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.network.meal.MealRemoteDataSource;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

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
    public void searchMealByName(MealNetworkCallback mealNetworkCallback, String mealName) {
        remoteDataSource.makeNetworkCallForSearchMealByName(mealNetworkCallback,mealName);
    }

    @Override
    public void searchMealByID(MealNetworkCallback mealNetworkCallback, String mealID) {
        remoteDataSource.makeNetworkCallToFilterMealByID(mealNetworkCallback, mealID);
    }

    @Override
    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback,Boolean isSingle) {
        remoteDataSource.makeNetworkCallForSingleRandomMeal(mealNetworkCallback, isSingle);
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
    public void filterByArea(MealNetworkCallback mealNetworkCallback, String area) {
        remoteDataSource.makeNetworkCallToFilterMealByArea(mealNetworkCallback, area);
    }

    @Override
    public void insertFavoriteMeal(FavoriteMeal meal) {
        mealLocalDataSource.insertFavoriteMeal(meal);
    }

    @Override
    public void deleteFavoriteMeal(FavoriteMeal meal) {
        mealLocalDataSource.deleteFavoriteMeal(meal);
    }

    @Override
    public void insertPlannedMeal(PlannedMeal meal) {
        mealLocalDataSource.insertPlannedMeal(meal);
    }

    @Override
    public void deletePlannedMeal(PlannedMeal meal) {
        mealLocalDataSource.deletePlannedMeal(meal);
    }

    @Override
    public LiveData<List<FavoriteMeal>> getStoredFavoriteMeals() {
        return mealLocalDataSource.getStoredFavoriteMeals();
    }

}
