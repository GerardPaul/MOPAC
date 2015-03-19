package com.gpplworx.mopac.mopac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "catalog.db";
    public static final String TABLE_JOURNAL = "journal";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTICLE_TITLE = "article_title";
    public static final String COLUMN_JOURNAL_TITLE = "journal_title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DATE_PUBLISHED = "date_published";
    public static final String COLUMN_VOLUME_OR_NUMBER = "volume_or_number";
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_MATERIAL_TYPE = "material_type";
    public static final String COLUMN_SUBJECT = "subject";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_JOURNAL + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_ARTICLE_TITLE + " TEXT " +
                COLUMN_JOURNAL_TITLE + " TEXT " +
                COLUMN_AUTHOR + " TEXT " +
                COLUMN_DATE_PUBLISHED + " TEXT " +
                COLUMN_VOLUME_OR_NUMBER + " TEXT " +
                COLUMN_SERIES + " TEXT " +
                COLUMN_NOTES + " TEXT " +
                COLUMN_MATERIAL_TYPE + " TEXT " +
                COLUMN_SUBJECT + " TEXT " +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        onCreate(db);
    }


}
