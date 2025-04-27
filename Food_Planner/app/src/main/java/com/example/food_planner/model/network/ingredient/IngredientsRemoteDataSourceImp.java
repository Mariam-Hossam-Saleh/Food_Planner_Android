package com.example.food_planner.model.network.ingredient;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.ingredient.IngredientResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientsRemoteDataSourceImp implements IngredientsRemoteDataSource{
    private static final String BASE_URL = "https://www.themealdb.com/";
    private static IngredientsRemoteDataSourceImp client = null;
    private final IngredientsService ingredientsService;
    private IngredientsRemoteDataSourceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ingredientsService = retrofit.create(IngredientsService.class);
    }

    public static IngredientsRemoteDataSourceImp getInstance(){

        if(client == null){
            client = new IngredientsRemoteDataSourceImp();
        }
        return client;
    }

    @Override
    public void makeNetworkCalltoGetAllIngredients(IngredientsNetworkCallback ingredientsNetworkCallback) {
        List<Ingredient> result = new ArrayList<>();
        ingredientsService.getAllIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                Log.i(TAG,"onResponce: CallBack"+response.raw()+response.body());
                result.addAll(response.body().getIngredients());
                ingredientsNetworkCallback.onSuccessIngredient(response.body().getIngredients());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.i(TAG,"onFailure: CallBack");
                ingredientsNetworkCallback.onFailureIngredient(t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
