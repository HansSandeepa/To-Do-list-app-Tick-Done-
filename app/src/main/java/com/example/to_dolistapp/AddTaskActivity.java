package com.example.to_dolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.to_dolistapp.databinding.AddTaskBinding;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTaskActivity extends AppCompatActivity {

    //declare binding object
    private AddTaskBinding binding;
    String taskName, selectedDate, selectTime;
    TextView selectedDateView,selectedTimeView,standardTimeView;

    boolean isTimeBtnSelected = false,isDateBtnSelected = false;    //used to check if date and time buttons are even clicked once
    boolean isTimeSelected = false,isDateSelected = false;    //used to check if date and time dialogs are canceled without selecting a data
    String taskNameRegex = ".{0,100}$"; //regex for validation
    private TasksDatabase db;

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

        //initialize the database
        db = new TasksDatabase(AddTaskActivity.this);

        //show date picker
        datePicker();

        //show time picker
        timePicker();

        //save Tasks
        saveTask();
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
                        isDateSelected = true;
                    },
                    year, month,day
                    );

            //set minimum date to today
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

            //check if date dialog is canceled without selecting a date
            datePickerDialog.setOnDismissListener(dialog -> {
                        if (!isDateSelected) {
                            binding.dateValidation.setText(R.string.please_select_a_date);
                            isDateBtnSelected = false;
                        }else {
                            binding.dateValidation.setText("");
                            isDateBtnSelected = true;
                        }
                    });
            datePickerDialog.show();
        });
    }

    //time picker method
    private void timePicker(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        binding.buttonSelectTime.setOnClickListener(v -> {
            binding.timeValidation.setText(""); //clear error message

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
                        standardTimeView.setText(selectedTime); //
                        Log.i("SELECTED TIME: ",selectedTime);
                        String convertedTime = new TimeConversion().convertedTime(selectedTime); //convert time into human readable format
                        selectedTimeView.setText(convertedTime);
                        isTimeSelected = true;
                    },
                    hour,min,false
            );

            //check if time dialog is canceled without selecting a time
            timePickerDialog.setOnDismissListener(dialog -> {
                if (!isTimeSelected){
                    binding.timeValidation.setText(R.string.please_select_a_time);
                    isTimeBtnSelected = false;
                }else{
                    isTimeBtnSelected = true;
                    binding.timeValidation.setText("");
                }
            });

            timePickerDialog.show();
        });
    }

    //save button action
    private void saveTask(){
        binding.buttonSaveTask.setOnClickListener(v -> {
            getData();  //get data from the inputs

            //validate data
            if (insertValidation()){
                if (db.addTask(taskName,selectedDate, selectTime)){
                    Toast.makeText(AddTaskActivity.this, "Task added successfully!", Toast.LENGTH_LONG).show();

                    //new MainActivity().showDataInRecyclerView();    //update recycler view when task is added
                    startActivity(new Intent(AddTaskActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(AddTaskActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //validate Data
    private boolean insertValidation(){
        Log.i("Task name: ",taskName);

        //task name validation
        if (taskName.isEmpty()){
            binding.taskNameField.setError("Task name cannot be empty!");
            return false;
        } else if (regexCheck(taskName, taskNameRegex)) {
            binding.taskNameField.setError("Task must have less than 100 letters!");
            return false;
        }

        //date validation
        if(!isDateBtnSelected){
            binding.dateValidation.setText(R.string.please_select_a_date);
            return false;
        } else if (!isDateSelected) {
            binding.dateValidation.setText(R.string.please_select_a_date);
            return false;
        }

        //time validation
        if (!isTimeBtnSelected){
            binding.timeValidation.setText(R.string.please_select_a_time);
            return false;
        } else if (!isTimeSelected) {
            binding.timeValidation.setText(R.string.please_select_a_time);
            return false;
        }
        return true;
    }

    //update validation
    private boolean updateValidation(){
        boolean haveData = false;

        //empty check
        if (taskName.isEmpty()){
            binding.taskNameField.setError("Task name cannot be empty!");
            return false;
        }

        //task name regex check
        if (regexCheck(taskName, taskNameRegex)) {
            binding.taskNameField.setError("Task must have less that 100 letters!");
            return false;
        }
        return true;
    }


    //regex pattern compilation
    private boolean regexCheck(String input,String regex){
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(input);
        // Check if the input string matches the pattern
        return !matcher.matches();
    }

    //getData from the inputs
    private void getData(){
        taskName = binding.taskNameField.getText().toString();
        selectedDate = binding.selectedDate.getText().toString();
        selectTime = binding.standardTimeContainer.getText().toString();
    }
}
