package com.example.food_planner.model.database.categorydatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

public class CategoryLocalDataSourceImp implements CategoryLocalDataSource {
    private final CategoryDAO dao;
    private static CategoryLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<Category>> storedCategories;
    private CategoryLocalDataSourceImp(Context context){
        CategoriesDatabase database = CategoriesDatabase.getInstance(context.getApplicationContext());
        dao = database.getCategoryDAO();
        storedCategories = dao.getAllCategories();
    }
    public static CategoryLocalDataSourceImp getInstance(Context context){
        if(localDataSourceImp == null){
            localDataSourceImp = new CategoryLocalDataSourceImp(context);
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
