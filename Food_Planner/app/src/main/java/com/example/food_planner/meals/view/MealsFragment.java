package com.example.food_planner.meals.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planner.R;
import com.example.food_planner.SignInActivity;
import com.example.food_planner.WelcomeActivity;
import com.example.food_planner.databinding.FragmentMealsBinding;
import com.example.food_planner.meals.presenter.MealsPresenter;
import com.example.food_planner.meals.presenter.MealsPresenterImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.example.food_planner.utils.mutual_interfaces.SetIconsStatus;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MealsFragment extends Fragment implements MealsView,OnMealClickListener , OnFavIconClickListener , OnCalendarIconClickListener , SetIconsStatus {
    ArrayList<Meal> mealsArrayList;
    RecyclerView recyclerviewMeals;
    MealAdapter mealAdapter;
    MealsPresenter mealsPresenter;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth firebaseAuth;
    private FragmentMealsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealsBinding.inflate(inflater, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        if (getArguments() != null && getArguments().getString("meal") != null) {
            String meal = getArguments().getString("meal");

            mealsArrayList = new ArrayList<>();
            mealAdapter = new MealAdapter(getContext(), mealsArrayList, this,this,this,this);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);


            recyclerviewMeals = binding.recyclerviewMeal;
            recyclerviewMeals.setLayoutManager(linearLayoutManager);
            recyclerviewMeals.setAdapter(mealAdapter);

            mealsPresenter = new MealsPresenterImp(MealsRepositoryImp.getInstance(getContext(),MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);

            switch (meal)
            {
                case "ingredient" : mealsPresenter.filterByMainIngredient(getArguments().getString("ingredientName")); break;
                case "category" : mealsPresenter.filterMealByCategory(getArguments().getString("categoryName")); break;
                case "area" : mealsPresenter.filterByArea(getArguments().getString("areaName")); break;
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
        else {
            if (meal.isFavorite) {
                imageView.setImageResource(R.drawable.favourite);
                mealsPresenter.removeMealFromFavourite(meal);
                Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = false;
            } else {
                imageView.setImageResource(R.drawable.favourite_colored);
                mealsPresenter.addMealToFavourite(meal);
                Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = true;
            }
        }
    }

    @Override
    public void onCalendarIconClickListener(ImageView imageView,PlannedMeal meal) {
        if (firebaseAuth.getCurrentUser() == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
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
        else {
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
                        mealsPresenter.addMealToCalendar(meal);
                        imageView.setImageResource(R.drawable.calendar_colored);
                        Toast.makeText(requireContext(), meal.getStrMeal() + " planned for " + selectedDate, Toast.LENGTH_LONG).show();
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
        LiveData<Boolean> isFavorite = mealsPresenter.isMealFavorite(favoriteMeal);
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
        LiveData<Boolean> isPlanned = mealsPresenter.isMealPlanned(plannedMeal);
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
}
