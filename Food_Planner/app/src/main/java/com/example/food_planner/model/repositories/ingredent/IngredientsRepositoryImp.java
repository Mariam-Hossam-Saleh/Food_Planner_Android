package com.example.food_planner.model.repositories.ingredent;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSource;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSource;

public class IngredientsRepositoryImp implements IngredientsRepository{
    IngredientsRemoteDataSource ingredietsRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static IngredientsRepositoryImp productsRepository = null;
    public static IngredientsRepositoryImp getInstance(IngredientsRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        if(productsRepository == null){
            productsRepository = new IngredientsRepositoryImp(remoteDataSource, mealLocalDataSource);
        }
        return productsRepository;
    }
    private IngredientsRepositoryImp(IngredientsRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource){
        this.ingredietsRemoteDataSource = remoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public void getAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback) {
        ingredietsRemoteDataSource.makeNetworkCalltoGetAllIngredients(ingredientsNetworkCallback);
    }
}
