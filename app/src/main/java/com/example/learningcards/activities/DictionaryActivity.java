package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.learningcards.R;
import com.example.learningcards.classes.MyDBHelper;
import com.example.learningcards.classes.Word;

import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    MyDBHelper helper;

    SimpleCursorAdapter userAdapter;
    Cursor cursor;
    ListView listView;
    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        helper = new MyDBHelper(this);

        cursor = helper.getAll();

        String[] headers = new String[]{MyDBHelper.COLUMN_WORD_RUS, MyDBHelper.COLUMN_WORD_CIN, MyDBHelper.COLUMN_WORD_EN};
        int[] to = new int[]{R.id.russianCursorAdapter, R.id.chineseCursorAdapter, R.id.englishCursorAdapter};

        userAdapter = new SimpleCursorAdapter(this, R.layout.triple_cursor_adapter, cursor, headers, to, 0);
        listView = findViewById(R.id.dictionaryView);
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DictionaryActivity.this, ChangeWordActivity.class);
                intent.putExtra("id", id);
                Log.e("putted id", "" + id);
                startActivity(intent);
            }
        });
        counter = findViewById(R.id.wordsCounter);
        counter.setText(Integer.toString(userAdapter.getCount()));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();


    }
}

