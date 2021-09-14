package com.example.learningcards.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userStore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "words"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD_RUS = "ru";
    public static final String COLUMN_WORD_EN = "en";
    public static final String COLUMN_USAGE_EN = "usage_en";
    public static final String COLUMN_TRANSCRIPTION = "transcription";
    public static final String COLUMN_WORD_CIN = "ch";
    public static final String COLUMN_PINYIN = "pinyin";
    public static final String COLUMN_USAGE_CIN = "usage_cin";
    public static final String COLUMN_STATUS_EN = "status_en";
    public static final String COLUMN_STATUS_CIN = "status_cin";
    public static final String COLUMN_GROUP = "the_group";

    public static final String CARD_TRIES_CIN = "CARD_TRIES_CIN";
    public static final String CARD_TRIES_EN = "CARD_TRIES_EN";
    public static final String HALF_LEARNED_CIN = "HALF_LEARNED_CIN";
    public static final String HALF_LEARNED_EN = "HALF_LEARNED_EN";
    public static final String LAST_WORD = "LAST_WORD";
    public static final String LEARNED_EN = "LEARNED_EN";
    public static final String LEARNED_CIN = "LEARNED_CIN";
    public static final String NOT_LEARNED_EN = "NOT_LEARNED_EN";
    public static final String NOT_LEARNED_CIN = "NOT_LEARNED_CIN";
    public static final String TEST_SUCCESS = "TEST_SUCCESS";
    public static final String TEST_TRIES = "TEST_TRIES";
    public static final String TIPS = "TIPS";


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE IF NOT EXISTS words (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_WORD_RUS + " TEXT, " +
                COLUMN_WORD_EN + " TEXT, " +
                COLUMN_TRANSCRIPTION + " TEXT, " +
                COLUMN_USAGE_EN + " TEXT, " +
                COLUMN_WORD_CIN + " TEXT, " +
                COLUMN_PINYIN + " TEXT, " +
                COLUMN_USAGE_CIN + " TEXT, " +
                COLUMN_STATUS_EN + " TEXT, " +
                COLUMN_STATUS_CIN + " TEXT, " +
                COLUMN_GROUP + " TEXT);");
        String sql = "CREATE TABLE IF NOT EXISTS stats (" +
                CARD_TRIES_CIN + " INTEGER, " +
                CARD_TRIES_EN + " INTEGER, " +
                HALF_LEARNED_CIN + " INTEGER, " +
                HALF_LEARNED_EN + " INTEGER, " +
                LAST_WORD + " TEXT, " +
                LEARNED_EN + " INTEGER, " +
                LEARNED_CIN + " INTEGER, " +
                NOT_LEARNED_EN + " INTEGER, " +
                NOT_LEARNED_CIN + " INTEGER, " +
                TEST_SUCCESS + " INTEGER, " +
                TIPS + " INTEGER, " +
                TEST_TRIES + " INTEGER);";

        db.execSQL(sql);


    }

    public void update(int id, Word word) {
        //todo избавиться от id
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORD_RUS, word.getRussian());
        cv.put(COLUMN_WORD_EN, word.getEnglish());
        cv.put(COLUMN_TRANSCRIPTION, word.getEnglishTranscription());
        cv.put(COLUMN_USAGE_EN, word.getEnglishUsage());
        cv.put(COLUMN_WORD_CIN, word.getChinese());
        cv.put(COLUMN_PINYIN, word.getPinyin());
        cv.put(COLUMN_USAGE_CIN, word.getChineseUsage());
        cv.put(COLUMN_STATUS_EN, word.getStatus_en());
        cv.put(COLUMN_STATUS_CIN, word.getStatus_cin());
        cv.put(COLUMN_GROUP, word.getGroup());

        Log.e("id", "" + id);
        Log.e("word", word.toString());

        getWritableDatabase().update(TABLE, cv, COLUMN_ID + "=" + String.valueOf(id), null);
    }

    public void insert(/*String rus, String en, String transcription, String enUsage, String cin, String pinyin, String chineseUsage, String status_en, String status_cin, String the_group*/ Word word) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORD_RUS, word.getRussian());
        cv.put(COLUMN_WORD_EN, word.getEnglish());
        cv.put(COLUMN_TRANSCRIPTION, word.getEnglishTranscription());
        cv.put(COLUMN_USAGE_EN, word.getEnglishUsage());
        cv.put(COLUMN_WORD_CIN, word.getChinese());
        cv.put(COLUMN_PINYIN, word.getPinyin());
        cv.put(COLUMN_USAGE_CIN, word.getChineseUsage());
        cv.put(COLUMN_STATUS_EN, word.getStatus_en());
        cv.put(COLUMN_STATUS_CIN, word.getStatus_cin());
        cv.put(COLUMN_GROUP, word.getGroup());
        /*cv.put(COLUMN_WORD_RUS,rus);
        cv.put(COLUMN_WORD_EN, en);
        cv.put(COLUMN_TRANSCRIPTION, transcription);
        cv.put(COLUMN_USAGE_EN, enUsage);
        cv.put(COLUMN_WORD_CIN,cin);
        cv.put(COLUMN_PINYIN, pinyin);
        cv.put(COLUMN_USAGE_CIN, chineseUsage);
        cv.put(COLUMN_STATUS_EN, status_en);
        cv.put(COLUMN_STATUS_CIN, status_cin);
        cv.put(COLUMN_GROUP, the_group);*/
        getWritableDatabase().insert(TABLE, null, cv);
    }

    public List<Word> getListByGroup(int number, String from, String to, String group) {
        List<Word> list = new ArrayList<>();
        int i = 0;
        Cursor userCursor = getReadableDatabase().rawQuery("select * from " + MyDBHelper.TABLE +
                " where " + from + " is not null and " + to + " is not null and " + COLUMN_GROUP + " = '" + group + "'", null);
        while (userCursor.moveToNext()) {
            Word word = new Word(
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_ID)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_RUS)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_TRANSCRIPTION)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PINYIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));
            list.add(word);
            i++;
        }
        if (i < number)
            number = i;
        userCursor.close();
        Collections.shuffle(list);
        return list.subList(0, number);
    }

    public List<Word> getRandomList(int number, String from, String to, String progress) {
        List<Word> list = new ArrayList<>();
        Cursor userCursor = getReadableDatabase().rawQuery("select * from " + MyDBHelper.TABLE +
                " where " + from + " is not null and " + to + " is not null and " + progress + " !='LEARNED'", null);


        int i = 0;
        while (userCursor.moveToNext()) {
            Word word = new Word(
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_ID)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_RUS)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_TRANSCRIPTION)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PINYIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));
            list.add(word);
            i++;

        }

        if (i < number)
            number = i;
        userCursor.close();
        Collections.shuffle(list);
        return list.subList(0, number);
    }

    public List<Word> getRandomRepeatList(int number, String from, String to, String progress) {
        List<Word> list = new ArrayList<>();
        Cursor userCursor = getReadableDatabase().rawQuery("select * from " + MyDBHelper.TABLE +
                " where " + from + " is not null and " + to + " is not null and " + progress + " ='LEARNED'", null);
        //todo переделать вызов getRandomList

        int i = 0;
        while (userCursor.moveToNext()) {
            Word word = new Word(
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_ID)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_RUS)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_TRANSCRIPTION)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PINYIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));
            list.add(word);
            i++;

        }

        if (i < number)
            number = i;
        userCursor.close();
        Collections.shuffle(list);
        return list.subList(0, number);
    }

    // todo объединение многих функций в одну
    public Word getWord(int id) {
        String sql = "select * from " + MyDBHelper.TABLE +
                " where " + COLUMN_ID + " = " + id;
        Log.e("sql", sql);
        Cursor userCursor = getReadableDatabase().rawQuery(sql, null);

        userCursor.moveToFirst();
        Word word = new Word(
                userCursor.getInt(userCursor.getColumnIndex(COLUMN_ID)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_EN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_CIN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_RUS)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_EN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_TRANSCRIPTION)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_EN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_CIN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_CIN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_PINYIN)),
                userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));
        userCursor.close();
        return word;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void updateStatusChinese(int ID, String status) {
        Log.e("ID", "" + ID);
        String sql = "update words set " + COLUMN_STATUS_CIN + " = '" + status + "' where " + COLUMN_ID + " = " + ID + ";";
        Log.e("SQL", sql);
        getWritableDatabase().execSQL(sql);
    }

    public void updateStatusEnglish(int ID, String status) {
        String sql = "update words set " + COLUMN_STATUS_EN + " = '" + status + "' where " + COLUMN_ID + " = " + ID + ";";
        getWritableDatabase().execSQL(sql);
    }

    public Cursor getAll() {
        List<Word> list = new ArrayList<>();
        Cursor userCursor = getReadableDatabase().rawQuery("select * from " + MyDBHelper.TABLE, null);
        return userCursor;
        /*while (userCursor.moveToNext()){
            Word word = new Word(
                    userCursor.getInt(userCursor.getColumnIndex(COLUMN_ID)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_STATUS_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_RUS)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_TRANSCRIPTION)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_EN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_WORD_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_USAGE_CIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_PINYIN)),
                    userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));
            list.add(word);
        }
        userCursor.close();
        return list;*/
    }

    public void delete(int id) {
        getWritableDatabase().delete("words", COLUMN_ID + " =? ", new String[]{String.valueOf(id)});
    }

    public String getLastWord() {
        String sql = "select " + LAST_WORD + " from stats";

        Cursor userCursor = getReadableDatabase().rawQuery(sql, null);


        if (!userCursor.moveToFirst()) {
            ContentValues cv = new ContentValues();
            cv.put(CARD_TRIES_CIN, 0);
            cv.put(CARD_TRIES_EN, 0);
            cv.put(HALF_LEARNED_CIN, 0);
            cv.put(HALF_LEARNED_EN, 0);
            cv.put(LEARNED_CIN, 0);
            cv.put(LEARNED_EN, 0);
            cv.put(NOT_LEARNED_CIN, 0);
            cv.put(NOT_LEARNED_EN, 0);
            cv.put(TEST_SUCCESS, 0);
            cv.put(TEST_TRIES, 0);
            cv.put(TIPS, 0);
            cv.put(LAST_WORD, "Тут будет последнее изученное слово");
            getWritableDatabase().insert("stats", null, cv);
            userCursor = getReadableDatabase().rawQuery(sql, null);
            userCursor.moveToFirst();

        }
        ;

        String str = userCursor.getString(userCursor.getColumnIndex(LAST_WORD));

        userCursor.close();

        return str;
    }

    public void setLastWord(String lastWord) {
        String sql = "update stats set " + LAST_WORD + " = '" + lastWord + "';";
        Log.e("new last word", sql);
        getWritableDatabase().execSQL(sql);
    }

    public void changeStat(int diff, String stat) {
        Log.e(" in change stat, args", stat + " " + diff);
        String sql = "select " + stat + " from stats";
        Cursor userCursor = getReadableDatabase().rawQuery(sql, null);
        userCursor.moveToFirst();
        int changingStat = userCursor.getInt(userCursor.getColumnIndex(stat));
        changingStat += diff;
        sql = "update stats set " + stat + " = " + changingStat + ";";
        getWritableDatabase().execSQL(sql);
        Log.e("changeStatSQL2", sql);
        userCursor.close();
    }

    public Cursor getAllStat() {
        return getReadableDatabase().rawQuery("select * from stats", null);
    }


    public List<String> getAllGroups() {
        List<String> list = new ArrayList<>();
        String sql = "select distinct " + COLUMN_GROUP + " from words";
        Cursor userCursor = getReadableDatabase().rawQuery(sql, null);

        while (userCursor.moveToNext()) {

            list.add(userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));

        }
        Collections.sort(list);
        userCursor.close();
        return list;
    }

    public List<String> getAllGroups(String language) {
        List<String> list = new ArrayList<>();
        String sql = "select distinct " + COLUMN_GROUP + " from words" + " where " + language + " is not null ";
        Cursor userCursor = getReadableDatabase().rawQuery(sql, null);

        while (userCursor.moveToNext()) {

            list.add(userCursor.getString(userCursor.getColumnIndex(COLUMN_GROUP)));

        }
        Collections.sort(list);
        userCursor.close();
        return list;
    }
}

