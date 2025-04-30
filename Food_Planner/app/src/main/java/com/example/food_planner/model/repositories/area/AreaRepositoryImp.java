package com.example.food_planner.model.repositories.area;

import com.example.food_planner.model.database.areadatabase.AreaLocalDataSource;
import com.example.food_planner.model.database.categorydatabase.CategoryLocalDataSource;
import com.example.food_planner.model.network.area.AreaNetworkCallback;
import com.example.food_planner.model.network.area.AreaRemoteDataSource;
import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.category.CategoryRemoteDataSource;
import com.example.food_planner.model.repositories.category.CategoryRepository;

public class AreaRepositoryImp implements AreaRepository {
    AreaRemoteDataSource areaRemoteDataSource;
    AreaLocalDataSource areaLocalDataSource;
    private static AreaRepositoryImp productsRepository = null;
    public static AreaRepositoryImp getInstance(AreaRemoteDataSource remoteDataSource, AreaLocalDataSource localDataSource){
        if(productsRepository == null){
            productsRepository = new AreaRepositoryImp(remoteDataSource, localDataSource);
        }
        return productsRepository;
    }
    private AreaRepositoryImp(AreaRemoteDataSource remoteDataSource, AreaLocalDataSource localDataSource){
        this.areaRemoteDataSource = remoteDataSource;
        this.areaLocalDataSource = localDataSource;
    }

    @Override
    public void getAllAreas(AreaNetworkCallback areaNetworkCallback) {
        areaRemoteDataSource.makeNetworkCalltoGetAllAreas(areaNetworkCallback);

    }
}
