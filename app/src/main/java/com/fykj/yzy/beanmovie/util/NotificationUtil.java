package com.fykj.yzy.beanmovie.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by 易镇艺 on 2017/7/29.
 */

public class NotificationUtil {
    private  NotificationManager manager;
    private static NotificationUtil instance;

    private NotificationUtil(Context context){
        manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    };

    public static NotificationUtil getInstance(Context context){
        if (instance==null){
            synchronized (NotificationUtil.class){
                if (instance==null){
                    instance=new NotificationUtil(context);
                }
            }
        }
        return instance;
    }

    public  void showDefuatNotification(Context context,int icon, String contentTitle,String contentText,Intent intent){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(contentTitle)
                .setContentText(contentText);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.build();

        manager.notify(0,notification);


    }


}
