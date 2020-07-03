package com.example.root.sub_katalog_dedi.setting.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import android.util.Log;

import com.example.root.sub_katalog_dedi.R;

import com.example.root.sub_katalog_dedi.model.ResultsItem;


import java.util.Calendar;

import java.util.List;



import static com.example.root.sub_katalog_dedi.activity.Detail_Main.EXTRA_PERSON;

import static com.example.root.sub_katalog_dedi.setting.notification.MovieDailyReceiver.NOTIFICATION_ID;

public class MovieReleaseReceiver extends BroadcastReceiver{
    private static int notifId = 1000;
    @Override
    public void onReceive(Context context, Intent intent) {
        String Title = intent.getStringExtra("notivtitle");
        int id = intent.getIntExtra("notivid", 0);
        String img = intent.getStringExtra("notivimg_poster");
        String date = intent.getStringExtra("notivdate");
        String ovr = intent.getStringExtra("notivoverview");
        ResultsItem movieResult = new ResultsItem(id,ovr,img,date,Title );
        String desc =String.valueOf(String.format(context.getString(R.string.release_today_msg), Title));
        sendNotification(context, context.getString(R.string.app_name), desc, id, movieResult);
    }

    private void sendNotification(Context context, String title, String desc, int id, ResultsItem movieResult) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        intent.putExtra(EXTRA_PERSON, movieResult);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        notificationManager.notify(id, builder.build());
    }

    public void setAlarm(Context context, List<ResultsItem> movieResults) {
        int delay = 0;

        for (ResultsItem movie : movieResults) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieReleaseReceiver.class);
            intent.putExtra("notivtitle", movie.getTitle());
            intent.putExtra("notivid", movie.getId());
            intent.putExtra("notivimg_poster", movie.getPosterPath());
            intent.putExtra("notivdate", movie.getReleaseDate());
            intent.putExtra("notivoverview", movie.getOverview());
            intent.putExtra("id", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            notifId += 1;
            delay += 3000;
            Log.v("title", movie.getTitle());
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieDailyReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
