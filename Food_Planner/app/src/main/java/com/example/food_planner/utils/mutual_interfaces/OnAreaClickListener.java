package com.example.food_planner.utils;

import android.widget.ImageView;

import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;

public interface OnAreaClickListener {
    void onAreaClickListener(ImageView imageView, Area area);
}
