package com.example.food_planner.model.repositories.area;

import com.example.food_planner.model.network.area.AreaNetworkCallback;

public interface AreaRepository {
    public void getAllAreas(AreaNetworkCallback areaNetworkCallback);
}
