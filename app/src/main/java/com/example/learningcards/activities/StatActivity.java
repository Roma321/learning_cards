package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.learningcards.R;
import com.example.learningcards.classes.MyDBHelper;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        MyDBHelper helper = new MyDBHelper(this);
        Cursor cursor = helper.getAllStat();
        cursor.moveToFirst();

        int enTries = cursor.getInt(cursor.getColumnIndex(MyDBHelper.CARD_TRIES_EN));
        int cinTries = cursor.getInt(cursor.getColumnIndex(MyDBHelper.CARD_TRIES_CIN));
        int totalTries = enTries + cinTries;

        int enLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.LEARNED_EN));
        int cinLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.LEARNED_CIN));
        int totalLearned = enLearned + cinLearned;

        int enHalfLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.HALF_LEARNED_EN));
        int cinHalfLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.HALF_LEARNED_CIN));
        int totalHalfLearned = enHalfLearned + cinHalfLearned;

        int enNotLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.NOT_LEARNED_EN));
        int cinNotLearned = cursor.getInt(cursor.getColumnIndex(MyDBHelper.NOT_LEARNED_CIN));
        int totalNotLearned = enNotLearned + cinNotLearned;

        String lastWord = helper.getLastWord();

        int enWords = enHalfLearned + enLearned + enNotLearned;
        int cinWords = cinHalfLearned + cinLearned + cinNotLearned;

        ((TextView) findViewById(R.id.totalCardTriesTv)).setText(Integer.toString(totalTries));
        ((TextView) findViewById(R.id.chineseCardTriesTv)).setText(Integer.toString(cinTries));
        ((TextView) findViewById(R.id.englishCardTriesTv)).setText(Integer.toString(enTries));

        ((TextView) findViewById(R.id.totalWordsTv)).setText(Integer.toString(enWords + cinWords));
        ((TextView) findViewById(R.id.englishWordsTv)).setText(Integer.toString(enWords));
        ((TextView) findViewById(R.id.chineseWordsTv)).setText(Integer.toString(cinWords));

        ((TextView) findViewById(R.id.totalLearnedTv)).setText(Integer.toString(totalLearned));
        ((TextView) findViewById(R.id.englishLearnedTv)).setText(Integer.toString(enLearned));
        ((TextView) findViewById(R.id.chineseLearnedTv)).setText(Integer.toString(cinLearned));

        ((TextView) findViewById(R.id.totalHalfLearnedTv)).setText(Integer.toString(totalHalfLearned));
        ((TextView) findViewById(R.id.chineseHalfLearnedTv)).setText(Integer.toString(cinHalfLearned));
        ((TextView) findViewById(R.id.englishHalfLearnedTv)).setText(Integer.toString(enHalfLearned));

        ((TextView) findViewById(R.id.totalNotLearnedTv)).setText(Integer.toString(totalNotLearned));
        ((TextView) findViewById(R.id.chineseNotLearnedTv)).setText(Integer.toString(cinNotLearned));
        ((TextView) findViewById(R.id.englishNotLearnedTv)).setText(Integer.toString(enNotLearned));

        ((TextView) findViewById(R.id.lastWordTvInStat)).setText(lastWord);


    }

}