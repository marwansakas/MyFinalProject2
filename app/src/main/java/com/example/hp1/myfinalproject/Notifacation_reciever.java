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

        Intent homeworkIntent=new Intent(context,HomeWork.class);//set the intent

        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);//set stackBuilder
        stackBuilder.addParentStack(HomeWork.class);//addParent Stack
        stackBuilder.addNextIntent(homeworkIntent);//add where the notification should go to

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(NOTIFICATION_CODE,PendingIntent.FLAG_UPDATE_CURRENT);//fet the pending intent

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);//build the otificationCompatBuilder

        Notification notification=builder.setContentTitle("You Have HomeWork")//set the notification title
                .setContentText("You Have HomeWork").setTicker("New Message Alert!")//set the notification message
                .setAutoCancel(true)//set it as cancable notification
                .setSmallIcon(R.drawable.torch)//set the icon for the notification
                .setContentIntent(pendingIntent).build();//set where the notifiction leads you to

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
