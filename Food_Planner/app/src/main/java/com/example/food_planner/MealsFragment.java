package com.example.food_planner;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.example.food_planner.databinding.FragmentMealsBinding;
import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.presenter.HomePresenterImp;
import com.example.food_planner.home.view.HomeView;
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
import com.example.food_planner.utils.OnFavIconClickListener;
import com.example.food_planner.utils.OnMealClickListener;
import com.example.food_planner.utils.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MealsFragment extends Fragment implements HomeView,OnMealClickListener , OnFavIconClickListener {
    ArrayList<Meal> mealsArrayList;
    RecyclerView recyclerviewMeals;
    MealAdapter mealAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentMealsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            String meal = getArguments().getString("meal");

            binding = FragmentMealsBinding.inflate(inflater, container, false);

            mealsArrayList = new ArrayList<>();
            mealAdapter = new MealAdapter(getContext(), mealsArrayList, this,this);

            linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);


            recyclerviewMeals = binding.recyclerviewMeal;
            recyclerviewMeals.setLayoutManager(linearLayoutManager);
            recyclerviewMeals.setAdapter(mealAdapter);

            homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),
                    IngredientsRepositoryImp.getInstance(IngredientsRemoteDataSourceImp.getInstance(), IngredientsLocalDataSourceImp.getInstance(getContext())),
                    CategoryRepositoryImp.getInstance(CategoryRemoteDataSourceImp.getInstance(), CategoryLocalDataSourceImp.getInstance(getContext())),
                    AreaRepositoryImp.getInstance(AreaRemoteDataSourceImp.getInstance(), AreaLocalDataSourceImp.getInstance(getContext())),this);
            switch (Objects.requireNonNull(meal))
            {
                case "ingredient" : homePresenter.filterByMainIngredient(getArguments().getString("ingredientName")); break;
                case "category" : homePresenter.filterMealByCategory(getArguments().getString("categoryName")); break;
                case "area" : homePresenter.filterByArea(getArguments().getString("areaName")); break;
            }

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
    public void onMealClickListener(ImageView imageView, Meal meal) {
        if (meal != null && meal.getStrMeal() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("mealID", meal.getIdMeal());

            NavController navController = NavHostFragment.findNavController(MealsFragment.this);
            navController.navigate(R.id.action_mealsFragment_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
        }
//        if(!favState) {
//            homePresenter.addMealToFavourite(meal);
//            Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            homePresenter.removeMealFromFavourite(meal);
//            Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onFavIconClickListener(ImageView imageView, Meal meal, boolean favState) {
        if(favState) {
            homePresenter.removeMealFromFavourite(meal);
        }
        else{
            homePresenter.addMealToFavourite(meal);
        }
    }
}
