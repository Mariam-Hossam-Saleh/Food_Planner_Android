//package com.example.food_planner.utils;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.StyleRes;
//import androidx.fragment.app.Fragment;
//
//import com.example.food_planner.home.presenter.HomePresenter;
//import com.example.food_planner.model.pojos.meal.PlannedMeal;
//
//public class DatePickerUtil {
//
//    public interface OnDateSelectedCallback {
//        void onDateSelected(String formattedDate);
//    }
//
//    public static void showDatePickerDialog(
//            @NonNull Fragment fragment,
//            @NonNull Context context,
//            @StyleRes int styleResId,
//            @NonNull PlannedMeal meal,
//            @NonNull HomePresenter presenter,
//            @NonNull OnDateSelectedCallback callback
//    ) {
//        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                context,
//                styleResId,
//                (view, selectedYear, selectedMonth, selectedDay) -> {
//                    String selectedDate = String.format(Locale.US, "%04d-%02d-%02d",
//                            selectedYear, selectedMonth + 1, selectedDay);
//
//                    meal.setDate(selectedDate);
//                    presenter.addMealToCalendar(meal);
//                    callback.onDateSelected(selectedDate);
//                },
//                year, month, day
//        );
//
//        // Set date limits
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//
//        Calendar maxDate = Calendar.getInstance();
//        maxDate.add(Calendar.DAY_OF_YEAR, 7);
//        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
//
//        // Optional title
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.getDefault());
//        String today = sdf.format(calendar.getTime());
//        String maxDateStr = sdf.format(maxDate.getTime());
//        datePickerDialog.setTitle("Select date (" + today + " - " + maxDateStr + ")");
//
//        datePickerDialog.show();
//    }
//}
//
