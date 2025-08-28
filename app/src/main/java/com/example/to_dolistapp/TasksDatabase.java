package com.example.to_dolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksDatabase extends SQLiteOpenHelper {
    public TasksDatabase(Context context) {
        super(context, "Tasks_Database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT, date TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);
    }

    public boolean addTask(String _taskName, String _date, String _time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task_name", _taskName);
        cv.put("date", _date);
        cv.put("time", _time);
        long result = db.insert("Tasks", null, cv);
        return result != -1;
    }

    public void deleteTask(int _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Tasks WHERE id = ?", new String[]{String.valueOf(_id)});
        if (c.getCount() > 0){
            c.close();
            db.delete("Tasks","id = ?", new String[]{String.valueOf(_id)});
            return;
        }
        c.close();
    }

    public Cursor getTaskTimeAndDate(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT date, time FROM Tasks WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM Tasks ORDER BY DATE(date) DESC, TIME(time) ASC", null);
    }
}
