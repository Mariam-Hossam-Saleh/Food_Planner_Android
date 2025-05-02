package com.example.food_planner.home.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.databinding.FragmentHomeBinding;
import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.presenter.HomePresenterImp;
import com.example.food_planner.model.database.areadatabase.AreaLocalDataSourceImp;
import com.example.food_planner.model.database.categorydatabase.CategoryLocalDataSourceImp;
import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSourceImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.area.AreaRemoteDataSourceImp;
import com.example.food_planner.model.network.category.CategoryRemoteDataSourceImp;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.area.AreaRepositoryImp;
import com.example.food_planner.model.repositories.category.CategoryRepositoryImp;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepositoryImp;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.OnAreaClickListener;
import com.example.food_planner.utils.adapters.AreaAdapter;
import com.example.food_planner.utils.adapters.CategoryAdapter;
import com.example.food_planner.utils.adapters.IngredientAdapter;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.example.food_planner.utils.OnCategoryClickListener;
import com.example.food_planner.utils.OnIngredientClickListener;
import com.example.food_planner.utils.OnMealClickListener;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnIngredientClickListener, OnCategoryClickListener, OnAreaClickListener {

    ArrayList<Meal> tenRandomMealArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Category> categoryArrayList;
    ArrayList<Area> areaArrayList;
    ImageView singleRandomMeal;
    TextView singleRandomMealText;
    CarouselRecyclerview carouselRecyclerviewTenRandomMeals;
    RecyclerView recyclerViewIngredients;
    RecyclerView recyclerViewCategories;
    RecyclerView recyclerViewAreas;
    MealAdapter tenRandomMealAdapter;
    IngredientAdapter ingredientAdapter;
    CategoryAdapter categoryAdapter;
    AreaAdapter areaAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager ingredientLayoutManager;
    LinearLayoutManager categoryLayoutManager;
    LinearLayoutManager areaLayoutManager;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        tenRandomMealArrayList = new ArrayList<>();
        tenRandomMealAdapter = new MealAdapter(getContext(), tenRandomMealArrayList, this);

        ingredientArrayList = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(getContext(), ingredientArrayList, this);

        categoryArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList, this);

        areaArrayList = new ArrayList<>();
        areaAdapter = new AreaAdapter(getContext(), areaArrayList, this);

        ingredientLayoutManager = new LinearLayoutManager(getActivity());
        ingredientLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        categoryLayoutManager = new LinearLayoutManager(getActivity());
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        areaLayoutManager = new LinearLayoutManager(getActivity());
        areaLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        singleRandomMeal = binding.singleRandomMeal;
        singleRandomMealText = binding.mealName;

        carouselRecyclerviewTenRandomMeals = binding.carouselRecyclerviewTenRandomMeals;
        carouselRecyclerviewTenRandomMeals.setAdapter(tenRandomMealAdapter);

        recyclerViewIngredients = binding.recyclerviewIngredients;
        recyclerViewIngredients.setLayoutManager(ingredientLayoutManager);
        recyclerViewIngredients.setAdapter(ingredientAdapter);

        recyclerViewCategories = binding.recyclerviewCategories;
        recyclerViewCategories.setLayoutManager(categoryLayoutManager);
        recyclerViewCategories.setAdapter(categoryAdapter);

        recyclerViewAreas = binding.recyclerviewAreas;
        recyclerViewAreas.setLayoutManager(areaLayoutManager);
        recyclerViewAreas.setAdapter(areaAdapter);

        homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),
                IngredientsRepositoryImp.getInstance(IngredientsRemoteDataSourceImp.getInstance(), IngredientsLocalDataSourceImp.getInstance(getContext())),
                CategoryRepositoryImp.getInstance(CategoryRemoteDataSourceImp.getInstance(), CategoryLocalDataSourceImp.getInstance(getContext())),
                AreaRepositoryImp.getInstance(AreaRemoteDataSourceImp.getInstance(), AreaLocalDataSourceImp.getInstance(getContext())),this);

        homePresenter.getSingleRandomMeal();
        homePresenter.getTenRandomMeals();
        homePresenter.getAllIngredients();
        homePresenter.getAllCategories();
        homePresenter.getAllAreas();


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
        if (mealList.size() == 1) {

            Meal meal = mealList.get(0);
            Glide.with(getContext())
                    .load(meal.getStrMealThumb())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .transform(new CenterCrop(), new RoundedCorners(30))
                            .override(200, 200)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.imagefailed))
                    .into(singleRandomMeal);
            singleRandomMealText.setText(meal.getStrMeal());
        } else {
            tenRandomMealAdapter.setMeals(mealList);
            tenRandomMealAdapter.notifyDataSetChanged();
        }
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowAreas(List<Area> areaList) {
        Log.d("HomeFragment", "Areas received: " + areaList.size());
        areaAdapter.setAreas(areaList);
        areaAdapter.notifyDataSetChanged();
        Log.d("HomeFragment", "Areas received: " + areaList.size());
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
            navController.navigate(R.id.action_nav_home_to_ingredientMealsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Ingredient is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClickListener(ImageView imageView, Category category) {
        if (category != null && category.getStrCategory() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", category.getStrCategory());

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_categoryMealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Category is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAreaClickListener(ImageView imageView, Area area) {
        if (area != null && area.getStrArea() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("areaName", area.getStrArea());

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_areaMealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Area is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}