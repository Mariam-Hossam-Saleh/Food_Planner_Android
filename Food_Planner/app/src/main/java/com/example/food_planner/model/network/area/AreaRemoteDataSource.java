package com.example.food_planner.model.network.area;

import com.example.food_planner.model.network.NetworkCallback;

public interface AreaRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
}
