package com.gpplworx.mopac.mopac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Catalog extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "catalog.db";

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_AUTHOR = "author";
    public static final String COLUMN_BOOK_PUBLICATION = "publication";
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

    public static final String TABLE_RECENT = "recent";
    public static final String COLUMN_RECENT_ID = "id";
    public static final String COLUMN_RECENT_ITEM = "item";
    public static final String COLUMN_RECENT_ITEM_TYPE = "type";

    public Catalog(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String book = "CREATE TABLE " + TABLE_BOOK + "(" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_BOOK_TITLE + " TEXT, " +
                COLUMN_BOOK_AUTHOR + " TEXT, " +
                COLUMN_BOOK_PUBLICATION + " TEXT, " +
                COLUMN_BOOK_PHYSICAL_DESCRIPTION + " TEXT, " +
                COLUMN_BOOK_SERIES + " TEXT, " +
                COLUMN_BOOK_NOTES + " TEXT, " +
                COLUMN_BOOK_ISBN + " TEXT, " +
                COLUMN_BOOK_CALL_NUMBER + " TEXT, " +
                COLUMN_BOOK_MATERIAL_TYPE + " TEXT, " +
                COLUMN_BOOK_SUBJECT + " TEXT " +
                ");";

        db.execSQL(book);

        String location = "CREATE TABLE " + TABLE_LOCATION + "(" +
                COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_LOCATION_ACCESSION_NUMBER + " TEXT, " +
                COLUMN_LOCATION_LOCATION + " TEXT, " +
                COLUMN_LOCATION_SECTION + " TEXT, " +
                COLUMN_LOCATION_STATUS + " TEXT, " +
                COLUMN_LOCATION_REFERENCE + " TEXT " +
                ");";

        db.execSQL(location);

        String recent = "CREATE TABLE " + TABLE_RECENT + "(" +
                COLUMN_RECENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECENT_ITEM + " VARCHAR(10), " +
                COLUMN_RECENT_ITEM_TYPE + " VARCHAR(1) " +
                ");";

        db.execSQL(recent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        onCreate(db);
    }

    public void addBook(Book book){
        if(!checkIfExists(book.get_id(), "book")) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BOOK_ID, book.get_id());
            values.put(COLUMN_BOOK_TITLE, book.get_title());
            values.put(COLUMN_BOOK_AUTHOR, book.get_author());
            values.put(COLUMN_BOOK_PUBLICATION, book.get_publication());
            values.put(COLUMN_BOOK_PHYSICAL_DESCRIPTION, book.get_physical_description());
            values.put(COLUMN_BOOK_SERIES, book.get_series());
            values.put(COLUMN_BOOK_NOTES, book.get_notes());
            values.put(COLUMN_BOOK_ISBN, book.get_isbn());
            values.put(COLUMN_BOOK_CALL_NUMBER, book.get_call_number());
            values.put(COLUMN_BOOK_MATERIAL_TYPE, book.get_material_type());
            values.put(COLUMN_BOOK_SUBJECT, book.get_subject());

            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_BOOK, null, values);
            db.close();
        }
    }

    public void addLocation(Location location){
        if(!checkIfExists(location.get_id(), "location")) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LOCATION_ID, location.get_id());
            values.put(COLUMN_LOCATION_ACCESSION_NUMBER, location.get_accession_number());
            values.put(COLUMN_LOCATION_LOCATION, location.get_location());
            values.put(COLUMN_LOCATION_SECTION, location.get_section());
            values.put(COLUMN_LOCATION_STATUS, location.get_status());
            values.put(COLUMN_LOCATION_REFERENCE, location.get_reference());

            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_LOCATION, null, values);
            db.close();
        }
    }

    public void addRecent(RecentItem recent){
        SQLiteDatabase db = getWritableDatabase();
        String item = recent.get_item(), type = recent.get_type();
        db.execSQL("DELETE FROM " + TABLE_RECENT +
                " WHERE " + COLUMN_RECENT_ITEM + "='" + item +
                "' AND " + COLUMN_RECENT_ITEM_TYPE + "='" + type + "'");

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECENT_ITEM,item);
        values.put(COLUMN_RECENT_ITEM_TYPE,type);

        db.insert(TABLE_RECENT, null, values);
        db.close();
    }

    public ArrayList<Location> getLocationItem(String id){
        ArrayList<Location> lists = new ArrayList<Location>();

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_LOCATION + " WHERE reference = '" + id + "'";


        Cursor c = db.rawQuery(query, null);

        if(c != null) {
            c.moveToFirst();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                Location location = new Location();
                int iLocationID = c.getColumnIndex(COLUMN_LOCATION_ID);
                int iLocationAccessionNumber = c.getColumnIndex(COLUMN_LOCATION_ACCESSION_NUMBER);
                int iLocationLocation = c.getColumnIndex(COLUMN_LOCATION_LOCATION);
                int iLocationSection = c.getColumnIndex(COLUMN_LOCATION_SECTION);
                int iLocationStatus = c.getColumnIndex(COLUMN_LOCATION_STATUS);

                location.set_id(c.getString(iLocationID));
                location.set_accession_number(c.getString(iLocationAccessionNumber));
                location.set_location(c.getString(iLocationLocation));
                location.set_section(c.getString(iLocationSection));
                location.set_status(c.getString(iLocationStatus));
                location.set_reference(id);

                lists.add(location);
            }
        }

        return lists;
    }

    public boolean checkIfExists(String id, String table){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        if(table.equals("book"))
            query = "SELECT * FROM " + TABLE_BOOK + " WHERE id = '" + id + "'";
        else if(table.equals("location"))
            query = "SELECT * FROM " + TABLE_LOCATION + " WHERE id = '" + id + "'";

        Cursor c = db.rawQuery(query, null);
        if(c.getCount() > 0)
            return true;
        else
            return false;
    }

    public Book getBookItem(String id){
        Book book = new Book();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE id = '" + id + "'";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
            int iBookID = c.getColumnIndex(COLUMN_BOOK_ID);
            int iBookTitle = c.getColumnIndex(COLUMN_BOOK_TITLE);
            int iBookAuthor = c.getColumnIndex(COLUMN_BOOK_AUTHOR);
            int iBookPublication = c.getColumnIndex(COLUMN_BOOK_PUBLICATION);
            int iBookPhysicalDescription = c.getColumnIndex(COLUMN_BOOK_PHYSICAL_DESCRIPTION);
            int iBookSeries = c.getColumnIndex(COLUMN_BOOK_SERIES);
            int iBookNotes = c.getColumnIndex(COLUMN_BOOK_NOTES);
            int iBookISBN = c.getColumnIndex(COLUMN_BOOK_ISBN);
            int iBookCallNumber = c.getColumnIndex(COLUMN_BOOK_CALL_NUMBER);
            int iBookMaterialType = c.getColumnIndex(COLUMN_BOOK_MATERIAL_TYPE);
            int iBookSubject = c.getColumnIndex(COLUMN_BOOK_SUBJECT);

            book.set_id(c.getString(iBookID));
            book.set_title(c.getString(iBookTitle));
            book.set_author(c.getString(iBookAuthor));
            book.set_publication(c.getString(iBookPublication));
            book.set_physical_description(c.getString(iBookPhysicalDescription));
            book.set_series(c.getString(iBookSeries));
            book.set_notes(c.getString(iBookNotes));
            book.set_isbn(c.getString(iBookISBN));
            book.set_call_number(c.getString(iBookCallNumber));
            book.set_material_type(c.getString(iBookMaterialType));
            book.set_subject(c.getString(iBookSubject));

        return book;
    }

    public ArrayList<SearchResults> recentSearch(){
        ArrayList<SearchResults> lists = new ArrayList<SearchResults>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_RECENT + " ORDER BY id DESC";
        Cursor a = db.rawQuery(query, null);
        int iItem = a.getColumnIndex(COLUMN_RECENT_ITEM);

        String item = null, q = null;
        if(a != null){
            for(a.moveToFirst(); !a.isAfterLast(); a.moveToNext()){
                item = a.getString(iItem);

                q = "SELECT * FROM " + TABLE_BOOK + " WHERE id = '" + item + "'";
                Cursor b = db.rawQuery(q,null);
                b.moveToFirst();
                int iBookID = b.getColumnIndex(COLUMN_BOOK_ID);
                int iBookTitle = b.getColumnIndex(COLUMN_BOOK_TITLE);
                int iBookAuthor = b.getColumnIndex(COLUMN_BOOK_AUTHOR);
                int iBookCallNumber = b.getColumnIndex(COLUMN_BOOK_CALL_NUMBER);
                int iBookMaterialType = b.getColumnIndex(COLUMN_BOOK_MATERIAL_TYPE);

                SearchResults list = new SearchResults();
                list.set_id(b.getString(iBookID));
                list.set_author(b.getString(iBookAuthor));
                list.set_call_number(b.getString(iBookCallNumber));
                list.set_title(b.getString(iBookTitle));
                list.set_status("Available");

                String type = b.getString(iBookMaterialType).toLowerCase();
                if(type.contains("book") || type.equals("do-not-use"))
                    list.set_type("book");
                else
                    list.set_type("journal");

                lists.add(list);
            }
        }

        return lists;
    }

    public void truncateDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        onCreate(db);
    }
}
