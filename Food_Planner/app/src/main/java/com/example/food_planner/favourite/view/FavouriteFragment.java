package com.example.food_planner.favourite.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planner.R;
import com.example.food_planner.databinding.FragmentFavouriteBinding;
import com.example.food_planner.databinding.FragmentHomeBinding;
import com.example.food_planner.favourite.presenter.FavouritePresenter;
import com.example.food_planner.favourite.presenter.FavouritePresenterImp;
import com.example.food_planner.home.view.HomeFragment;
import com.example.food_planner.model.database.ingredientsdatabase.IngredientsLocalDataSourceImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.ingredient.IngredientsRemoteDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepositoryImp;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.OnCalendarIconClickListener;
import com.example.food_planner.utils.OnFavIconClickListener;
import com.example.food_planner.utils.OnMealClickListener;
import com.example.food_planner.utils.adapters.FavoriteMealsAdapter;
import com.example.food_planner.utils.adapters.MealAdapter;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FavouriteFragment extends Fragment implements FavouriteView, OnMealClickListener, OnFavIconClickListener, OnCalendarIconClickListener {

    LiveData<List<FavoriteMeal>> mealArrayList;
    FavoriteMealsAdapter favoriteMealsAdapter;
    RecyclerView mealRecyclerView;
    FavouritePresenter favouritePresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentFavouriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealRecyclerView = binding.recyclerviewFavourites;
        mealRecyclerView.setHasFixedSize(true);
        mealRecyclerView.setLayoutManager(linearLayoutManager);

        favouritePresenter = new FavouritePresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);
        mealArrayList = favouritePresenter.getFavouriteMeals();
        mealArrayList.observe(getActivity(), new Observer<List<FavoriteMeal>>() {
            @Override
            public void onChanged(List<FavoriteMeal> meals) {
                favoriteMealsAdapter = new FavoriteMealsAdapter(getActivity(),meals,FavouriteFragment.this,FavouriteFragment.this);
                mealRecyclerView.setAdapter(favoriteMealsAdapter);
                favoriteMealsAdapter.setMeals(meals);

            }
        });

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


    @Override
    public void ShowFavoriteMeals(List<FavoriteMeal> mealList) {
        favoriteMealsAdapter.setMeals(mealList);
        favoriteMealsAdapter.notifyDataSetChanged();
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

            NavController navController = NavHostFragment.findNavController(FavouriteFragment.this);
            navController.navigate(R.id.action_nav_favourite_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal, boolean favState) {
        favouritePresenter.removeMealFromFavourite(meal);
        Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarIconClickListener(PlannedMeal meal) {
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
                    favouritePresenter.addMealToCalendar(meal);
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
        datePickerDialog.show();
    }
}