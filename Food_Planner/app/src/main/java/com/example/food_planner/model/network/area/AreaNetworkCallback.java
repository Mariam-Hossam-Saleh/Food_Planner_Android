package com.example.food_planner.model.network.area;

import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;

import java.util.List;

public interface AreaNetworkCallback {
    public void onSuccessArea(List<Area> areas);
    public void onFailureArea(String errorMSG);
}
