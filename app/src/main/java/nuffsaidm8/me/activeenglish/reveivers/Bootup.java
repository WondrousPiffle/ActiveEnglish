package nuffsaidm8.me.activeenglish.reveivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nuffsaidm8.me.activeenglish.DBHelper;
import nuffsaidm8.me.activeenglish.R;

/**
 * Created by Eli on 6/14/2016.
 */
public class Bootup extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Received when the phone boots up
        //Set next date to check for words, if the time is not already past noon. If the time is
        //past noon, send notification and reset the alarm
        DBHelper db = new DBHelper(context);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long milliseconds = calendar.getTime().getTime();

        Intent newIntent = new Intent(context, CheckTime.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(milliseconds < System.currentTimeMillis()){
            //Past noon that day, set alarm to noon tomorrow
            if(db.areWordsToReview(System.currentTimeMillis())){
                //Send notification
                playNotification(context, "Active English", "You have words to review!");
            }
            calendar.add(Calendar.DATE, 1);
        }

        //Set alarm for noon
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTime().getTime(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTime().getTime(), pendingIntent);
        }
    }

    private void playNotification(Context context, String name, String description){
        long[] vibrate = new long[2];
        vibrate[0] = 500;
        vibrate[1] = 1000;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText(description)
                .setSmallIcon(R.drawable.notificationicon)
                .setLights(Color.GREEN, 500, 2000)
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setContentTitle(name);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

}
