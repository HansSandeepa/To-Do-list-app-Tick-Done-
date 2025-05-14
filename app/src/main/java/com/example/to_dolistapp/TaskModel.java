package com.example.to_dolistapp;

public class TaskModel {

    private final int id;
    private final String taskName,date,time;

    public TaskModel(int id, String taskName, String date, String time) {
        this.id = id;
        this.taskName = taskName;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
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
