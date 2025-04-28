package com.example.food_planner.model.repositories.ingredent;

import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSource;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSource;

public class IngredientsRepositoryImp implements IngredientsRepository{
    IngredientsRemoteDataSource ingredientsRemoteDataSource;
    IngredientsLocalDataSource ingredientsLocalDataSource;
    private static IngredientsRepositoryImp productsRepository = null;
    public static IngredientsRepositoryImp getInstance(IngredientsRemoteDataSource remoteDataSource, IngredientsLocalDataSource ingredientsLocalDataSource){
        if(productsRepository == null){
            productsRepository = new IngredientsRepositoryImp(remoteDataSource, ingredientsLocalDataSource);
        }
        return productsRepository;
    }
    private IngredientsRepositoryImp(IngredientsRemoteDataSource remoteDataSource, IngredientsLocalDataSource ingredientsLocalDataSource){
        this.ingredientsRemoteDataSource = remoteDataSource;
        this.ingredientsLocalDataSource = ingredientsLocalDataSource;
    }

    @Override
    public void getAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback) {
        ingredientsRemoteDataSource.makeNetworkCalltoGetAllIngredients(ingredientsNetworkCallback);
    }

}
