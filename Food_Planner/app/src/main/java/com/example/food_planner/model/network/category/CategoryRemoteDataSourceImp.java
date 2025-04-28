package com.example.food_planner.model.network.category;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.category.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryRemoteDataSourceImp implements CategoryRemoteDataSource{
    private static final String BASE_URL = "https://www.themealdb.com/";
    private static CategoryRemoteDataSourceImp client = null;
    private final CategoryService categoryService;
    private CategoryRemoteDataSourceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        categoryService = retrofit.create(CategoryService.class);
    }

    public static CategoryRemoteDataSourceImp getInstance(){

        if(client == null){
            client = new CategoryRemoteDataSourceImp();
        }
        return client;
    }@Override
    public void makeNetworkCalltoGetAllCategories(CategoryNetworkCallback categoryNetworkCallback) {
        categoryService.getAllCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.i(TAG, "onResponse: " + response.raw());

                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    if (categories != null) {
                        categoryNetworkCallback.onSuccessCategory(categories);
                    } else {
                        Log.e(TAG, "Categories list is null!");
                        categoryNetworkCallback.onFailureCategory("Categories not found");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful or body is null");
                    categoryNetworkCallback.onFailureCategory("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                categoryNetworkCallback.onFailureCategory(t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
