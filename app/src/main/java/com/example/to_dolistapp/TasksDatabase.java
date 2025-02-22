package com.example.to_dolistapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Tasks.class}, version = 1)
abstract public class TasksDatabase extends RoomDatabase {
    public abstract TasksDao getTasksDao();
}
