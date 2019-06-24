package dev.mvvasilev.taskmanager.ui.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskNotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra("task_notification");
        int notificationId = intent.getIntExtra("task_notification_id", 0);
        int notificationPriority = intent.getIntExtra("task_notification_priority", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationChannel notificationChannel = new NotificationChannel("task_notification_channel", "Task Notifications", notificationPriority);
        notificationManager.createNotificationChannel(notificationChannel);

        notificationManager.notify(notificationId, notification);
    }
}
