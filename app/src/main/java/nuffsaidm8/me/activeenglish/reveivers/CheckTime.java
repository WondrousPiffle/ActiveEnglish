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
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nuffsaidm8.me.activeenglish.DBHelper;
import nuffsaidm8.me.activeenglish.R;

/**
 * Created by Eli on 6/14/2016.
 */
public class CheckTime extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Eventually received when it is time for a notification
        DBHelper db = new DBHelper(context);
        //reset alarm for 24 hours

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        long milliseconds = calendar.getTime().getTime();

        if(db.areWordsToReview(System.currentTimeMillis())){
            //Send notification
            playNotification(context, "Active English", "You have words to review!");
        }

        Intent newIntent = new Intent(context, CheckTime.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
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
