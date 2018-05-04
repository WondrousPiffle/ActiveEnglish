package nuffsaidm8.me.activeenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    Switch useTrans, reset, showWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Typeface settingsFace = Typeface.createFromAsset(this.getAssets(), "fonts/engebrechtrebd.ttf");
        TextView SettingsHeader = (TextView) this.findViewById(R.id.settingsHeader);
        SettingsHeader.setTypeface(settingsFace);

        useTrans = (Switch) findViewById(R.id.checkByTransSwitch);
        reset = (Switch) findViewById(R.id.resetDayWaitSwitch);
        showWarning = (Switch) findViewById(R.id.dontShowSwitch);
        Button toChooserButton = (Button) findViewById(R.id.customizeDayWaits);
        Button home = (Button) findViewById(R.id.homeButtonSettings);

        useTrans.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Help - Use Translation Switch")
                        .setMessage("Turning this on (blue color) will show you the translation of the word instead of the word itself when a word appears in the vocab checking page.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        reset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Help - Reset Day Waits Switch")
                        .setMessage("Turning this on (blue color) will, every time a word is translated incorrectly in the vocab checking page, reset the number of days between each time a word appears to be checked to the first wait amount.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        showWarning.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Help - Show Duplicate Warning Switch")
                        .setMessage("Turning this on (blue color) will alert you every time you submit a word that already has a translation or listing in the database. You can then decide if you want to continue and submit the word.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        toChooserButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Help - Customize Day Waits Button")
                        .setMessage("This will go to the day waits editor, where you can customize the amounts of time inbetween times every word appears in the vocab checking page.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        home.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Help - Home Button")
                        .setMessage("This will take you back to the homepage.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (sp.getBoolean("useTrans", false)) {
            useTrans.setChecked(true);
        }

        if (sp.getBoolean("resetAfterMiss", false)) {
            reset.setChecked(true);
        }

        if(sp.getBoolean("showWarning", true)){
            showWarning.setChecked(true);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        if (useTrans.isChecked()) {
            editor.putBoolean("useTrans", true);
        } else {
            editor.putBoolean("useTrans", false);
        }

        if (reset.isChecked()) {
            editor.putBoolean("resetAfterMiss", true);
        } else {
            editor.putBoolean("resetAfterMiss", false);
        }

        if(showWarning.isChecked()){
            editor.putBoolean("showWarning", true);
        } else {
            editor.putBoolean("showWarning", false);
        }

        editor.apply();

    }

    public void toChoserActivity(View view){
        Intent intent = new Intent(getApplicationContext(), ChooserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    public void home(View view){
        Intent intent = new Intent(getApplicationContext(), LoadupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
