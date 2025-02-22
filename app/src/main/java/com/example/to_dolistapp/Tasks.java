package com.example.to_dolistapp;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tasks")
public class Tasks {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "task_name")
    String taskName;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "time")
    String time;

    @Ignore
    public Tasks() {
    }

    public Tasks(String taskName, String date, String time) {
        this.taskName = taskName;
        this.date = date;
        this.time = time;
        this.id = 0;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
