package com.example.learningcards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.learningcards.classes.MyDBHelper;
import com.example.learningcards.R;
import com.example.learningcards.classes.Word;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    MyDBHelper helper;
    SQLiteDatabase db;
    List<Word> list;
    TextView card;
    TextView cardsLeft;
    //TextView answersCalc;
    private static final String LOG_TAG = "SwipeTouchListener";
    private static int MIN_DISTANCE;
    private float downX;
    private float downY;

    int type;//1 china 2 eng
    int number;
    boolean isRepeating;
    boolean isDaily;

    String currentGroup;
    boolean isGroups = false;

    int currentWord;
    int currentPart;//0-слово 1-транскрипция 2-перевод
    boolean isReversed = false; // нет: слово - транскрипция-перевод да: перевод - транскрипция - слово

    int rightAnswers = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        card = findViewById(R.id.card);
        cardsLeft = findViewById(R.id.cardsLeft);
        //answersCalc=findViewById(R.id.answersCalc);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        currentWord = 0;
        currentPart = 0;

        MIN_DISTANCE = (int) (120.0f * dm.densityDpi / 160.0f + 0.5);
        card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        float upX = event.getX();
                        float upY = event.getY();

                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        // горизонтальный свайп
                        if (Math.abs(deltaX) > MIN_DISTANCE) { // если дистанция не меньше минимальной
                            // слева направо
                            if (deltaX < 0) {
                                onLeftToRightSwipe(card);
                                return true;
                            }
                            //справа налево
                            if (deltaX > 0) {
                                onRightToLeftSwipe(card);
                                return true;
                            }
                        }

                        // вертикальный свайп
                        if (Math.abs(deltaY) > MIN_DISTANCE) { //если дистанция не меньше минимальной
                            // сверху вниз
                            if (deltaY < 0) {
                                onTopToBottomSwipe();
                                return true;
                            }
                            // снизу вверх
                            if (deltaY > 0) {
                                onBottomToTopSwipe();
                                return true;
                            }
                        }

                        return false;
                    }
                }
                return false;
            }
        });

        helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();
        type = getIntent().getExtras().getInt("type");
        number = getIntent().getExtras().getInt("number");
        isRepeating = getIntent().getExtras().getBoolean("isRepeating");
        isGroups = getIntent().getExtras().getBoolean("isGroups");
        currentGroup = getIntent().getExtras().getString("currentGroup");
        Log.e("type", "" + type);
        Log.e("number", "" + number);
        Log.e("isRepeating", String.valueOf(isRepeating));

        if (isGroups) {

            if (type == 1)
                list = helper.getListByGroup(number, MyDBHelper.COLUMN_WORD_CIN, MyDBHelper.COLUMN_WORD_RUS, currentGroup);
            else
                list = helper.getListByGroup(number, MyDBHelper.COLUMN_WORD_EN, MyDBHelper.COLUMN_WORD_RUS, currentGroup);
        } else {
            if (isRepeating) {

                if (type == 1)
                    list = helper.getRandomRepeatList(number, MyDBHelper.COLUMN_WORD_CIN, MyDBHelper.COLUMN_WORD_RUS, MyDBHelper.COLUMN_STATUS_CIN);
                else
                    list = helper.getRandomRepeatList(number, MyDBHelper.COLUMN_WORD_EN, MyDBHelper.COLUMN_WORD_RUS, MyDBHelper.COLUMN_STATUS_EN);

            } else {
                if (type == 1)
                    list = helper.getRandomList(number, MyDBHelper.COLUMN_WORD_CIN, MyDBHelper.COLUMN_WORD_RUS, MyDBHelper.COLUMN_STATUS_CIN);
                else
                    list = helper.getRandomList(number, MyDBHelper.COLUMN_WORD_EN, MyDBHelper.COLUMN_WORD_RUS, MyDBHelper.COLUMN_STATUS_EN);
            }
        }
        if (list.size() < number)
            number = list.size();
        if (number == 0) {
            if (isRepeating) card.setText("Нет изученных слов");
            else
                card.setText("Не осталось неизученных слов. Гордись собой!");
            card.setOnTouchListener(null);
            ((Button) findViewById(R.id.redArrowButton)).setOnClickListener(null);
            ((Button) findViewById(R.id.greenArrowButton)).setOnClickListener(null);
        } else setWord();

    }

    public void onRightToLeftSwipe(View view) {

        switch (currentPart) {//
            case 2:

                rate();
                currentWord++;
                Log.e("number and current word", "" + number + " " + currentWord);
                if (number == currentWord)
                    goToMain();
                else
                    setWord();
                break;
            case 1://на транскрпиции, спрашиваем перевод
                ((TextView) findViewById(R.id.hint)).setText("Свайпни чтобы получить следующую карточку");
                rightAnswers++;
                currentPart++;
                //answersCalc.setText(Integer.toString(rightAnswers));
                if (type == 1) {
                    if (isReversed)
                        card.setText(list.get(currentWord).getChinese());
                    else
                        card.setText(list.get(currentWord).getRussian());
                } else if (type == 2)
                    if (isReversed)
                        card.setText(list.get(currentWord).getEnglish());
                    else
                        card.setText(list.get(currentWord).getRussian());
                break;
            case 0: //на слове, спрашиваем транскрипцию
                rightAnswers++;
                currentPart++;
                ((TextView) findViewById(R.id.hint)).setText("Перевод?");

                //answersCalc.setText(Integer.toString(rightAnswers));
                if (type == 1) {
                    card.setText(list.get(currentWord).getPinyin());
                    helper.changeStat(1, MyDBHelper.CARD_TRIES_CIN);
                } else if (type == 2) {
                    card.setText(list.get(currentWord).getEnglishTranscription());
                    helper.changeStat(1, MyDBHelper.CARD_TRIES_EN);
                }
                //удобно, что транскрпиция посередине, не нужно делать поправку на isReversed


        }


        Log.i(LOG_TAG, "Справа налево!");
    }


    public void onLeftToRightSwipe(View view) {

        switch (currentPart) {//

            case 2:

                rate();
                currentWord++;
                if (number == currentWord)
                    goToMain();
                else
                    setWord();
                break;
            case 1://на транскрпиции, спрашиваем перевод

                currentPart++;
                ((TextView) findViewById(R.id.hint)).setText("Свайпни чтобы получить следующую карточку");
                //answersCalc.setText(Integer.toString(rightAnswers));
                if (type == 1) {
                    if (isReversed)
                        card.setText(list.get(currentWord).getChinese());
                    else
                        card.setText(list.get(currentWord).getRussian());
                } else if (type == 2)
                    if (isReversed)
                        card.setText(list.get(currentWord).getEnglish());
                    else
                        card.setText(list.get(currentWord).getRussian());
                break;
            case 0: //на слове, спрашиваем транскрипцию

                currentPart++;
                //answersCalc.setText(Integer.toString(rightAnswers));
                if (type == 1) {
                    card.setText(list.get(currentWord).getPinyin());
                    helper.changeStat(1, MyDBHelper.CARD_TRIES_CIN);
                } else if (type == 2) {
                    card.setText(list.get(currentWord).getEnglishTranscription());
                    helper.changeStat(1, MyDBHelper.CARD_TRIES_EN);
                }
                ((TextView) findViewById(R.id.hint)).setText("Перевод?");
                //удобно, что транскрпиция посередине, не нужно делать поправку на isReversed

        }

        Log.i(LOG_TAG, "Слева направо!");
    }

    private void onTopToBottomSwipe() {
        Log.i(LOG_TAG, "Сверху вниз!");
    }

    private void onBottomToTopSwipe() {
        Log.i(LOG_TAG, "Снизу вверх!");
        currentWord++;
        setWord();
    }

    private void setWord() {
        Log.e("I am in", "setWord");
        ((TextView) findViewById(R.id.hint)).setText("Транскрипция?");
        if (type == 1) {
            if (isReversed)
                card.setText(list.get(currentWord).getRussian());
            else
                card.setText(list.get(currentWord).getChinese());
        } else if (type == 2)
            if (isReversed)
                card.setText(list.get(currentWord).getRussian());
            else
                card.setText(list.get(currentWord).getEnglish());
        rightAnswers = 0;
        currentPart = 0;
        cardsLeft.setText("" + (number - currentWord));
        //answersCalc.setText(Integer.toString(rightAnswers));

    }

    private void rate() {
        Log.e("rate", "" + rightAnswers + " " + type + " " + (list.get(currentWord).toString()));
        //todo присосаться к helper.update()
        if (rightAnswers == 2) {
            if (type == 1) {
                helper.updateStatusChinese(list.get(currentWord).getId(), "LEARNED");
                if (list.get(currentWord).getStatus_cin().equals("HALF_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_CIN);
                    helper.changeStat(1, MyDBHelper.LEARNED_CIN);
                } else if (list.get(currentWord).getStatus_cin().equals("NOT_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_CIN);
                    helper.changeStat(1, MyDBHelper.LEARNED_CIN);
                }
            } else {
                helper.updateStatusEnglish(list.get(currentWord).getId(), "LEARNED");
                if (list.get(currentWord).getStatus_en().equals("HALF_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_EN);
                    helper.changeStat(1, MyDBHelper.LEARNED_EN);
                } else if (list.get(currentWord).getStatus_en().equals("NOT_LEARNED")) {

                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_EN);
                    helper.changeStat(1, MyDBHelper.LEARNED_EN);
                }
            }
            String str = list.get(currentWord).getRussian() + " ";
            if (type == 1) {
                str += list.get(currentWord).getChinese() + " ";
                str += list.get(currentWord).getPinyin();
            } else if (type == 2) {
                str += list.get(currentWord).getEnglish() + " ";
                str += list.get(currentWord).getEnglishTranscription();
            }
            helper.setLastWord(str);

        } else if (rightAnswers == 1) {
            if (type == 1) {
                helper.updateStatusChinese(list.get(currentWord).getId(), "HALF_LEARNED");
                if (list.get(currentWord).getStatus_cin().equals("LEARNED")) {
                    helper.changeStat(1, MyDBHelper.HALF_LEARNED_CIN);
                    helper.changeStat(-1, MyDBHelper.LEARNED_CIN);
                } else if (list.get(currentWord).getStatus_cin().equals("NOT_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_CIN);
                    helper.changeStat(1, MyDBHelper.HALF_LEARNED_CIN);
                }
            } else {
                helper.updateStatusEnglish(list.get(currentWord).getId(), "HALF_LEARNED");
                if (list.get(currentWord).getStatus_en().equals("LEARNED")) {
                    helper.changeStat(1, MyDBHelper.HALF_LEARNED_EN);
                    helper.changeStat(-1, MyDBHelper.LEARNED_EN);
                } else if (list.get(currentWord).getStatus_en().equals("NOT_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.NOT_LEARNED_EN);
                    helper.changeStat(1, MyDBHelper.HALF_LEARNED_EN);
                }
            }

        } else {
            if (type == 1) {
                helper.updateStatusChinese(list.get(currentWord).getId(), "NOT_LEARNED");
                if (list.get(currentWord).getStatus_cin().equals("HALF_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_CIN);
                    helper.changeStat(1, MyDBHelper.NOT_LEARNED_CIN);
                } else if (list.get(currentWord).getStatus_cin().equals("LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.LEARNED_CIN);
                    helper.changeStat(1, MyDBHelper.NOT_LEARNED_CIN);
                }
            } else {
                helper.updateStatusEnglish(list.get(currentWord).getId(), "NOT_LEARNED");
                if (list.get(currentWord).getStatus_en().equals("HALF_LEARNED")) {
                    helper.changeStat(-1, MyDBHelper.HALF_LEARNED_EN);
                    helper.changeStat(1, MyDBHelper.NOT_LEARNED_EN);
                } else if (list.get(currentWord).getStatus_en().equals("LEARNED")) {
                    helper.changeStat(1, MyDBHelper.NOT_LEARNED_EN);
                    helper.changeStat(-1, MyDBHelper.LEARNED_EN);
                }
            }
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void rotateCards(View view) {
        //todo а если несколько карточек разыграны?
        Collections.shuffle(list);
        isReversed = !isReversed;
        setWord();

    }

    void writeFile(String str) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("lastWord.txt", MODE_PRIVATE)));
            // пишем данные
            bw.write(str);
            // закрываем поток
            bw.close();
            Log.e("LOG_TAG", "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}