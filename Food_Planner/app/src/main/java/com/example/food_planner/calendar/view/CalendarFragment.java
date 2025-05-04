package com.example.food_planner.calendar.view;

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
import com.example.food_planner.calendar.presenter.CalendarPresenter;
import com.example.food_planner.calendar.presenter.CalendarPresenterImp;
import com.example.food_planner.databinding.FragmentCalenderBinding;
import com.example.food_planner.databinding.FragmentFavouriteBinding;
import com.example.food_planner.favourite.presenter.FavouritePresenter;
import com.example.food_planner.favourite.presenter.FavouritePresenterImp;
import com.example.food_planner.favourite.view.FavouriteView;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.OnCalendarIconClickListener;
import com.example.food_planner.utils.OnFavIconClickListener;
import com.example.food_planner.utils.OnMealClickListener;
import com.example.food_planner.utils.adapters.FavoriteMealsAdapter;
import com.example.food_planner.utils.adapters.PlannedMealsAdapter;

import java.util.List;

public class CalendarFragment extends Fragment implements CalendarView, OnMealClickListener, OnFavIconClickListener , OnCalendarIconClickListener {

    LiveData<List<PlannedMeal>> mealArrayList;
    PlannedMealsAdapter plannedMealsAdapter;
    RecyclerView mealRecyclerView;
    CalendarPresenter calendarPresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentCalenderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalenderBinding.inflate(inflater, container, false);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealRecyclerView = binding.recyclerviewMeal;
        mealRecyclerView.setHasFixedSize(true);
        mealRecyclerView.setLayoutManager(linearLayoutManager);

        calendarPresenter = new CalendarPresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);
        mealArrayList = calendarPresenter.getStoredPlannedMeals();
        mealArrayList.observe(getActivity(), new Observer<List<PlannedMeal>>() {
            @Override
            public void onChanged(List<PlannedMeal> meals) {
                plannedMealsAdapter = new PlannedMealsAdapter(getActivity(),meals, CalendarFragment.this, CalendarFragment.this);
                mealRecyclerView.setAdapter(plannedMealsAdapter);
                plannedMealsAdapter.setPlannedMeals(meals);

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
    public void ShowPlannedMeals(List<PlannedMeal> mealList) {
        plannedMealsAdapter.setPlannedMeals(mealList);
        plannedMealsAdapter.notifyDataSetChanged();
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

            NavController navController = NavHostFragment.findNavController(CalendarFragment.this);
            navController.navigate(R.id.action_nav_calender_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal, boolean favState) {
//        favouritePresenter.removeMealFromFavourite(meal);
//        Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarIconClickListener(PlannedMeal meal) {
        calendarPresenter.removeMealFromCalendar(meal);
        Toast.makeText(getActivity(), "Removed from calendar successfully!", Toast.LENGTH_SHORT).show();
    }
}