package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.learningcards.classes.MyDBHelper;
import com.example.learningcards.R;
import com.example.learningcards.classes.Word;

import java.util.Collections;
import java.util.List;

public class AddWordActivity extends AppCompatActivity {
    LinearLayout foreignWordLayout;
    boolean chineseAdded = false;
    boolean englishAdded = false;
    boolean chineseUseAdded = false;
    boolean englishUseAdded = false;
    boolean groupAdded = false;

    EditText chinese;
    EditText pinyin;
    EditText chineseUse;

    EditText english;
    EditText transcription;
    EditText englishUse;

    EditText group;

    MyDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        foreignWordLayout = findViewById(R.id.foreignWord);
        helper = new MyDBHelper(this);

    }

    public void addChinese(View view) {

        findViewById(R.id.addChineseButton).setEnabled(false);

        chineseAdded = true;

        chinese = new EditText(this);
        chinese.setHint("Китайский (иероглифы)");
        foreignWordLayout.addView(chinese);

        pinyin = new EditText(this);
        pinyin.setHint("Пиньинь");
        foreignWordLayout.addView(pinyin);

        final Button addChineseUseButton = new Button(this);
        addChineseUseButton.setText("Добавить пример использования (китайский");
        addChineseUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aaa) {
                addChineseUseButton.setEnabled(false);

                chineseUseAdded = true;

                chineseUse = new EditText(AddWordActivity.this);
                chineseUse.setHint("Пример использования");
                foreignWordLayout.addView(chineseUse);
            }
        });
        foreignWordLayout.addView(addChineseUseButton);

    }

    public void addEnglish(View view) {

        findViewById(R.id.addEnglishButton).setEnabled(false);

        englishAdded = true;

        english = new EditText(this);
        english.setHint("Английский");
        foreignWordLayout.addView(english);

        transcription = new EditText(this);
        transcription.setHint("Транскрипция");
        foreignWordLayout.addView(transcription);

        final Button addEnglishUseButton = new Button(this);
        addEnglishUseButton.setText("Добавить пример использования (английский)");
        addEnglishUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addEnglishUseButton.setEnabled(false);

                englishUseAdded = true;

                englishUse = new EditText(AddWordActivity.this);
                englishUse.setHint("Пример использования (английский)");
                foreignWordLayout.addView(englishUse);

            }
        });
        foreignWordLayout.addView(addEnglishUseButton);
    }

    public void addWordToDatabase(View view) {
        Word word = new Word();
        EditText russian = findViewById(R.id.russian);
        word.setRussian(russian.getText().toString());

        if (chineseAdded) {
            helper.changeStat(1, MyDBHelper.NOT_LEARNED_CIN);
            word.setChinese(chinese.getText().toString());
            word.setPinyin(pinyin.getText().toString());
            if (chineseUseAdded) {
                word.setChineseUsage(chineseUse.getText().toString());
            }

        }
        if (englishAdded) {
            helper.changeStat(1, MyDBHelper.NOT_LEARNED_EN);
            word.setEnglish(english.getText().toString());
            word.setEnglishTranscription(transcription.getText().toString());

            if (englishUseAdded) {
                word.setEnglishUsage(englishUse.getText().toString());
            }

        }
        if (groupAdded)
            word.setGroup(ChangeWordActivity.firstUpperCase(group.getText().toString()));
        helper.insert(word);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addGroup(View view) {

        groupAdded = true;

        findViewById(R.id.addGroupButton).setEnabled(false);

        group = new EditText(this);
        group.setHint("Группа");

        Spinner spinner = new Spinner(this);

        List<String> list = helper.getAllGroups();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                group.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        foreignWordLayout.addView(spinner);
        foreignWordLayout.addView(group);
    }


}
