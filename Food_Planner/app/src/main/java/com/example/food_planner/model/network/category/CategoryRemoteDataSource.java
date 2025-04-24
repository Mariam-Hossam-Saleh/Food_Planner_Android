package com.example.food_planner.model.network.category;

import com.example.food_planner.model.network.NetworkCallback;

public interface CategoryRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
}
