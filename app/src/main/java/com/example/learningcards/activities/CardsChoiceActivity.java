package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.learningcards.R;

public class CardsChoiceActivity extends AppCompatActivity {

    int type;
    int a = 15;
    boolean isRepeating;
    boolean isGroups;
    //String currentGroup="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_choice);
        isRepeating = getIntent().getExtras().getBoolean("isRepeating");
        isGroups = getIntent().getExtras().getBoolean("isGroups");
        /*if (isGroups)
            currentGroup = getIntent().getExtras().getString("currentGroup");
        TextView textView = findViewById(R.id.currentGroup_tv);
        textView.setText(currentGroup);*/
    }

    public void showMeDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.number_choice);
        SeekBar seekBar = dialog.findViewById(R.id.seekBar2);

        final TextView tv_reputation = dialog.findViewById(R.id.reputation);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_reputation.setText(String.valueOf(progress));
                a = progress;
                Log.e("number", "" + a);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        dialog.show();
    }

    public void chineseClick(View view) {
        type = 1;
        showMeDialog();

    }

    public void englishClick(View view) {
        type = 2;
        showMeDialog();
    }

    public void getButtonResult(View view) {

        if (isGroups){
            Intent intent = new Intent(this, ThemeChoiceActivity.class);
            Log.e("number", "" + a);
            intent.putExtra("number", a);
            intent.putExtra("type", type);
            intent.putExtra("isRepeating", isRepeating);
            intent.putExtra("isGroups", isGroups);
            //intent.putExtra("currentGroup", currentGroup);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, CardsActivity.class);
            Log.e("number", "" + a);
            intent.putExtra("number", a);
            intent.putExtra("type", type);
            intent.putExtra("isRepeating", isRepeating);
            intent.putExtra("isGroups", isGroups);
            //intent.putExtra("currentGroup", currentGroup);
            startActivity(intent);
        }

    }
}
