package com.example.hp1.myfinalproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import static com.example.hp1.myfinalproject.HomeWork.NOTIFICATION_CODE;

/**
 * Created by Dell on 9/6/2017.
 */



class Notifacation_reciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent homeworkIntent=new Intent(context,HomeWork.class);

        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeWork.class);
        stackBuilder.addNextIntent(homeworkIntent);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(NOTIFICATION_CODE,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);

        Notification notification=builder.setContentTitle("You Have HomeWork")
                .setContentText("You Have HomeWork").setTicker("New Messege Alert!")
                .setAutoCancel(true).setSmallIcon(R.drawable.torch).setContentIntent(pendingIntent).build();

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
