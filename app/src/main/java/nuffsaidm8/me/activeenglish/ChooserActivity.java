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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.TreeMap;

public class ChooserActivity extends AppCompatActivity {

    //Day wait is the key, total number of days to wait is the value.
    private Map<Integer, Integer> dayWaitsMap = new TreeMap<>();
    int currentDayWait = 1;

    EditText daysInput;
    EditText weeksInput;
    EditText monthsInput;
    EditText yearsInput;
    TextView waitNum;
    TextView daysLabel;
    TextView weeksLabel;
    TextView monthsLabel;
    TextView yearsLabel;
    Button homeButtonChooser;

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
        setContentView(R.layout.activity_chooser);

        toast = new Toast(getApplicationContext());

        //Set press and hold capabilities to show function.

        waitNum = (TextView) findViewById(R.id.waitNum);
        daysLabel = (TextView) findViewById(R.id.DaysLabel);
        weeksLabel = (TextView) findViewById(R.id.WeeksLabel);
        monthsLabel = (TextView) findViewById(R.id.MonthsLabel);
        yearsLabel = (TextView) findViewById(R.id.YearsLabel);
        homeButtonChooser = (Button) findViewById(R.id.homeButtonChooser);
        ImageButton check = (ImageButton) findViewById(R.id.doneButton);
        ImageButton trash = (ImageButton) findViewById(R.id.trashButton);
        ImageButton cancel = (ImageButton) findViewById(R.id.cancelbutton);
        ImageButton rightArrow = (ImageButton) findViewById(R.id.rightArrow);
        ImageButton leftArrow = (ImageButton) findViewById(R.id.leftArrow);
        ImageButton newPageButton = (ImageButton) findViewById(R.id.newPageButton);
        daysInput = (EditText) findViewById(R.id.daysInput);
        weeksInput = (EditText) findViewById(R.id.weeksInput);
        monthsInput = (EditText) findViewById(R.id.monthsInput);
        yearsInput = (EditText) findViewById(R.id.yearsInput);

        homeButtonChooser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Home Button")
                        .setMessage("This will take you back to the homepage. It will not save the waits open in the editor.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        waitNum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Day Wait Number")
                        .setMessage("This is the current wait that you are editing. A wait is the amount of days, weeks, months and years between one time a word appears in the vocab checking activity and the next time it will appear. Each time a word is checked, the wait following the previously used wait will be used.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        weeksLabel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Weeks")
                        .setMessage("This is the number of weeks for the current wait.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        daysLabel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Days")
                        .setMessage("This is the number of days for the current wait.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        monthsLabel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Months")
                        .setMessage("This is the number of months for the current wait. All months are treated as 31 days.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        yearsLabel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Years")
                        .setMessage("This is the number of years for the current wait. All years are treated as 365 days.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        check.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Done Button")
                        .setMessage("This will close the editor and save your waits. The new waits will override your previous waits, but only for all words created from then on, not words already in the database.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        trash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Trash Button")
                        .setMessage("This will delete the wait currently displayed in the editor. All subsequent waits will be moved up.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        cancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Cancel Button")
                        .setMessage("This will close the editor and NOT save your new waits. The old waits will NOT be overridden.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        rightArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Move Right Button")
                        .setMessage("This will move to the next wait, provided one exists.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        leftArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - Move Left Button")
                        .setMessage("This will move to the previous wait, provided one exists.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        newPageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ChooserActivity.this)
                        .setTitle("Help - New Wait Button")
                        .setMessage("This will create a new wait directly after the wait currently displayed in the editor. All subsequent waits will be moved back.")
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
        SharedPreferences.Editor edit = sp.edit();

        //Used to iterate through all stored daywaits.
        int currentIndex = 1;
        //Used to track how many are stored, then displayed to user.
        int count = 0;
        while (sp.contains("DayWait" + currentIndex)) {
            dayWaitsMap.put(currentIndex, sp.getInt("DayWait" + currentIndex, 0));
            currentIndex++;
            count++;
        }

        if (count > 1) {
            showAToast(count + " waits retrieved from storage", true);
        } else if (count == 1) {
            showAToast(count + " wait retrieved from storage", true);
        } else {
            showAToast("No waits found in storage", true);
        }

        //Put the first daywaits data into the blanks (If applicable).

        if (dayWaitsMap.containsKey(1)) {
            int totalDays = dayWaitsMap.get(1);
            int years = totalDays / 365;
            int months = totalDays % 365 / 31;
            int weeks = totalDays % 365 % 30 / 7;
            int days = totalDays % 365 % 30 % 7;
            daysInput.setText(String.valueOf(days));
            weeksInput.setText(String.valueOf(weeks));
            monthsInput.setText(String.valueOf(months));
            yearsInput.setText(String.valueOf(years));
        } else {
            daysInput.setText("0");
            weeksInput.setText("0");
            monthsInput.setText("0");
            yearsInput.setText("0");
        }
        edit.apply();
    }

