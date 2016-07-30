package com.example.colormemory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 30/7/16.
 * All Right Reserved
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scoreManager";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_SCORES = "scores";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    public static String Lock = "dblock";
    private static DatabaseHandler mDb;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + KEY_TIMESTAMP + " LONG PRIMARY KEY, " + KEY_NAME
                + " TEXT, " + KEY_SCORE + " INTEGER )";

        sqLiteDatabase.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new Record
    public void addScore(ScoreObject score) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.enableWriteAheadLogging();
            ContentValues values = new ContentValues();
            values.put(KEY_TIMESTAMP, System.currentTimeMillis());

            values.put(KEY_NAME, score.name);
            values.put(KEY_SCORE, score.score);
            // Inserting Row
            db.insert(TABLE_SCORES, null, values);
            db.close();
        } // Closing database connection
    }

    public ArrayList<ScoreObject> getScores() {
        ArrayList<ScoreObject> scoreList = new ArrayList<ScoreObject>();
        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] score = new String[]{KEY_SCORE, KEY_NAME};
            Cursor cursor = db.query(TABLE_SCORES, score, null, null, null, null, KEY_SCORE + " DESC");
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, cursor.getString(0));
                    scoreList.add(new ScoreObject(
                            cursor.getString(cursor
                                    .getColumnIndex(KEY_NAME)),
                            cursor.getInt(cursor
                                    .getColumnIndex(KEY_SCORE))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return scoreList;
    }

    public static DatabaseHandler getInstance(Context context) {
        if (mDb == null) {
            mDb = new DatabaseHandler(context);
        }
        return mDb;
    }
}
