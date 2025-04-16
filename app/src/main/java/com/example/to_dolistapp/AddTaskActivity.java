package com.example.to_dolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.to_dolistapp.databinding.AddTaskBinding;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    //declare binding object
    private AddTaskBinding binding;
    TextView selectedDateView,selectedTimeView,standardTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //inflate the layout using view binding
        binding = AddTaskBinding.inflate(getLayoutInflater());
        //set the root view as the content view
        setContentView(binding.getRoot());

        //initialize the selectedDate TextView
        selectedDateView = binding.selectedDate;
        selectedTimeView = binding.selectedTime;    //this is used for human readable time format (hh:mm a)
        standardTimeView = binding.standardTimeContainer;   //this is used for standard time format (HH:mm)

        //show date picker
        datePicker();

        //show time picker
        timePicker();
    }

    //date picker method
    private void datePicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.buttonSelectDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view,selectedYear,selectedMonth,selectedDate) ->{
                        String date;
                        if ((selectedMonth + 1) < 10){
                            if (selectedDate < 10){
                                date = selectedYear + "-0" + (selectedMonth + 1)  + "-0" + selectedDate;
                            }else {
                                date = selectedYear + "-0" + (selectedMonth + 1)  + "-" + selectedDate;
                            }
                        }else {
                            if (selectedDate < 10){
                                date = selectedYear + "-" + (selectedMonth + 1)  + "-0" + selectedDate;
                            }else {
                                date = selectedYear + "-" + (selectedMonth + 1)  + "-" + selectedDate;
                            }
                        }
                        Log.i("SELECTED DATE: ",date);
                        selectedDateView.setText(date);
                    },
                    year, month,day
                    );
            datePickerDialog.show();
        });
    }

    //time picker method
    private void timePicker(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        binding.buttonSelectTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view,hourOfDay,minute) ->{
                        String selectedTime;
                        if (hourOfDay < 10){
                            if (minute < 10){
                                selectedTime = "0" + hourOfDay + ":0" + minute;
                            }else {
                                selectedTime = "0" + hourOfDay + ":" + minute;
                            }
                        }else {
                            if (minute < 10){
                                selectedTime = hourOfDay + ":0" + minute;
                            }else {
                                selectedTime = hourOfDay + ":" + minute;
                            }
                        }
                        standardTimeView.setText(selectedTime);
                        Log.i("SELECTED TIME: ",selectedTime);
                        String convertedTime = new TimeConversion().convertedTime(selectedTime); //convert time into human readable format
                        selectedTimeView.setText(convertedTime);
                    },
                    hour,min,false
            );
            timePickerDialog.show();
        });
    }
}