    public void done(View view) {
        //Retrieve shared preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();

        int totalDays = Integer.parseInt(daysInput.getText().toString()) + Integer.parseInt(weeksInput.getText().toString()) * 7 + Integer.parseInt(monthsInput.getText().toString()) * 31 + Integer.parseInt(yearsInput.getText().toString()) * 365;
        dayWaitsMap.put(currentDayWait, totalDays);

        //Used to iterate through all stored daywaits.
        int currentIndex = 1;
        while (sp.contains("DayWait" + currentIndex)) {
            edit.remove("DayWait" + currentIndex);
            currentIndex++;
        }

        //Used to keep track of how many waits are being put into storage.
        int count = 0;
        //Input new data
        for (int key : dayWaitsMap.keySet()) {
            //Results in every listing as DayWait#
            count++;
            edit.putInt("DayWait" + key, dayWaitsMap.get(key));
        }

        //Move back to settings activity
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);

        if (count != 1) {
            showAToast(count + " waits saved", false);
        } else {
            showAToast(count + " wait saved", false);
        }

        edit.apply();
    }

    public void leftArrow(View view) {
        //Store the data then clear the fields and update the top label.
        //Note: If there is already data in the hashmap for whichever day wait you go back to, put that info in the blanks.
        //THIS IS GOING BACK, SO I MUST CHECK IF THERE IS ALREADY DATA FOR THIS DAYWAIT IN THE HASHMAP

        //First check that all blanks are filled, otherwise deny the action.
        //Remember to converteverything to days before storing things in the hashmap.

        if (currentDayWait <= 1) {
            //First possible daywait, can't go back further

            showAToast("Cannot go back further", false);

        } else if (daysInput.getText().toString().equals("") || weeksInput.getText().toString().equals("") || yearsInput.getText().toString().equals("")) {
            //Not all fields have a value

            showAToast("Please fill in all text fields", false);

        } else {
            //Valid to move back, get the preivous daywait and its corrisponding days.

            int totalDays = Integer.parseInt(daysInput.getText().toString()) + Integer.parseInt(weeksInput.getText().toString()) * 7 + Integer.parseInt(monthsInput.getText().toString()) * 31 + Integer.parseInt(yearsInput.getText().toString()) * 365;
            dayWaitsMap.put(currentDayWait, totalDays);
            currentDayWait--;

            waitNum.setText(String.format("%s%d", getString(R.string.waitnum), currentDayWait));
            daysInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 % 7));
            weeksInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 / 7));
            monthsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 / 31));
            yearsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) / 365));

        }

    }

    public void rightArrow(View view) {
        //Store the data then clear the fields and update the top label.
        //Note: If there is already data in the hashmap for whichever day wait is up next (i.e. they went back to change something then are moving back up now), put that info in the blanks.
        //MAKE SURE TO CHECK THE HASHMAP FOR DATA

        //First check that all blanks are filled, otherwise deny the action.
        //Remember to converteverything to days before storing things in the hashmap.
        int temp = currentDayWait + 1;
        if (!dayWaitsMap.containsKey(temp)) {
            showAToast("No other waits created", false);
        } else if (daysInput.getText().toString().equals("") || weeksInput.getText().toString().equals("") || yearsInput.getText().toString().equals("")) {
            //Not all fields have a value
            showAToast("Please fill in all text fields", false);
        } else {
            //Valid to move forward, get the next daywait and its data if applicable.
            int totalDays = Integer.parseInt(daysInput.getText().toString()) + Integer.parseInt(weeksInput.getText().toString()) * 7 + Integer.parseInt(monthsInput.getText().toString()) * 31 + Integer.parseInt(yearsInput.getText().toString()) * 365;
            dayWaitsMap.put(currentDayWait, totalDays);
            currentDayWait++;
            daysInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 % 7));
            weeksInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 / 7));
            monthsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 / 31));
            yearsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) / 365));
            waitNum.setText(String.format("%s%d", getString(R.string.waitnum), currentDayWait));
        }

    }

    public void cancel(View view) {
        //Back to settings, no need to save data
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        showAToast("Nothing was changed", false);
    }

    public void trash(View view) {

        new AlertDialog.Builder(ChooserActivity.this)
                .setTitle("Are you sure you want to delete this?")
                .setMessage("There is no way to undo this action.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do Stuff

                        if (dayWaitsMap.containsKey(currentDayWait)) {
                            //Found it, deleting
                            if (dayWaitsMap.keySet().size() <= 1) {
                                showAToast("Cannot delete, no other waits", false);
                                return;
                            }
                            dayWaitsMap.remove(currentDayWait);
                            Map<Integer, Integer> temp = new TreeMap<>();
                            for (int key : dayWaitsMap.keySet()) {
                                if (key > currentDayWait) {
                                    temp.put(key - 1, dayWaitsMap.get(key));
                                } else {
                                    temp.put(key, dayWaitsMap.get(key));
                                }
                            }
                            dayWaitsMap = temp;
                            if (dayWaitsMap.containsKey(currentDayWait)) {
                                //More after the deleted one
                                waitNum.setText(String.format("%s%d", getString(R.string.waitnum), currentDayWait));
                                daysInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 % 7));
                                weeksInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 / 7));
                                monthsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 / 31));
                                yearsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) / 365));
                            } else {
                                //No more after the deleted one, go back to the previous one
                                currentDayWait--;
                                waitNum.setText(String.format("%s%d", getString(R.string.waitnum), currentDayWait));
                                daysInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 % 7));
                                weeksInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 % 31 / 7));
                                monthsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) % 365 / 31));
                                yearsInput.setText(String.valueOf(dayWaitsMap.get(currentDayWait) / 365));
                            }
                            showAToast("Successfully deleted", false);
                        } else {
                            //??? Wut Happnd?
                            showAToast("Something went wrong", false);
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void newPage(View view) {

        //Needs testing out

        currentDayWait++;
        Map<Integer, Integer> temp = new TreeMap<>();
        for (int key : dayWaitsMap.keySet()) {
            if (key >= currentDayWait) {
                temp.put(key + 1, dayWaitsMap.get(key));
            } else {
                temp.put(key, dayWaitsMap.get(key));
            }
        }
        dayWaitsMap = temp;
        dayWaitsMap.put(currentDayWait, 0);
        daysInput.setText("0");
        weeksInput.setText("0");
        monthsInput.setText("0");
        yearsInput.setText("0");
        waitNum.setText(String.format("%s%d", getString(R.string.waitnum), currentDayWait));
        showAToast("New wait created", false);
    }

    public void home(View view) {
        Intent intent = new Intent(getApplicationContext(), LoadupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        showAToast("Nothing was changed", false);
    }

}
