package nuffsaidm8.me.activeenglish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eli on 3/27/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vocab.db";
    public static final String VOCAB_INPUT = "Input_Word";
    public static final String VOCAB_TRANSLATION = "Translation";
    public static final String VOCAB_HINT = "Hint";
    public static final String DATE_TO_REMIND = "Date_To_Remind";
    public static final String TABLE_NAME = "Vocabulary";
    public static final String DAY_WAITS = "Day_Waits";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + VOCAB_INPUT + " TEXT, "
                + VOCAB_TRANSLATION + " TEXT, "
                + VOCAB_HINT + " TEXT, "
                + DATE_TO_REMIND + " INTEGER, "
                + DAY_WAITS + " TEXT"
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addWord(Word word) {
        ContentValues values = new ContentValues();
        values.put(VOCAB_INPUT, word.getWord());
        values.put(VOCAB_TRANSLATION, word.getTranslation());
        values.put(DATE_TO_REMIND, word.getDate());
        values.put(VOCAB_HINT, word.getHint());
        values.put(DAY_WAITS, Arrays.toString(word.getDayWaitsArray()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Word> getWordsToReview(long date) {
        List<Word> returnWords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE_TO_REMIND + " <= '" + date + "'", null);
        while (cursor.moveToNext()) {
            String retrievedName = cursor.getString(cursor.getColumnIndex(VOCAB_INPUT));
            String retrievedTranslation = cursor.getString(cursor.getColumnIndex(VOCAB_TRANSLATION));
            String retrievedHint = cursor.getString(cursor.getColumnIndex(VOCAB_HINT));
            long retrievedDate = cursor.getLong(cursor.getColumnIndex(DATE_TO_REMIND));
            int[] dayWaitsArray = backToArray(cursor.getString(cursor.getColumnIndex(DAY_WAITS)));
            returnWords.add(new Word(retrievedName, retrievedTranslation, retrievedHint, retrievedDate, dayWaitsArray));
        }
        cursor.close();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + DATE_TO_REMIND + " <= '" + date + "'");
        return returnWords;
    }

    public boolean areWordsToReview(long date) {
        List<Word> returnWords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE_TO_REMIND + " <= '" + date + "'", null);
        while (cursor.moveToNext()) {
            String retrievedName = cursor.getString(cursor.getColumnIndex(VOCAB_INPUT));
            String retrievedTranslation = cursor.getString(cursor.getColumnIndex(VOCAB_TRANSLATION));
            String retrievedHint = cursor.getString(cursor.getColumnIndex(VOCAB_HINT));
            long retrievedDate = cursor.getLong(cursor.getColumnIndex(DATE_TO_REMIND));
            int[] dayWaitsArray = backToArray(cursor.getString(cursor.getColumnIndex(DAY_WAITS)));
            returnWords.add(new Word(retrievedName, retrievedTranslation, retrievedHint, retrievedDate, dayWaitsArray));
        }
        cursor.close();
        return !returnWords.isEmpty();
    }

    public boolean doesWordExist(Word word) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + VOCAB_INPUT + " = '" + word.getWord() + "' OR " + VOCAB_INPUT + " = '" + word.getTranslation() + "'", null);
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + VOCAB_TRANSLATION + " = '" + word.getWord() + "' OR " + VOCAB_TRANSLATION + " = '" + word.getTranslation() + "'", null);
        boolean check = false;
        if (cursor.moveToNext() || cursor2.moveToNext()) {
            check = true;
        }
        cursor.close();
        cursor2.close();
        return check;
    }

    public boolean doesWordExactlyExist(Word word) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE (" + VOCAB_INPUT + " = '" + word.getWord() + "' AND " + VOCAB_TRANSLATION + " = '" + word.getTranslation() + "') OR (" + VOCAB_INPUT + " = '" + word.getTranslation() + "' AND " + VOCAB_TRANSLATION + " = '" + word.getWord() + "')", null);
        boolean check = false;
        if (cursor.moveToNext()) {
            check = true;
        }
        cursor.close();
        return check;
    }

    public List<Word> getAllWords() {
        List<Word> returnWords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String retrievedName = cursor.getString(cursor.getColumnIndex(VOCAB_INPUT));
            String retrievedTranslation = cursor.getString(cursor.getColumnIndex(VOCAB_TRANSLATION));
            String retrievedHint = cursor.getString(cursor.getColumnIndex(VOCAB_HINT));
            long retrievedDate = cursor.getLong(cursor.getColumnIndex(DATE_TO_REMIND));
            int[] dayWaitsArray = backToArray(cursor.getString(cursor.getColumnIndex(DAY_WAITS)));
            returnWords.add(new Word(retrievedName, retrievedTranslation, retrievedHint, retrievedDate, dayWaitsArray));
        }
        cursor.close();
        return returnWords;
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + VOCAB_INPUT + " = '" + word.getWord() + "' AND " + VOCAB_TRANSLATION + " = '" + word.getTranslation() + "'");
    }

    private int[] backToArray(String stringForm) {
        stringForm = stringForm.replace("[", "");
        stringForm = stringForm.replace("]", "");
        String[] s = stringForm.split(", ");
        int[] numbers = new int[s.length];
        for (int curr = 0; curr < s.length; curr++) {
            numbers[curr] = Integer.parseInt(s[curr]);
        }
        return numbers;
    }
}
