package com.example.food_planner.model.database.categorydatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

public class CategoriesLocalDataSourceImp implements CategoriesLocalDataSource {
    private final CategoriesDAO dao;
    private static CategoriesLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Category>> storedCategories;
    private CategoriesLocalDataSourceImp(Context context){
        CategoriesDatabase database = CategoriesDatabase.getInstance(context.getApplicationContext());
        dao = database.getCategoryDAO();
        storedCategories = dao.getAllCategories();
    }
    public static CategoriesLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new CategoriesLocalDataSourceImp(context);
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
        return storedCategories;
    }
}
