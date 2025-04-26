package com.example.food_planner.model.database.categorydatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.database.ingredientsdatabase.IngredientsDAO;
import com.example.food_planner.model.database.ingredientsdatabase.IngredientsDatabase;
import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSource;
import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

public class CategoriesLocalDataSourceImp implements CategoriesLocalDataSource {
    private final CategoriesDAO dao;
    private static CategoriesLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Category>> storedCategories;
    private CategoriesLocalDataSourceImp(Context context){
        CategoriesDatabase database = IngredientsDatabase.getInstance(context.getApplicationContext());
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
    public void insertCategory(Category category) {
        new Thread(() -> dao.insertCategory(category)).start();
    }

    @Override
    public void deleteCategory(Category category) {
        new Thread(() -> dao.deleteCategory(category)).start();
    }

    @Override
    public LiveData<List<Category>> getAllStoredCategories() {
        return storedCategory;
    }
}
