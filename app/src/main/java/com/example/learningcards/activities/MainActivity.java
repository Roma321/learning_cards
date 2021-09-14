package com.example.learningcards.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.learningcards.classes.MyDBHelper;
import com.example.learningcards.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    MyDBHelper helper;
    SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();

        /*Intent notifyIntent = new Intent(this, MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);*/


        /*for (int i=0;i<100;i++){
            String str=Integer.toString(i);
            Log.e("now:", str);
            helper.insert(new Word (str,str,"rus"+str,str,str,str,"chinese"+str,str, "pin"+str, str));
        }
        List<Word> list = helper.getRandomList(99,MyDBHelper.COLUMN_WORD_CIN,MyDBHelper.COLUMN_TRANSCRIPTION, MyDBHelper.COLUMN_STATUS_CIN);
        for (int i=0;i<list.size();i++)
            Log.e("tag", list.get(i).toString());*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView lastWord = findViewById(R.id.tv_lastWord);
        lastWord.setText(helper.getLastWord());
    }

    public void goToCards(View view) {
        Intent intent = new Intent(MainActivity.this, CardsChoiceActivity.class);
        if (view.getId()==R.id.trainCardsButton){
            intent.putExtra("isRepeating", false);
        }
        else {
            intent.putExtra("isRepeating", true);
        }
        startActivity(intent);
    }

    public void goToAddWord(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    public void goToDictionary(View view) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    String readFile() {
        String resultString = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput("lastWord.txt")));
            String str = "";

            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.e("LOG_TAG", str + "in the game");
                resultString += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public void goToStat(View view) {
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }

    public void goToThemes(View view) {
        Intent intent = new Intent(this, CardsChoiceActivity.class);
        intent.putExtra("isRepeating", false);
        intent.putExtra("isGroups", true);
        startActivity(intent);
    }

}