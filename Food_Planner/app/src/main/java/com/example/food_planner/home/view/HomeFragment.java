package com.example.food_planner.home.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planner.R;
import com.example.food_planner.databinding.FragmentHomeBinding;
import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.presenter.HomePresenterImp;
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
import com.example.food_planner.utils.adapters.CategoryAdapter;
import com.example.food_planner.utils.adapters.IngredientAdapter;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.example.food_planner.utils.OnCategoryClickListener;
import com.example.food_planner.utils.OnIngredientClickListener;
import com.example.food_planner.utils.OnMealClickListener;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnIngredientClickListener, OnCategoryClickListener {

    ArrayList<Meal> tenRandomMealArrayList;
    ArrayList<Meal> randomMealArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Category> categoryArrayList;
    CarouselRecyclerview carouselRecyclerviewTenRandomMeals;
    RecyclerView recyclerViewIngredients;
    RecyclerView recyclerViewCategories;
    MealAdapter tenRandomMealAdapter;
    MealAdapter randomMealAdapter;
    IngredientAdapter ingredientAdapter;
    CategoryAdapter categoryAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager ingredientLayoutManager;
    LinearLayoutManager categoryLayoutManager;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        randomMealArrayList = new ArrayList<>();
        randomMealAdapter = new MealAdapter(getContext(), randomMealArrayList, this);

        tenRandomMealArrayList = new ArrayList<>();
        tenRandomMealAdapter = new MealAdapter(getContext(), tenRandomMealArrayList, this);

        ingredientArrayList = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(getContext(), ingredientArrayList, this);

        categoryArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList, this);

        ingredientLayoutManager = new LinearLayoutManager(getActivity());
        ingredientLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        categoryLayoutManager = new LinearLayoutManager(getActivity());
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        carouselRecyclerviewTenRandomMeals = binding.carouselRecyclerviewTenRandomMeals;
        carouselRecyclerviewTenRandomMeals.setAdapter(tenRandomMealAdapter);

        recyclerViewIngredients = binding.recyclerviewIngredients;
        recyclerViewIngredients.setLayoutManager(ingredientLayoutManager);
        recyclerViewIngredients.setAdapter(ingredientAdapter);

        recyclerViewCategories = binding.recyclerviewCategories;
        recyclerViewCategories.setLayoutManager(categoryLayoutManager);
        recyclerViewCategories.setAdapter(categoryAdapter);


        homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())), IngredientsRepositoryImp.getInstance(IngredientsRemoteDataSourceImp.getInstance(), IngredientsLocalDataSourceImp.getInstance(getContext())), CategoryRepositoryImp.getInstance(CategoryRemoteDataSourceImp.getInstance(), CategoryLocalDataSourceImp.getInstance(getContext())), this);
        homePresenter.getTenRandomMeals();
//        homePresenter.getSingleRandomMeal();
        homePresenter.getAllIngredients();
        homePresenter.getAllCategories();


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
        tenRandomMealAdapter.setMeals(mealList);
        tenRandomMealAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowIngredients(List<Ingredient> ingredientList) {
        ingredientAdapter.setIngredients(ingredientList);
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowCategories(List<Category> categoryList) {
        categoryAdapter.setCategories(categoryList);
        categoryAdapter.notifyDataSetChanged();
        Log.d("HomeFragment", "Categories received: " + categoryList.size());

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
        if (!favState) {
            homePresenter.addMealToFavourite(meal);
            Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
        } else {
            homePresenter.removeMealFromFavourite(meal);
            Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIngredientClickListener(ImageView imageView, Ingredient ingredient) {
        if (ingredient != null && ingredient.getStrIngredient() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("ingredientName", ingredient.getStrIngredient());

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.ingredientMealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Ingredient is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClickListener(ImageView imageView, Category category) {
        if (category != null && category.getStrCategory() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", category.getStrCategory());

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.categoryMealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Category is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}