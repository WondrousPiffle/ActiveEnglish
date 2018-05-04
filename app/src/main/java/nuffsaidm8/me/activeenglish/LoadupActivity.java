package nuffsaidm8.me.activeenglish;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nuffsaidm8.me.activeenglish.reveivers.CheckTime;

public class LoadupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadup);

        Typeface welcomeFace = Typeface.createFromAsset(this.getAssets(), "fonts/engebrechtrebd.ttf");
        TextView welcomeView = (TextView) this.findViewById(R.id.welcome);
        TextView selectView = (TextView) this.findViewById(R.id.selectWhatYouWantToDo);
        selectView.setTypeface(welcomeFace);
        welcomeView.setTypeface(welcomeFace);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sp.getBoolean("openedBefore", false)){
            //Never opened yet

            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DATE, 1);
            long milliseconds = calendar.getTime().getTime();

            Intent newIntent = new Intent(getApplicationContext(), CheckTime.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
            }

            sp.edit().putBoolean("openedBefore", true).apply();


            //ALL DEFAULTS, ONLY APPLIED ON FIRST LOADUP OF THE APP


            //One day
            sp.edit().putInt("DayWait1", 1).apply();
            //Two days
            sp.edit().putInt("DayWait2", 2).apply();
            //One week
            sp.edit().putInt("DayWait3", 7).apply();
            //Two weeks
            sp.edit().putInt("DayWait4", 14).apply();
            //Three weeks
            sp.edit().putInt("DayWait5", 21).apply();
            //One month(ish)
            sp.edit().putInt("DayWait6", 31).apply();
            //Two months
            sp.edit().putInt("DayWait7", 62).apply();
            //6 months
            sp.edit().putInt("DayWait8", 186).apply();
            //One year
            sp.edit().putInt("DayWait9", 365).apply();
            //Two years
            sp.edit().putInt("DayWait10", 730).apply();


        }

        Button enterVocab = (Button) this.findViewById(R.id.enterVocab);
        Button checkVocab = (Button) this.findViewById(R.id.checkVocab);
        Button settingsButton = (Button) this.findViewById(R.id.toSettingsButton);
        Button searchButton = (Button) this.findViewById(R.id.goToSearchWords);

        enterVocab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LoadupActivity.this)
                        .setTitle("Help - To Enter Vocab Page Button")
                        .setMessage("This will take you to the vocab entry page, where you can enter new words to be stored in the database and repeatedly given to you to help you memorize it.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        checkVocab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LoadupActivity.this)
                        .setTitle("Help - To Check Vocab Page Button")
                        .setMessage("This will take you to the vocab checking page, where you can test yourself on any word in the database that is scheduled to appear.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        settingsButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LoadupActivity.this)
                        .setTitle("Help - To Settings Page Button")
                        .setMessage("This will take you to the settings page, where you are able to adjust the app's settings.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        searchButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(LoadupActivity.this)
                        .setTitle("Help - To Search Page Button")
                        .setMessage("This will take you to the search page, where you are able to search words in the database and edit them.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        enterVocab.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnterVocabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        checkVocab.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckVocabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    public void goToSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchWord.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

}
