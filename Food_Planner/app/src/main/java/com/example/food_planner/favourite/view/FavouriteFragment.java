package com.example.food_planner.favourite.view;

import android.app.DatePickerDialog;
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
import com.example.food_planner.databinding.FragmentFavouriteBinding;
import com.example.food_planner.favourite.presenter.FavouritePresenter;
import com.example.food_planner.favourite.presenter.FavouritePresenterImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
//import com.example.food_planner.utils.FirebaseSyncHelper;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FavouriteFragment extends Fragment implements FavouriteView, OnMealClickListener, OnFavIconClickListener, OnCalendarIconClickListener {

    LiveData<List<FavoriteMeal>> favoriteMealArrayList;
    FavoriteMealsAdapter favoriteMealsAdapter;
    RecyclerView mealRecyclerView;
    FavouritePresenter favouritePresenter;
    LinearLayoutManager linearLayoutManager;
    String userName;
    LottieAnimationView favoriteAnimationView;
    private FragmentFavouriteBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritePresenter = new FavouritePresenterImp(
                MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),MealLocalDataSourceImp.getInstance(getContext())),
                this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealRecyclerView = binding.recyclerviewFavourites;
        mealRecyclerView.setHasFixedSize(true);
        mealRecyclerView.setLayoutManager(linearLayoutManager);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteAnimationView = binding.favoriteAnimationView;

        // check if there is signed in user to display fav meals list or not
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userName = "Guest";
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getDisplayName() != null) {
            userName = mAuth.getCurrentUser().getDisplayName();
        }
        Log.d("FavoriteFragment", "UserName from FirebaseAuth: " + userName);

        if (Objects.equals(userName, "Guest")) {
            favoriteAnimationView.setVisibility(View.VISIBLE);
            mealRecyclerView.setVisibility(View.GONE);
            return ;
        } else {
            favoriteAnimationView.setVisibility(View.GONE);
            mealRecyclerView.setVisibility(View.VISIBLE);
        }

        favoriteMealArrayList = favouritePresenter.getFavouriteMeals();
        favoriteMealArrayList.observe(getActivity(), new Observer<List<FavoriteMeal>>() {
            @Override
            public void onChanged(List<FavoriteMeal> meals) {
                favoriteMealsAdapter = new FavoriteMealsAdapter(getActivity(),meals,FavouriteFragment.this,FavouriteFragment.this);
                mealRecyclerView.setAdapter(favoriteMealsAdapter);
                favoriteMealsAdapter.setMeals(meals);

            }
        });
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
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal) {
        favouritePresenter.removeMealFromFavourite(meal);
        imageView.setImageResource(R.drawable.favourite);
        Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
        meal.isFavorite = false;
    }

    @Override
    public void onCalendarIconClickListener(ImageView imageView, PlannedMeal meal) {
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