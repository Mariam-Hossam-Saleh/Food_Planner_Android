package com.example.food_planner.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planner.databinding.FragmentHomeBinding;
import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.presenter.HomePresenterImp;
import com.example.food_planner.model.database.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealsRemoteDataSourceImp;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener{

    ArrayList<Meal> mealArrayList;
    RecyclerView homeRecyclerView;
    HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mealArrayList = new ArrayList<>();
        homeRecyclerView = binding.recycleViewHome;
        homeRecyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        homeAdapter = new HomeAdapter(getContext(),mealArrayList,this);
        homeRecyclerView.setAdapter(homeAdapter);

        homePresenter = new HomePresenterImp(MealsRepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);
        homePresenter.getMealsByFirstLetter("s");
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void ShowMeals(List<Meal> mealList) {
        homeAdapter.setMeals(mealList);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMealClickListener(Meal meal) {
        homePresenter.addToFavourite(meal);
        Toast.makeText(getActivity(),"Added to favorite successfully!",Toast.LENGTH_SHORT).show();

    }
}