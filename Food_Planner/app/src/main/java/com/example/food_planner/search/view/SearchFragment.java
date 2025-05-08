package com.example.food_planner.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planner.R;
import com.example.food_planner.databinding.FragmentSearchBinding;
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
import com.example.food_planner.search.presenter.SearchPresenter;
import com.example.food_planner.search.presenter.SearchPresenterImp;
import com.example.food_planner.utils.mutual_interfaces.OnAreaClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnCategoryClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnIngredientClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.example.food_planner.utils.adapters.AreaAdapter;
import com.example.food_planner.utils.adapters.CategoryAdapter;
import com.example.food_planner.utils.adapters.IngredientAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements SearchView, OnMealClickListener, OnIngredientClickListener, OnCategoryClickListener, OnAreaClickListener {

    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Category> categoryArrayList;
    ArrayList<Area> areaArrayList;
    SearchMealsAdapter searchMealsAdapter;
    IngredientAdapter ingredientAdapter;
    CategoryAdapter categoryAdapter;
    AreaAdapter areaAdapter;
    RecyclerView mealRecyclerView;
    Button btnCategories;
    Button btnIngredients;
    Button btnCountries;
    android.widget.SearchView searchView;
    SearchPresenter searchPresenter;
    String searchElement;
    private ConnectivityManager.NetworkCallback networkCallback;
    private FragmentSearchBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMealsAdapter = new SearchMealsAdapter(getContext(), new ArrayList<>(), this);

        searchPresenter = new SearchPresenterImp(MealsRepositoryImp.getInstance(getContext(),MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),
                IngredientsRepositoryImp.getInstance(IngredientsRemoteDataSourceImp.getInstance(), IngredientsLocalDataSourceImp.getInstance(getContext())),
                CategoryRepositoryImp.getInstance(CategoryRemoteDataSourceImp.getInstance(), CategoryLocalDataSourceImp.getInstance(getContext())),
                AreaRepositoryImp.getInstance(AreaRemoteDataSourceImp.getInstance(), AreaLocalDataSourceImp.getInstance(getContext())),this);

        ingredientArrayList = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(getContext(), ingredientArrayList, this);

        categoryArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList, this);

        areaArrayList = new ArrayList<>();
        areaAdapter = new AreaAdapter(getContext(), areaArrayList, this);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        mealRecyclerView = binding.recyclerviewSearch;

        btnCategories = binding.btnCategory;
        btnIngredients = binding.btnIngredients;
        btnCountries = binding.btnCountries;
        searchView = binding.searchView;

        mealRecyclerView.setHasFixedSize(true);

        btnCategories.setOnClickListener( v -> {
            searchElement = "category";
            mealRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mealRecyclerView.setAdapter(categoryAdapter);
            searchPresenter.getAllCategories();
        });
        btnIngredients.setOnClickListener(v -> {
            searchElement = "ingredient";
            mealRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mealRecyclerView.setAdapter(ingredientAdapter);
            searchPresenter.getAllIngredients();
        });
        btnCountries.setOnClickListener(v -> {
            searchElement = "area";
            mealRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mealRecyclerView.setAdapter(areaAdapter);
            searchPresenter.getAllAreas();
        });
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.trim().toLowerCase(Locale.ROOT);

                if (query.isEmpty()) {
                    searchElement = null;               // reset filter
                    mealRecyclerView.setAdapter(null);  // clear previous results
                    return true;
                }
                if (searchElement == null || searchElement.isEmpty()) {
                    searchMealsAdapter = new SearchMealsAdapter(getContext(), new ArrayList<>(), SearchFragment.this);
                    mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mealRecyclerView.setAdapter(searchMealsAdapter);
                    searchPresenter.searchMealByName(query);
                } else {
                    filterList(query);
                }
                return true;
            }

        });


        return binding.getRoot();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void filterList(String query) {
        switch (searchElement) {
            case "ingredient":
                ArrayList<Ingredient> filteredIngredients = new ArrayList<>();
                for (Ingredient ing : ingredientArrayList) {
                    if (ing.getStrIngredient().toLowerCase(Locale.ROOT).contains(query)) {
                        filteredIngredients.add(ing);
                    }
                }
                ingredientAdapter.setIngredients(filteredIngredients);
                ingredientAdapter.notifyDataSetChanged();
                break;

            case "category":
                ArrayList<Category> filteredCategories = new ArrayList<>();
                for (Category cat : categoryArrayList) {
                    if (cat.getStrCategory().toLowerCase(Locale.ROOT).contains(query)) {
                        filteredCategories.add(cat);
                    }
                }
                categoryAdapter.setCategories(filteredCategories);
                categoryAdapter.notifyDataSetChanged();
                break;

            case "area":
                ArrayList<Area> filteredAreas = new ArrayList<>();
                for (Area area : areaArrayList) {
                    if (area.getStrArea().toLowerCase(Locale.ROOT).contains(query)) {
                        filteredAreas.add(area);
                    }
                }
                areaAdapter.setAreas(filteredAreas);
                areaAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest request = new NetworkRequest.Builder().build();

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                requireActivity().runOnUiThread(() -> {
                    binding.noConnection.setVisibility(View.GONE);
                    binding.mainContent.setVisibility(View.VISIBLE);
//                    Toast.makeText(requireContext(), "Internet connection restored", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
                requireActivity().runOnUiThread(() -> {
                    binding.noConnection.setVisibility(View.VISIBLE);
                    binding.mainContent.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                });
            }
        };

        connectivityManager.registerNetworkCallback(request, networkCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkCallback != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowSearchMeals(List<Meal> mealList) {
        searchMealsAdapter.setMeals(mealList);
        searchMealsAdapter.notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowIngredients(List<Ingredient> ingredientList) {
        ingredientArrayList.clear();
        ingredientArrayList.addAll(ingredientList); // keep the full original list
        ingredientAdapter.setIngredients(new ArrayList<>(ingredientList)); // display full list
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowAreas(List<Area> areaList) {
        areaArrayList.clear();
        areaArrayList.addAll(areaList); // store original list
        areaAdapter.setAreas(new ArrayList<>(areaList)); // display full list
        areaAdapter.notifyDataSetChanged();
        Log.d("SearchFragment", "Areas received: " + areaList.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ShowCategories(List<Category> categoryList) {
        categoryArrayList.clear();
        categoryArrayList.addAll(categoryList); // store original list
        categoryAdapter.setCategories(new ArrayList<>(categoryList)); // display full list
        categoryAdapter.notifyDataSetChanged();
        Log.d("SearchFragment", "Categories received: " + categoryList.size());
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

            NavController navController = NavHostFragment.findNavController(SearchFragment.this);
            navController.navigate(R.id.action_nav_search_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIngredientClickListener(ImageView imageView, Ingredient ingredient) {
        if (ingredient != null && ingredient.getStrIngredient() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("ingredientName", ingredient.getStrIngredient());
            bundle.putString("meal","ingredient");

            NavController navController = NavHostFragment.findNavController(SearchFragment.this);
            navController.navigate(R.id.action_nav_search_to_mealsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Ingredient is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClickListener(ImageView imageView, Category category) {
        if (category != null && category.getStrCategory() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", category.getStrCategory());
            bundle.putString("meal","category");

            NavController navController = NavHostFragment.findNavController(SearchFragment.this);
            navController.navigate(R.id.action_nav_search_to_mealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Category is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAreaClickListener(ImageView imageView, Area area) {
        if (area != null && area.getStrArea() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("areaName", area.getStrArea());
            bundle.putString("meal","area");

            NavController navController = NavHostFragment.findNavController(SearchFragment.this);
            navController.navigate(R.id.action_nav_search_to_mealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Area is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}