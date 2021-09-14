package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.learningcards.R;
import com.example.learningcards.classes.MyDBHelper;
import com.example.learningcards.classes.Word;

import java.util.Collections;
import java.util.List;

public class ChangeWordActivity extends AppCompatActivity {
    MyDBHelper helper;
    int id;
    Word word;

    LinearLayout layout;

    EditText russian;

    EditText chinese;
    EditText pinyin;
    EditText chineseUse;

    EditText english;
    EditText transcription;
    EditText englishUse;

    EditText group;
    Spinner chineseStatus;
    Spinner englishStatus;

    boolean chineseAdded = false;
    boolean englishAdded = false;
    boolean chineseUseAdded = false;
    boolean englishUseAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_word);
        id = (int) getIntent().getExtras().getLong("id");
        helper = new MyDBHelper(this);

        word = helper.getWord(id);
        layout = findViewById(R.id.changeWordLayout);
        russian = findViewById(R.id.russianChange);
        russian.setText(word.getRussian());
        addGroup();
        if (word.getChinese() != null) {

            chineseAdded = true;

            chinese = new EditText(this);
            chinese.setHint("Китайский (иероглифы)");
            chinese.setText(word.getChinese());
            layout.addView(chinese);

            pinyin = new EditText(this);
            pinyin.setHint("Пиньинь");
            pinyin.setText(word.getPinyin());
            layout.addView(pinyin);

            if (word.getChineseUsage() != null) {
                chineseUseAdded = true;

                chineseUse = new EditText(this);
                chineseUse.setHint("Пример использования");
                chineseUse.setText(word.getChineseUsage());
                layout.addView(chineseUse);
            }
        }
        if (word.getEnglish() != null) {

            englishAdded = true;

            english = new EditText(this);
            english.setHint("Английский");
            english.setText(word.getEnglish());
            layout.addView(english);

            transcription = new EditText(this);
            transcription.setHint("Транскрипция");
            transcription.setText(word.getEnglishTranscription());
            layout.addView(transcription);

            if (word.getEnglishUsage() != null) {
                englishUseAdded = true;

                englishUse = new EditText(this);
                englishUse.setHint("Пример использования (английский)");
                englishUse.setText(word.getEnglishUsage());
                layout.addView(englishUse);
            }
        }
    }

    //todo спиннеры на статус
    public void saveWord(View view) {
        word.setRussian(russian.getText().toString());
        if (chineseAdded) {
            word.setChinese(chinese.getText().toString());
            word.setPinyin(pinyin.getText().toString());
            if (chineseUseAdded) {
                word.setChineseUsage(chineseUse.getText().toString());
            }

        }
        if (englishAdded) {
            word.setEnglish(english.getText().toString());
            word.setEnglishTranscription(transcription.getText().toString());

            if (englishUseAdded) {
                word.setEnglishUsage(englishUse.getText().toString());
            }

        }
        word.setGroup(firstUpperCase(group.getText().toString()));
        Log.e("id", "" + id);
        Log.e("word", word.toString());
        helper.update(id, word);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void deleteWord(View view) {
        if (chineseAdded) {
            switch (word.getStatus_cin()) {
                case "LEARNED":
                    helper.changeStat(-1, MyDBHelper.LEARNED_CIN);
                    break;
                case "HALF_LEARNED":
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_CIN);
                    break;
                case "NOT_LEARNED":
                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_CIN);
                    break;

            }
        }
        if (englishAdded) {
            switch (word.getStatus_en()) {
                case "LEARNED":
                    helper.changeStat(-1, MyDBHelper.LEARNED_EN);
                    break;
                case "HALF_LEARNED":
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_EN);
                    break;
                case "NOT_LEARNED":
                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_EN);
                    break;

            }
        }
        helper.delete(id);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void addGroup() {

        group = new EditText(this);
        group.setHint("Группа");
        group.setText(word.getGroup());

        Spinner spinner = new Spinner(this);
        List<String> list = helper.getAllGroups();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(list.indexOf(word.getGroup()));
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                group.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        layout.addView(spinner);
        layout.addView(group);
    }

    static String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";//или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
