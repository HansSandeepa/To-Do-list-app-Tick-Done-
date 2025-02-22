package com.example.to_dolistapp;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


public interface TasksDao {

    @Insert
    public void addTask(Tasks task);

    @Update
    public void updateTask(Tasks task);

    @Delete
    public void deleteTask(Tasks task);

    @Query("SELECT * FROM Tasks WHERE DATE(date) = DATE('now','localtime') ORDER BY TIME(time) ASC")
    public List<Tasks> getTodayTasks();

    @Query("SELECT * FROM Tasks WHERE DATE(date) < DATE('now','localtime') ORDER BY TIME(time) ASC")
    public List<Tasks> getOverdueTasks();

    @Query("SELECT * FROM Tasks WHERE DATE(date) = DATE('now','+1 day','localtime') ORDER BY TIME(time) ASC")
    public List<Tasks> getTomorrowTasks();

    @Query("SELECT * FROM Tasks WHERE DATE(date) > DATE('now','+1 day','localtime') ORDER BY DATE(date) ASC, TIME(time) ASC")
    public List<Tasks> getUpcomingTasks();
}
