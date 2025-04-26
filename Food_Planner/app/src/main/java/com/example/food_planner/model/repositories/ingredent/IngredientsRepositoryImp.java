package com.example.food_planner.model.repositories.ingredent;

import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSource;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSource;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSource;
import com.example.food_planner.model.pojos.ingredient.Ingredient;

public class IngredientsRepositoryImp implements IngredientsRepository{
    IngredientsRemoteDataSource ingredietsRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static IngredientsRepositoryImp productsRepository = null;
    public static IngredientsRepositoryImp getInstance(IngredientsRemoteDataSource remoteDataSource, IngredientsLocalDataSource ingredientsLocalDataSource){
        if(productsRepository == null){
            productsRepository = new IngredientsRepositoryImp(remoteDataSource, ingredientsLocalDataSource);
        }
        return productsRepository;
    }
    private IngredientsRepositoryImp(IngredientsRemoteDataSource remoteDataSource, IngredientsLocalDataSource ingredientsLocalDataSource){
        this.ingredietsRemoteDataSource = remoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public void getAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback) {
        ingredietsRemoteDataSource.makeNetworkCalltoGetAllIngredients(ingredientsNetworkCallback);
    }
}
