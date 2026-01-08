package com.example.to_dolistapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public class NotificationChannels {

    public NotificationChannels(Context context) {
        NotificationChannel reminderChannel = new NotificationChannel(
                "reminder_channel",
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        reminderChannel.setDescription("Notifications for upcoming task reminders");

        NotificationChannel dueChannel = new NotificationChannel(
                "due_channel",
                "Due Time Alerts",
                NotificationManager.IMPORTANCE_HIGH
        );
        dueChannel.setDescription("Notifications for tasks that met its due time");

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(reminderChannel);
        notificationManager.createNotificationChannel(dueChannel);
    }
}
