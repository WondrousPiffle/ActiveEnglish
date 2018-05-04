package nuffsaidm8.me.activeenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SearchWord extends AppCompatActivity {

    ImageButton search;
    EditText wordShower;
    EditText translationShower;
    EditText hintShower;
    TextView dateDisplay;
    AutoCompleteTextView searchInput;
    DBHelper db;
    Word currentWord;
    Word currentWordCopy;
    ArrayAdapter<String> adapter;
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
    protected void onStop() {
        super.onStop();
        if (currentWord != null) {
            db.addWord(currentWordCopy);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        toast = new Toast(getApplicationContext());

        search = (ImageButton) this.findViewById(R.id.search);
        View root = search.getRootView();
        root.setBackgroundColor(Color.rgb(0, 0, 255));
        ImageButton trash = (ImageButton) this.findViewById(R.id.trashButtonSearch);
        Button home = (Button) this.findViewById(R.id.homeButtonSearch);
        Button submit = (Button) this.findViewById(R.id.submitchanges);
        Button resetDatesButton = (Button) this.findViewById(R.id.resetDatesButton);
        wordShower = (EditText) this.findViewById(R.id.showWord);
        translationShower = (EditText) this.findViewById(R.id.showtrans);
        hintShower = (EditText) this.findViewById(R.id.showhint);
        searchInput = (AutoCompleteTextView) this.findViewById(R.id.searchinput);
        dateDisplay = (TextView) this.findViewById(R.id.nextDateDisplay);

        search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Search Button")
                        .setMessage("This will search the database for the requested word (based off of the input word or translation) and bring it up if it exists.")
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
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Trash Button")
                        .setMessage("This will remove the word brought up in the editor from the database only if all of the data matches a word in the database.")
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
                new AlertDialog.Builder(SearchWord.this)
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

        submit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Submit Button")
                        .setMessage("This will add the word to the database, overwriting the old word, but only if either the word or translation matches an existing word.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        resetDatesButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Reset Dates Button")
                        .setMessage("This will reset the waits between checking the word back to the first wait. The word must be resumbited for this change to save.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        wordShower.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Word Display")
                        .setMessage("This displays the searched word, it can be edited. The word must be resumbited for this change to save.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        translationShower.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Translation Display")
                        .setMessage("This displays the translation of the searched word, it can be edited. The word must be resumbited for this change to save.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        hintShower.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Hint Display")
                        .setMessage("This displays the hint (if one exists) for the searched word, it can be edited or left blank. The word must be resumbited for this change to save.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        searchInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Seaerch Bar")
                        .setMessage("Input a word or translation of a word here, then press the search button to bring up the word. Suggestions of words in the database you might be looking for are provided.")
                        .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        dateDisplay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(SearchWord.this)
                        .setTitle("Help - Date Display")
                        .setMessage("This displays the next date the word will be retrieved for checking.")
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

        List<Word> allWords = db.getAllWords();
        List<String> words = new ArrayList<>();
        for (Word word : allWords) {
            words.add(word.getWord() + "\n   --> " + word.getTranslation());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        searchInput.setAdapter(adapter);
    }

    public void searchForWord(View view) {
        for (Word word : db.getAllWords()) {
            /* THIS CHECKING SYSYTEM NEEDS TO BE INFINITELY BETTER*/
            if(currentWord != null && (currentWord.getWord().equals(searchInput.getText().toString()) || currentWord.getTranslation().equals(searchInput.getText().toString()))) {
                //Just a check to see if the word is being edited so that the correct toast message can be shown to the user.
                showAToast("This word is already in the editor", true);
                return;
            } else if (word.getWord().equalsIgnoreCase(searchInput.getText().toString()) || word.getTranslation().equalsIgnoreCase(searchInput.getText().toString())) {
                //Found the word in the database.
                if(currentWord != null){
                    db.addWord(currentWordCopy);
                    adapter.add(currentWordCopy.getWord());
                    adapter.add(currentWordCopy.getTranslation());
                    currentWord = null;
                    currentWordCopy = null;
                }
                db.deleteWord(word);
                //Remember to fix when new adapter system is in place
                adapter.remove(word.getWord());
                adapter.remove(word.getTranslation());
                wordShower.setText(word.getWord());
                translationShower.setText(word.getTranslation());
                hintShower.setText(word.getHint());
                searchInput.setText("");
                dateDisplay.setText(String.valueOf(word.getDate()));
                currentWord = word;
                currentWordCopy = new Word(word.getWord(), word.getTranslation(), word.getHint(), word.getDate(), word.getDayWaitsArray());
                return;
            }
        }
        showAToast("This word is not in the database", false);
    }

    public void submitChanges(View view) {
        if (currentWord == null) {
            showAToast("No word to update", false);
            return;
        }

        if (wordShower.getText().toString().equals("") || translationShower.getText().toString().equals("")) {
            showAToast("A word and a translation are both required", true);
            return;
        }

        Word newword = new Word(wordShower.getText().toString(), translationShower.getText().toString(), hintShower.getText().toString(), currentWord.getDate(), currentWord.getDayWaitsArray());
        db.addWord(newword);
        adapter.add(newword.getWord());
        adapter.add(newword.getTranslation());
        showAToast("Word successfully updated", false);
        wordShower.setText("");
        translationShower.setText("");
        hintShower.setText("");
        dateDisplay.setText("N/A");
        currentWord = null;
        currentWordCopy = null;
    }

    public void resetDates(View view) {
        if (currentWord != null) {
            try {
                currentWord.resetDayWait();
                currentWord.setNextDate();
                Date date = new Date(currentWord.getDate());
                dateDisplay.setText(date.toString());
                showAToast("Checking dates reset", false);
            } catch (Exception e) {
                showAToast("Something went wrong", false);
            }
        } else {
            showAToast("Search a word first", false);
        }

    }

    public void home(View view) {
        //To loadup activity
        Intent intent = new Intent(getApplicationContext(), LoadupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    public void trash(View view) {

        new AlertDialog.Builder(SearchWord.this)
                .setTitle("Are you sure you want to delete this?")
                .setMessage("There is no way to undo this action.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do Stuff
                        if (currentWord != null){
                            currentWord = null;
                            currentWordCopy = null;
                            wordShower.setText("");
                            translationShower.setText("");
                            hintShower.setText("");
                            searchInput.setText("");
                            dateDisplay.setText("N/A");
                            showAToast("Successfully deleted", false);
                        } else {
                            showAToast("No word to trash", false);
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
}
