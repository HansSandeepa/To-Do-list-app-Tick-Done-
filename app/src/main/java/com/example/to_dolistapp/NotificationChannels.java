package com.example.to_dolistapp;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public class NotificationChannels {

    public static final String REMINDER_CHANNEL_ID = "reminder_channel";
    public static final String DUE_CHANNEL_ID = "due_channel";
    public static void createNotificationChannels(Context context) {
        NotificationChannel reminderChannel = new NotificationChannel(
                REMINDER_CHANNEL_ID,
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        reminderChannel.setDescription("Notifications for upcoming task reminders");

        NotificationChannel dueChannel = new NotificationChannel(
                DUE_CHANNEL_ID,
                "Due Time Alerts",
                NotificationManager.IMPORTANCE_HIGH
        );
        dueChannel.setDescription("Notifications for tasks that met its due time");

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(reminderChannel);
        notificationManager.createNotificationChannel(dueChannel);
    }
}
