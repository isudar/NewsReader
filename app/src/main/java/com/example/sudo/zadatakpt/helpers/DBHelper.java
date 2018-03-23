package com.example.sudo.zadatakpt.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sudo.zadatakpt.models.News;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return dbHelper;
    }

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news";

    private static final String TABLE_NEWS = "news";

    /**
     * General types
     */
    private static final String COLUMN_ID = "Id";

    /**
     * News table
     */

    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_URL_TO_IMAGE = "urlToImage";
    private static final String COLUMN_PUBLISHED_AT = "publishedAT";

    /**
     * SQL for creating table
     */
    private static final String CREATE_TABLE_NEWS = "CREATE TABLE " + TABLE_NEWS + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_AUTHOR + " TEXT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_URL + " TEXT,"
            + COLUMN_URL_TO_IMAGE + " TEXT,"
            + COLUMN_PUBLISHED_AT + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

        onCreate(db);
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<News>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                newsList.add(new News(
                        Long.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return newsList;
    }

    public void addNews(News news) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTHOR, news.getAuthor());
        values.put(COLUMN_TITLE, news.getTitle());
        values.put(COLUMN_DESCRIPTION, news.getDescription());
        values.put(COLUMN_URL, news.getUrl());
        values.put(COLUMN_URL_TO_IMAGE, news.getUrlToImage());
        values.put(COLUMN_PUBLISHED_AT, news.getPublishedAt());


        sqLiteDatabase.insert(TABLE_NEWS, null, values);
        sqLiteDatabase.close();
    }

    public News getNews(Long id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS +
                " WHERE " + COLUMN_ID + "=" + id;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Log.e("test", "Is there some shit");
            News news = new News(
                    Long.valueOf(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            Log.e("test", "Is there some shit");
            Log.e("test", String.valueOf(news.getId()));
            cursor.close();
            sqLiteDatabase.close();
            return news;
        } else {
            sqLiteDatabase.close();
            return null;
        }
    }

    public void deleteNews() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NEWS, null, null);
        sqLiteDatabase.close();
    }
}
