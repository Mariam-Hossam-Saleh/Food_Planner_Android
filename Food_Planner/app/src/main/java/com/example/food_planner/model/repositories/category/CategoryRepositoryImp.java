package com.example.food_planner.model.repositories.category;

import com.example.food_planner.model.database.categorydatabase.CategoryLocalDataSource;
import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.category.CategoryRemoteDataSource;

public class CategoryRepositoryImp implements CategoryRepository {
    CategoryRemoteDataSource categoryRemoteDataSource;
    CategoryLocalDataSource categoryLocalDataSource;
    private static CategoryRepositoryImp productsRepository = null;
    public static CategoryRepositoryImp getInstance(CategoryRemoteDataSource remoteDataSource, CategoryLocalDataSource localDataSource){
        if(productsRepository == null){
            productsRepository = new CategoryRepositoryImp(remoteDataSource, localDataSource);
        }
        return productsRepository;
    }
    private CategoryRepositoryImp(CategoryRemoteDataSource remoteDataSource, CategoryLocalDataSource localDataSource){
        this.categoryRemoteDataSource = remoteDataSource;
        this.categoryLocalDataSource = localDataSource;
    }

    @Override
    public void getAllCategories(CategoryNetworkCallback categoryNetworkCallback) {
        categoryRemoteDataSource.makeNetworkCalltoGetAllCategories(categoryNetworkCallback);

    }
}
