package com.example.food_planner.model.database.ingredientsdatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.ingredient.Ingredient;

import java.util.List;

public class IngredientsLocalDataSourceImp implements IngredientsLocalDataSource{
    private final IngredientsDAO dao;
    private static IngredientsLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Ingredient>> storedIngredients;
    private IngredientsLocalDataSourceImp(Context context){
        IngredientsDatabase database = IngredientsDatabase.getInstance(context.getApplicationContext());
        dao = database.getIngredientDAO();
        storedIngredients = dao.getAllIngredients();
    }
    public static IngredientsLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new IngredientsLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }


    @Override
    public void insertIngredient(Ingredient ingredient) {
        new Thread(() -> dao.insertIngredient(ingredient)).start();
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        new Thread(() -> dao.deleteIngredient(ingredient)).start();
    }

    @Override
    public LiveData<List<Ingredient>> getAllStoredIngredients() {
        return storedIngredients;
    }
}
