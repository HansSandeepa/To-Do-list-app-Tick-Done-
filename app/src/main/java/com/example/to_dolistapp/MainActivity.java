package com.example.to_dolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_dolistapp.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTasksEmptyListener{

    Button addTaskBtn;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<TaskModel> tasksList;
    RecyclerAdapter recyclerAdapter;
    TasksDatabase db;
    ImageView noTasksImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using View Binding
        com.example.to_dolistapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set the root view as the content view
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //setup notification channels
        NotificationChannels.createNotificationChannels(this);

        // get xml components from binding class
        addTaskBtn = binding.addTaskBtn;
        recyclerView = binding.recyclerView;
        noTasksImageView = binding.noTasksImg;
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
        tasksList = new ArrayList<>();

        if(c != null && c.getCount() > 0){
            while (c.moveToNext()){
                tasksList.add(new TaskModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
            }
        }
        if (c != null){
            c.close();
        }

        //initialize recycler view
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerAdapter = new RecyclerAdapter(tasksList, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        checkIfTasksEmpty();
    }

    private void checkIfTasksEmpty(){
        if (tasksList.isEmpty()){
            onTaskEmpty();
        }else{
            onTaskNotEmpty();
        }
    }

    @Override
    public void onTaskEmpty(){
        if(recyclerView != null){
            recyclerView.setVisibility(View.GONE);
        }
        if (noTasksImageView != null){
            noTasksImageView.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this,"No tasks found",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskNotEmpty() {
        if(recyclerView != null){
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (noTasksImageView != null){
            noTasksImageView.setVisibility(View.GONE);
        }
    }

    // Refresh data in RecyclerView when the activity resumes
    // This will also handle the case where AddTaskActivity finishes
    // and MainActivity needs to show the new task or an empty state.
    @Override
    protected void onResume(){
        super.onResume();
        showDataInRecyclerView();
    }
}