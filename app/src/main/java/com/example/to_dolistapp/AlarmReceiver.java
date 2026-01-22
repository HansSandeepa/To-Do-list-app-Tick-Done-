package com.example.to_dolistapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra("TASK_NAME");
        int taskId = intent.getIntExtra("TASK_ID",0);

        //Default to reminder if something goes wrong
        String channelId = intent.getStringExtra("CHANNEL_ID");
        if(channelId == null) channelId = NotificationChannels.DUE_CHANNEL_ID;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(channelId.equals(NotificationChannels.REMINDER_CHANNEL_ID) ? "Reminder": "Task Due!")
                .setContentText(taskName)
                .setPriority(channelId.equals(NotificationChannels.DUE_CHANNEL_ID) ? NotificationCompat.PRIORITY_HIGH : NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(taskId,builder.build());
        }

    }
}
