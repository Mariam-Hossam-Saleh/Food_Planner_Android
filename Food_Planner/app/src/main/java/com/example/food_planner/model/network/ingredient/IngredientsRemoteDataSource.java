package com.example.food_planner.model.network.ingredient;

import com.example.food_planner.model.network.NetworkCallback;

public interface IngredientsRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
}
