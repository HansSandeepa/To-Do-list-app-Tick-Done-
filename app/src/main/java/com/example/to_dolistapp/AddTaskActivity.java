package com.example.to_dolistapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_dolistapp.databinding.AddTaskBinding;

public class AddTaskActivity extends AppCompatActivity {

    //declare binding object
    private AddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //inflate the layout using view binding
        binding = AddTaskBinding.inflate(getLayoutInflater());
        //set the root view as the content view
        setContentView(binding.getRoot());
    }
}
