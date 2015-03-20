package com.gpplworx.mopac.mopac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "catalog.db";
    public static final String TABLE_JOURNAL = "journal";
    public static final String COLUMN_JOURNAL_ID = "_id";
    public static final String COLUMN_JOURNAL_ARTICLE_TITLE = "article_title";
    public static final String COLUMN_JOURNAL_JOURNAL_TITLE = "journal_title";
    public static final String COLUMN_JOURNAL_AUTHOR = "author";
    public static final String COLUMN_JOURNAL_DATE_PUBLISHED = "date_published";
    public static final String COLUMN_JOURNAL_VOLUME_OR_NUMBER = "volume_or_number";
    public static final String COLUMN_JOURNAL_SERIES = "series";
    public static final String COLUMN_JOURNAL_NOTES = "notes";
    public static final String COLUMN_JOURNAL_MATERIAL_TYPE = "material_type";
    public static final String COLUMN_JOURNAL_SUBJECT = "subject";

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_AUTHOR = "author";
    public static final String COLUMN_BOOK_PUBLICATION = "publication";
    public static final String COLUMN_BOOK_DISTRIBUTION = "distribution";
    public static final String COLUMN_BOOK_PHYSICAL_DESCRIPTION = "physical_description";
    public static final String COLUMN_BOOK_SERIES = "series";
    public static final String COLUMN_BOOK_NOTES = "notes";
    public static final String COLUMN_BOOK_ISBN = "isbn";
    public static final String COLUMN_BOOK_CALL_NUMBER = "call_number";
    public static final String COLUMN_BOOK_MATERIAL_TYPE = "material_type";
    public static final String COLUMN_BOOK_SUBJECT = "subject";

    public static final String TABLE_LOCATION = "location";
    public static final String COLUMN_LOCATION_ID = "id";
    public static final String COLUMN_LOCATION_ACCESSION_NUMBER = "accession_number";
    public static final String COLUMN_LOCATION_LOCATION = "location";
    public static final String COLUMN_LOCATION_SECTION = "section";
    public static final String COLUMN_LOCATION_STATUS = "status";
    public static final String COLUMN_LOCATION_REFERENCE = "reference";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String journal = "CREATE TABLE " + TABLE_JOURNAL + "(" +
                COLUMN_JOURNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_JOURNAL_ARTICLE_TITLE + " TEXT " +
                COLUMN_JOURNAL_JOURNAL_TITLE + " TEXT " +
                COLUMN_JOURNAL_AUTHOR + " TEXT " +
                COLUMN_JOURNAL_DATE_PUBLISHED + " TEXT " +
                COLUMN_JOURNAL_VOLUME_OR_NUMBER + " TEXT " +
                COLUMN_JOURNAL_SERIES + " TEXT " +
                COLUMN_JOURNAL_NOTES + " TEXT " +
                COLUMN_JOURNAL_MATERIAL_TYPE + " TEXT " +
                COLUMN_JOURNAL_SUBJECT + " TEXT " +
                ");";

        db.execSQL(journal);

        String book = "CREATE TABLE " + TABLE_BOOK + "(" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_BOOK_TITLE + " TEXT " +
                COLUMN_BOOK_AUTHOR + " TEXT " +
                COLUMN_BOOK_PUBLICATION + " TEXT " +
                COLUMN_BOOK_DISTRIBUTION + " TEXT " +
                COLUMN_BOOK_PHYSICAL_DESCRIPTION + " TEXT " +
                COLUMN_BOOK_SERIES + " TEXT " +
                COLUMN_BOOK_NOTES + " TEXT " +
                COLUMN_BOOK_ISBN + " TEXT " +
                COLUMN_BOOK_CALL_NUMBER + " TEXT " +
                COLUMN_BOOK_MATERIAL_TYPE + " TEXT " +
                COLUMN_BOOK_SUBJECT + " TEXT " +
                ");";

        db.execSQL(book);

        String location = "CREATE TABLE " + TABLE_LOCATION + "(" +
                COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_LOCATION_ACCESSION_NUMBER + " TEXT " +
                COLUMN_LOCATION_LOCATION + " TEXT " +
                COLUMN_LOCATION_SECTION + " TEXT " +
                COLUMN_LOCATION_STATUS + " TEXT " +
                COLUMN_LOCATION_REFERENCE + " TEXT " +
                ");";

        db.execSQL(location);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }


}
