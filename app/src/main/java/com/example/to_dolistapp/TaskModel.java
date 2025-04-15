package com.example.to_dolistapp;

public class TaskModel {
    private final String taskName,date,time;

    public TaskModel(String taskName, String date, String time) {
        this.taskName = taskName;
        this.date = date;
        this.time = time;
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
}
