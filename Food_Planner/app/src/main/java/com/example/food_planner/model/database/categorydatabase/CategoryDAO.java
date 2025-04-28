package com.example.food_planner.model.database.categorydatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM categories_table")
    public LiveData<List<Category>> getAllCategories();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertCategory(Category category);
    @Delete
    public void deleteCategory(Category category);
}
