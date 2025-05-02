package com.example.food_planner;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food_planner.databinding.FragmentCategoryMealsBinding;
import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.presenter.HomePresenterImp;
import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.database.areadatabase.AreaLocalDataSourceImp;
import com.example.food_planner.model.network.area.AreaRemoteDataSourceImp;
import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.repositories.area.AreaRepositoryImp;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.example.food_planner.utils.OnMealClickListener;
import com.example.food_planner.model.database.categorydatabase.CategoryLocalDataSourceImp;
import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSourceImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.category.CategoryRemoteDataSourceImp;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.category.CategoryRepositoryImp;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepositoryImp;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;

import java.util.ArrayList;
import java.util.List;


public class AreaMealsFragment extends Fragment implements HomeView,OnMealClickListener {
    ArrayList<Meal> mealsArrayList;
    RecyclerView recyclerviewMeals;
    MealAdapter mealAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentCategoryMealsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            String areaName = getArguments().getString("areaName");

            binding = FragmentCategoryMealsBinding.inflate(inflater, container, false);

            mealsArrayList = new ArrayList<>();
            mealAdapter = new MealAdapter(getContext(), mealsArrayList, this);

            linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);


            recyclerviewMeals = binding.recyclerviewCategoryMeal;
            recyclerviewMeals.setLayoutManager(linearLayoutManager);
            recyclerviewMeals.setAdapter(mealAdapter);

            homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),
                    IngredientsRepositoryImp.getInstance(IngredientsRemoteDataSourceImp.getInstance(), IngredientsLocalDataSourceImp.getInstance(getContext())),
                    CategoryRepositoryImp.getInstance(CategoryRemoteDataSourceImp.getInstance(), CategoryLocalDataSourceImp.getInstance(getContext())),
                    AreaRepositoryImp.getInstance(AreaRemoteDataSourceImp.getInstance(), AreaLocalDataSourceImp.getInstance(getContext())),this);
            homePresenter.filterByArea(areaName);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowMeals(List<Meal> mealList) {
        mealAdapter.setMeals(mealList);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowIngredients(List<Ingredient> ingredientList) {

    }

    @Override
    public void ShowCategories(List<Category> categoryList) {

    }

    @Override
    public void ShowAreas(List<Area> areaList) {

    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onMealClickListener(ImageView imageView, Meal meal, boolean favState) {
        if(!favState) {
            homePresenter.addMealToFavourite(meal);
            Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
        }
        else{
            homePresenter.removeMealFromFavourite(meal);
            Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
        }
    }

}
