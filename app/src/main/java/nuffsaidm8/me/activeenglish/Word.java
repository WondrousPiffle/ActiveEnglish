package nuffsaidm8.me.activeenglish;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Eli on 3/27/2016.
 */
public class Word implements Comparable<Word>{

    private int[] daywaits;
    private int daywaitindex = 0;

    private String word;
    private String translation;
    private String hint = "";
    private long date;
    private int dayWait;

    public String getHint(){
        return hint;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public long getDate() {
        return date;
    }

    public void setNextDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTimeInMillis() + dayWait * 86400000;
    }

    public void resetDayWait(){
        daywaitindex = 0;
        dayWait = daywaits[0];
    }

    public int getDayWait(){
        return dayWait;
    }

    public void nextDayWait(){
        daywaitindex += 1;
        dayWait = daywaits[daywaitindex];
    }

    public int[] getDayWaitsArray(){
        return daywaits;
    }

    public boolean isLastWait(){
        return daywaitindex == daywaits.length - 1;
    }

    public Word(String word, String translation, String hint, long date, int[] daywaits){
        this.word = word;
        this.translation = translation;
        this.hint = hint;
        this.date = date;
        this.dayWait = daywaits[0];
        this.daywaits = daywaits;
    }

    //NonNull?
    @Override
    public int compareTo(@NonNull Word another) {
        return word.compareTo(another.getWord());
    }
}
