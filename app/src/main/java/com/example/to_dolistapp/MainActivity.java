package com.example.to_dolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_dolistapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addTaskBtn;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<TaskModel> tasksList;
    RecyclerAdapter recyclerAdapter;
    TasksDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using View Binding
        com.example.to_dolistapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set the root view as the content view
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get xml components from binding class
        addTaskBtn = binding.addTaskBtn;
        recyclerView = binding.recyclerView;

        //show data in recycler view
        showDataInRecyclerView();

        //show add task model
        showAddTaskModel();
    }

    private void showAddTaskModel(){
        addTaskBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddTaskActivity.class)));
    }

    public void showDataInRecyclerView(){
        db = new TasksDatabase(MainActivity.this);
        Cursor c = db.getTasks();

        if(c.getCount() == 0){
            Toast.makeText(this,"No tasks found",Toast.LENGTH_LONG).show();
            return;
        }

        tasksList = new ArrayList<>();

        while (c.moveToNext()){
            tasksList.add(new TaskModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        }

        //initialize recycler view
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerAdapter = new RecyclerAdapter(tasksList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}