package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.learningcards.R;
import com.example.learningcards.classes.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThemeChoiceActivity extends AppCompatActivity {
    int type;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getExtras().getInt("type");
        number = getIntent().getExtras().getInt("number");
        setContentView(R.layout.activity_theme_choice);
        MyDBHelper helper = new MyDBHelper(this);
        final List<String> list;
        if (type == 1) {
            list = helper.getAllGroups(MyDBHelper.COLUMN_WORD_CIN);
        } else {
            list = helper.getAllGroups(MyDBHelper.COLUMN_WORD_EN);
        }

        final List<String> oddList = new ArrayList<>();
        final List<String> evenList = new ArrayList<>();
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                evenList.add(list.get(i));
            } else {
                oddList.add(list.get(i));
            }
        }
        ArrayAdapter<String> evenAdapter = new ArrayAdapter(this,
                R.layout.squared_list_adapter, evenList);
        final ListView evenListView = findViewById(R.id.listOfThemesEven);
        evenListView.setAdapter(evenAdapter);
        evenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goFurther(evenList.get(position));
            }
        });

        ArrayAdapter<String> oddAdapter = new ArrayAdapter(this,
                R.layout.squared_list_adapter, oddList);
        final ListView oddListView = findViewById(R.id.listOfThemesOdd);
        oddListView.setAdapter(oddAdapter);
        oddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goFurther(oddList.get(position));
            }
        });

    }

    void goFurther(String theme) {

        Intent intent = new Intent(this, CardsActivity.class);
        intent.putExtra("currentGroup", theme);
        intent.putExtra("isRepeating", false);
        intent.putExtra("isGroups", true);
        intent.putExtra("type", type);
        intent.putExtra("number", number);
        startActivity(intent);
    }


}