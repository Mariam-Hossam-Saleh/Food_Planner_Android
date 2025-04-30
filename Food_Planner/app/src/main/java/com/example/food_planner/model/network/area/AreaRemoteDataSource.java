package com.example.food_planner.model.network.area;

import com.example.food_planner.model.network.meal.MealNetworkCallback;

public interface AreaRemoteDataSource {
    void makeNetworkCalltoGetAllAreas(AreaNetworkCallback areaNetworkCallback);
}
