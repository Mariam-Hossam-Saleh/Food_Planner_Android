package com.example.food_planner.home.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
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
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.SignInActivity;
import com.example.food_planner.WelcomeActivity;
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
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.area.AreaRepositoryImp;
import com.example.food_planner.model.repositories.category.CategoryRepositoryImp;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepositoryImp;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.mutual_interfaces.OnAreaClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.adapters.AreaAdapter;
import com.example.food_planner.utils.adapters.CategoryAdapter;
import com.example.food_planner.utils.adapters.IngredientAdapter;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.example.food_planner.utils.mutual_interfaces.OnCategoryClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnIngredientClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.example.food_planner.utils.mutual_interfaces.SetIconsStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener, OnFavIconClickListener, OnCalendarIconClickListener,OnIngredientClickListener, OnCategoryClickListener, OnAreaClickListener , SetIconsStatus {

    ArrayList<Meal> tenRandomMealArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Category> categoryArrayList;
    ArrayList<Area> areaArrayList;
    TextView welcomeMessage;
    ImageView singleRandomMeal;
    TextView singleRandomMealText;
    ImageView favouriteIcon;
    ImageView calendarIcon;
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
    LottieAnimationView noConnectionAnimation;
    FirebaseAuth firebaseAuth;
    private ConnectivityManager.NetworkCallback networkCallback;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        noConnectionAnimation = binding.noConnection;
        noConnectionAnimation.setVisibility(View.GONE);
        // check if there is network or no
        if (!isConnected()) {
            noConnectionAnimation.setVisibility(View.VISIBLE);
            binding.mainContent.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            return binding.getRoot();
        }

        tenRandomMealArrayList = new ArrayList<>();
        tenRandomMealAdapter = new MealAdapter(getContext(), tenRandomMealArrayList, this,this,this,this);

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

        welcomeMessage = binding.welcomeMessage;
        singleRandomMeal = binding.singleRandomMeal;
        singleRandomMealText = binding.mealName;
        favouriteIcon = binding.addFavouritesIcon;
        calendarIcon = binding.addCalendarIcon;

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

        homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(getContext(),MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),
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
        ConnectivityManager connectivityManager =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest request = new NetworkRequest.Builder().build();

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                requireActivity().runOnUiThread(() -> {
                    noConnectionAnimation.setVisibility(View.GONE);
                    binding.mainContent.setVisibility(View.VISIBLE);
                   // Toast.makeText(requireContext(), "Internet connection restored", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
                requireActivity().runOnUiThread(() -> {
                    noConnectionAnimation.setVisibility(View.VISIBLE);
                    binding.mainContent.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                });
            }
        };

        connectivityManager.registerNetworkCallback(request, networkCallback);

        super.onViewCreated(view, savedInstanceState);
        String userName;
        if (getArguments() != null) {
            userName = getArguments().getString("UserName", "Guest");
            Log.d("HomeFragment", "Received username: " + userName);
        }
        else{
            userName = "Guest";
            Log.e("HomeFragment", "No arguments received!");
        }

        Log.d("HomeFragment", "Setting username: " + userName);
        getView().post(() -> {
            if (getArguments() != null) {
                String updatedName = getArguments().getString("UserName", userName);
                welcomeMessage.setText("Welcome " + updatedName);
            }
        });
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
    public void ShowMeals(List<Meal> mealList) {
        if (mealList.size() == 1) {

            Meal meal = mealList.get(0);
            FavoriteMeal favoriteMeal = new FavoriteMeal(meal);
            PlannedMeal plannedMeal = new PlannedMeal(meal);
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
            singleRandomMeal.setOnClickListener(v -> {
                if (meal.getStrMeal() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mealID", meal.getIdMeal());

                    NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                    navController.navigate(R.id.action_nav_home_to_mealDetailsFragment, bundle);
                } else {
                    Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
                }
            });
            favouriteIcon.setOnClickListener(v -> {
                onFavIconClickListener(favouriteIcon, favoriteMeal);
            });
            calendarIcon.setOnClickListener(v -> {
                onCalendarIconClickListener(calendarIcon ,plannedMeal);
            });
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
    public void onMealClickListener(ImageView imageView, Meal meal) {
        if (meal != null && meal.getStrMeal() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("mealID", meal.getIdMeal());

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal) {
        if (firebaseAuth.getCurrentUser() == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Login Required")
                    .setMessage("You need to log in to add meals to your favorites.")
                    .setPositiveButton("Login", (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }
        else{
            if(meal.isFavorite) {
                imageView.setImageResource(R.drawable.favourite);
                homePresenter.removeMealFromFavourite(meal);
                Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = false;
            }
            else{
                imageView.setImageResource(R.drawable.favourite_colored);
                homePresenter.addMealToFavourite(meal);
                Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = true;
            }
        }

    }

    @Override
    public void onIngredientClickListener(ImageView imageView, Ingredient ingredient) {
        if (ingredient != null && ingredient.getStrIngredient() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("ingredientName", ingredient.getStrIngredient());
            bundle.putString("meal","ingredient");

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_mealsFragment, bundle);
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

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_mealsFragment, bundle);
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

            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(R.id.action_nav_home_to_mealsFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Area is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCalendarIconClickListener(ImageView imageView, PlannedMeal meal) {
        if (firebaseAuth.getCurrentUser() == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Login Required")
                    .setMessage("You need to log in to add meals to your calendar.")
                    .setPositiveButton("Login", (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }
        else{
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    R.style.my_dialog_theme,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.US, "%04d-%02d-%02d",
                                selectedYear, selectedMonth + 1, selectedDay);
                        meal.setDate(selectedDate);
                        homePresenter.addMealToCalendar(meal);
                        imageView.setImageResource(R.drawable.calendar_colored);
                        Toast.makeText(requireContext(), meal.getStrMeal() +" planned for " + selectedDate, Toast.LENGTH_LONG).show();
                    },
                    year, month, day
            );
            // Set minimum date (today)
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            // Set maximum date (1 week from today)
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.DAY_OF_YEAR, 7); // Add 7 days
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

            // If the date picker is canceled, leave icon
            datePickerDialog.setOnCancelListener(dialog -> {
                imageView.setImageResource(R.drawable.calendar);
            });

            datePickerDialog.show();
        }
    }

    @Override
    public void setHeartStatus(ImageView imageView, FavoriteMeal meal) {
        FavoriteMeal favoriteMeal = new FavoriteMeal(meal);
        LiveData<Boolean> isFavorite = homePresenter.isMealFavorite(favoriteMeal);
        isFavorite.observe(getViewLifecycleOwner(), isFav -> {
            if (isFav) {
                imageView.setImageResource(R.drawable.favourite_colored);
                favoriteMeal.isFavorite = true;
            } else {
                imageView.setImageResource(R.drawable.favourite);
                favoriteMeal.isFavorite = false;
            }
        });
    }

    @Override
    public void setCalendarStatus(ImageView imageView, PlannedMeal meal) {
        PlannedMeal plannedMeal = new PlannedMeal(meal);
        LiveData<Boolean> isPlanned = homePresenter.isMealPlanned(plannedMeal);
        isPlanned.observe(getViewLifecycleOwner(), isPlan -> {
            if (isPlan) {
                imageView.setImageResource(R.drawable.calendar_colored);
                plannedMeal.isPlanned = true;
            } else {
                imageView.setImageResource(R.drawable.calendar);
                plannedMeal.isPlanned = false;
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}