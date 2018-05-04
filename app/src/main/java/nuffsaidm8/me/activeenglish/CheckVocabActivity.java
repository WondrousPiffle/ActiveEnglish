package nuffsaidm8.me.activeenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckVocabActivity extends AppCompatActivity {

    DBHelper db;
    Word currentWord = null;
    List<Word> wordsToReview;
    List<Word> checked = new ArrayList<>();
    boolean useTrans = false;
    boolean resetAfterMiss = false;
    boolean hintOn = false;
    TextView currentWordDisplay;
    TextView hintDisplay;
    EditText translationField;

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
    protected void onStop(){
        super.onStop();
        //Add stuff back to the database
        for(Word word : wordsToReview){
            db.addWord(word);
        }
        for(Word word : checked){
            db.addWord(word);
        }
        if(currentWord != null){
            db.addWord(currentWord);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_vocab);

        toast = new Toast(getApplicationContext());

        translationField = (EditText) this.findViewById(R.id.translationfield);
        currentWordDisplay = (TextView) this.findViewById(R.id.currentWordDisplay);
        hintDisplay = (TextView) this.findViewById(R.id.hintShower);

        Button showHintBtn = (Button) findViewById(R.id.showHintBtn);
        Button homeButtonCheck = (Button) findViewById(R.id.homeButtonCheck);
        Button check = (Button) findViewById(R.id.check);
        Button skipBtn = (Button) findViewById(R.id.skipBtn);

        translationField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Translation Field")
                        .setMessage("Input the translation of the displayed word here.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        currentWordDisplay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Current Word Display")
                        .setMessage("This is the word you are translating.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        hintDisplay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Hint Display")
                        .setMessage("This is where the hint will be displayed if you choose to toggle the hint on.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        showHintBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Hint Toggle Button")
                        .setMessage("This toggles the hint on and off.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        homeButtonCheck.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
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

        check.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Check Button")
                        .setMessage("This will check your translation against the word's actual translation, then bring up a new word.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        skipBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CheckVocabActivity.this)
                        .setTitle("Help - Skip Button")
                        .setMessage("This will skip the current word and move on to the next word. The skipped word will appear again after all other words have been skiped or checked.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        db = new DBHelper(getApplicationContext());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sp.getBoolean("useTrans", false)){
            useTrans = false;
        } else {
            useTrans = true;
        }

        if(!sp.getBoolean("resetAfterMiss", false)){
            resetAfterMiss = false;
        } else {
            resetAfterMiss = true;
        }

        wordsToReview = db.getWordsToReview(System.currentTimeMillis());
        Collections.sort(wordsToReview);

        if(wordsToReview.isEmpty()){
            //No words to review, nothing needs to be done
            showAToast("No new words to review", true);
            currentWordDisplay.setText("");
            hintDisplay.setText("N/A");
        } else {
            //Update the currentWordDisplay
            if(useTrans){
                currentWordDisplay.setText(wordsToReview.get(0).getTranslation());
            } else {
                currentWordDisplay.setText(wordsToReview.get(0).getWord());
            }

            currentWord = wordsToReview.get(0);
            wordsToReview.remove(0);
            if(!currentWord.getHint().equals("")){
                hintDisplay.setText(R.string.Hint);
            } else {
                hintDisplay.setText(R.string.NoHint);
            }
        }

    }

    public void checkAnswerClicked(View view) {

        //Make sure there is something to check
        if(currentWordDisplay.getText().toString().equals("")){
            showAToast("No words to review", true);
            return;
        }

        //Check word and adjust the word's settings
        if(useTrans){
            if (translationField.getText().toString().equalsIgnoreCase(currentWord.getWord())) {
                showAToast("Correct", false);
                if (!currentWord.isLastWait()) {
                    currentWord.nextDayWait();
                    currentWord.setNextDate();
                    checked.add(currentWord);
                } else {
                    //Word is done
                    showAToast("You've finished this word", false);
                    showAToast("This word has been removed from the database",true);
                }
            } else {
                showAToast("Incorrect. The correct translation is:", false);
                showAToast(currentWord.getWord(), false);
                if(resetAfterMiss){
                    currentWord.resetDayWait();
                }
                currentWord.setNextDate();
                checked.add(currentWord);
            }
        } else {
            if (translationField.getText().toString().equalsIgnoreCase(currentWord.getTranslation())) {
                showAToast("Correct", false);
                if (!currentWord.isLastWait()) {
                    currentWord.nextDayWait();
                    currentWord.setNextDate();
                    checked.add(currentWord);
                } else {
                    //Word is done
                    showAToast("You've finished this word", false);
                    showAToast("This word has been removed from the database", true);
                }
            } else {
                showAToast("Incorrect. The correct translation is:", false);
                showAToast(currentWord.getWord(), false);
                if(resetAfterMiss){
                    currentWord.resetDayWait();
                }
                currentWord.setNextDate();
                checked.add(currentWord);
            }
        }

        //Set up the next word if available
        if(wordsToReview.isEmpty()){
            showAToast("No more words to review", true);
            currentWord = null;
            currentWordDisplay.setText("");
            hintDisplay.setText("");
        } else {
            if(useTrans){
                currentWordDisplay.setText(wordsToReview.get(0).getTranslation());
            } else {
                currentWordDisplay.setText(wordsToReview.get(0).getWord());
            }
            currentWord = wordsToReview.get(0);
            wordsToReview.remove(0);
            if(currentWord.getHint().equals("")){
                hintDisplay.setText(R.string.NoHint);
            } else {
                hintDisplay.setText(R.string.Hint);
            }
        }

        translationField.setText("");
    }

    public void showHint(View view){
        if(currentWord != null && !currentWord.getHint().equals("")){
            if(!hintOn){
                hintDisplay.setText(currentWord.getHint());
                hintOn = true;
            } else {
                hintDisplay.setText(R.string.Hint);
                hintOn = false;
            }
        } else {
            showAToast("This word does not have a hint", false);
        }
    }

    public void skip(View view) {
        if(wordsToReview.size() == 0){
            showAToast("No other words to check", false);
            return;
        }
        wordsToReview.add(currentWord);
        currentWord = wordsToReview.get(0);
        wordsToReview.remove(0);
        if(useTrans){
            currentWordDisplay.setText(currentWord.getTranslation());
        } else {
            currentWordDisplay.setText(currentWord.getWord());
        }
        if(!currentWord.getHint().equals("")){
            hintDisplay.setText(R.string.Hint);
        } else {
            hintDisplay.setText(R.string.NoHint);
        }
    }

    public void home(View view){
        Intent intent = new Intent(getApplicationContext(), LoadupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

}
