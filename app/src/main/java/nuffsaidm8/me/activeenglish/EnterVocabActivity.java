package nuffsaidm8.me.activeenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EnterVocabActivity extends AppCompatActivity {

    DBHelper db;

    private Toast toast;

    private long lastToastSent = 0;
    private boolean lastWasLong = true;

    public void showAToast(String st, boolean isLong) {
        long longLength = 3500;
        long shortLength = 2000;
        if(lastWasLong) {
            if(System.currentTimeMillis() >= lastToastSent + longLength){
                //Can send another
                lastToastSent = System.currentTimeMillis();
                if (isLong) {
                    lastWasLong = true;
                    toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG);
                } else {
                    lastWasLong = false;
                    toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT);
                }
                toast.show();
            } else {
                //Still up
                String text = ((TextView) ((LinearLayout) toast.getView()).getChildAt(0)).getText().toString();
                if (!text.equalsIgnoreCase(st)) {
                    toast.cancel();
                    if(lastWasLong){
                        lastToastSent+= longLength;
                    } else {
                        lastToastSent+= shortLength;
                    }
                    if (isLong) {
                        lastWasLong = true;
                        toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG);
                    } else {
                        lastWasLong = false;
                        toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            }
        } else {
            if(System.currentTimeMillis() >= lastToastSent + shortLength){
                //Can send another
                lastToastSent = System.currentTimeMillis();
                if (isLong) {
                    lastWasLong = true;
                    toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG);
                } else {
                    lastWasLong = false;
                    toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT);
                }
                toast.show();
            } else {
                //Still up
                String text = ((TextView) ((LinearLayout) toast.getView()).getChildAt(0)).getText().toString();
                if (!text.equalsIgnoreCase(st)) {
                    toast.cancel();
                    if(lastWasLong){
                        lastToastSent+= longLength;
                    } else {
                        lastToastSent+= shortLength;
                    }
                    if (isLong) {
                        lastWasLong = true;
                        toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG);
                    } else {
                        lastWasLong = false;
                        toast = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_vocab);
        db = new DBHelper(getApplicationContext());
        toast = new Toast(getApplicationContext());

        EditText wordInput = (EditText) findViewById(R.id.wordBox);
        EditText translationInput = (EditText) findViewById(R.id.translationBox);
        EditText hintInput = (EditText) findViewById(R.id.enterHint);
        Button submitButton = (Button) findViewById(R.id.submit);
        Button home = (Button) findViewById(R.id.homeButtonEnter);


        wordInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(EnterVocabActivity.this)
                        .setTitle("Help - Word Input")
                        .setMessage("Input the word to be translated here.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });


        translationInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(EnterVocabActivity.this)
                        .setTitle("Help - Translation Input")
                        .setMessage("Input the translation of the word here.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });


        hintInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(EnterVocabActivity.this)
                        .setTitle("Help - Hint Input")
                        .setMessage("Input a hint for the word here (not required).")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });


        submitButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(EnterVocabActivity.this)
                        .setTitle("Help - Submit Button")
                        .setMessage("Click to submit the word to the database.")
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
                new AlertDialog.Builder(EnterVocabActivity.this)
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


    }

    public void submitToDatabase(View view) {
        EditText wordInput = (EditText) findViewById(R.id.wordBox);
        EditText translationInput = (EditText) findViewById(R.id.translationBox);
        EditText hintInput = (EditText) findViewById(R.id.enterHint);
        String wordText = wordInput.getText().toString();
        String translationText = translationInput.getText().toString();
        String hintText = hintInput.getText().toString();

        if(wordText.equalsIgnoreCase("") || translationText.equalsIgnoreCase("")){
            //Something was not filled in correctly, so nothing should sumbit.
            showAToast("Word and translation fields are required", true);
            return;
        }

        List<Integer> waits = new ArrayList<>();
        int currentIndex = 1;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        while(sp.contains("DayWait" + currentIndex)){
            waits.add(sp.getInt("DayWait" + currentIndex, 0));
            currentIndex++;
        }

        //Change the number at the end to an array with all the day wait values stored in the sharedPreferences
        int[] waits2 = new int[waits.size()];
        for(int i = 0;i < waits2.length;i++) {
            waits2[i] = waits.get(i);
        }

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, waits2[0]);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long milliseconds = calendar.getTime().getTime();

        final Word currentWord = new Word(wordText, translationText, hintText, milliseconds, waits2);

        if(db.doesWordExist(currentWord)){
            if(db.doesWordExactlyExist(currentWord)){
                showAToast("That exact word already exists", true);
            } else if(sp.getBoolean("showWarning", true)){
                View checkBoxView = View.inflate(this, R.layout.checkbox, null);
                CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sp2.edit().putBoolean("showWarning", false).apply();
                    }
                });
                checkBox.setText(R.string.dontshowagain);
                new AlertDialog.Builder(EnterVocabActivity.this)
                        .setTitle(R.string.warning)
                        .setView(checkBoxView)
                        .setMessage(R.string.proceed)
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                submitToDB(currentWord);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                submitToDB(currentWord);
            }
        } else {
            submitToDB(currentWord);
        }
    }

    public void submitToDB(Word word){
        db.addWord(word);
        showAToast("Word successfully added", false);
        ((EditText) findViewById(R.id.wordBox)).setText("");
        ((EditText) findViewById(R.id.translationBox)).setText("");
        ((EditText) findViewById(R.id.enterHint)).setText("");
    }

    public void home(View view){
        Intent intent = new Intent(getApplicationContext(), LoadupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

}
