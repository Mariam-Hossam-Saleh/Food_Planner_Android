package com.example.food_planner.calendar.view;

import static android.content.ContentValues.TAG;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.food_planner.R;
import com.example.food_planner.calendar.presenter.CalendarPresenter;
import com.example.food_planner.calendar.presenter.CalendarPresenterImp;
import com.example.food_planner.databinding.FragmentCalenderBinding;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment implements CalendarView, OnMealClickListener, OnFavIconClickListener , OnCalendarIconClickListener {

    LiveData<List<PlannedMeal>> mealArrayList;
    PlannedMealsAdapter plannedMealsAdapter;
    RecyclerView mealRecyclerView;
    CalendarPresenter calendarPresenter;
    LinearLayoutManager linearLayoutManager;
    android.widget.CalendarView calendarView;
    String currentSelectedDate;
    LottieAnimationView calendarAnimationView;
    String userName;
    private FragmentCalenderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalenderBinding.inflate(inflater, container, false);

        calendarView = binding.calendarView;
        calendarAnimationView = binding.calendarAnimationView;

        Calendar calendar = Calendar.getInstance();
        currentSelectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
        calendarView.setDate(calendar.getTimeInMillis(), false, true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealRecyclerView = binding.recyclerviewMeal;
        mealRecyclerView.setHasFixedSize(true);
        mealRecyclerView.setLayoutManager(linearLayoutManager);

        calendarPresenter = new CalendarPresenterImp(
                MealsRepositoryImp.getInstance(
                        MealRemoteDataSourceImp.getInstance(),
                        MealLocalDataSourceImp.getInstance(getContext())),
                this);

        plannedMealsAdapter = new PlannedMealsAdapter(getActivity(), new ArrayList<>(),
                CalendarFragment.this, CalendarFragment.this);
        mealRecyclerView.setAdapter(plannedMealsAdapter);


        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarAnimationView = binding.calendarAnimationView;

        // check if there is signed in user to display fav meals list or not
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userName = "Guest";
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getDisplayName() != null) {
            userName = mAuth.getCurrentUser().getDisplayName();
        }
        Log.d("CalendarFragment", "UserName from FirebaseAuth: " + userName);

        if (Objects.equals(userName, "Guest")) {
            calendarAnimationView.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.GONE);
            mealRecyclerView.setVisibility(View.GONE);
            return ;
        } else {
            calendarAnimationView.setVisibility(View.GONE);
            calendarView.setVisibility(View.VISIBLE);
            mealRecyclerView.setVisibility(View.VISIBLE);
        }

        mealArrayList = calendarPresenter.getPlannedMealsByDate(currentSelectedDate);
        mealArrayList.observe(getActivity(), new Observer<List<PlannedMeal>>() {
            @Override
            public void onChanged(List<PlannedMeal> meals) {
                plannedMealsAdapter = new PlannedMealsAdapter(getActivity(),meals, CalendarFragment.this, CalendarFragment.this);
                mealRecyclerView.setAdapter(plannedMealsAdapter);
                plannedMealsAdapter.setPlannedMeals(meals);

            }
        });
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            currentSelectedDate = sdf.format(calendar.getTime());
            Log.d(TAG, "Selected date: " + currentSelectedDate);
            calendarPresenter.getPlannedMealsByDate(currentSelectedDate).observe(getViewLifecycleOwner(), plannedMeals -> {
                plannedMealsAdapter.setPlannedMeals(plannedMeals);
                plannedMealsAdapter.notifyDataSetChanged();
                Log.d("CalendarFragment", "Fetched " + (plannedMeals != null ? plannedMeals.size() : 0) + " meals for " + currentSelectedDate);
                if (plannedMeals == null || plannedMeals.isEmpty()) {
                    calendarAnimationView.setVisibility(View.VISIBLE);
                    mealRecyclerView.setVisibility(View.GONE);
                } else {
                    calendarAnimationView.setVisibility(View.GONE);
                    mealRecyclerView.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
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
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal) {
//        favouritePresenter.removeMealFromFavourite(meal);
//        Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarIconClickListener(ImageView imageView, PlannedMeal meal) {
        calendarPresenter.removeMealFromCalendar(meal);
        Toast.makeText(getActivity(), "Removed from calendar successfully!", Toast.LENGTH_SHORT).show();
    }
}