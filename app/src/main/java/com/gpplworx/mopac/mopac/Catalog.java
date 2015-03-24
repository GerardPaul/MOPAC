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

    public static final String TABLE_JOURNAL = "journal";
    public static final String COLUMN_JOURNAL_ID = "id";
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
        String journal = "CREATE TABLE " + TABLE_JOURNAL + "(" +
                COLUMN_JOURNAL_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_JOURNAL_ARTICLE_TITLE + " TEXT, " +
                COLUMN_JOURNAL_JOURNAL_TITLE + " TEXT, " +
                COLUMN_JOURNAL_AUTHOR + " TEXT, " +
                COLUMN_JOURNAL_DATE_PUBLISHED + " TEXT, " +
                COLUMN_JOURNAL_VOLUME_OR_NUMBER + " TEXT, " +
                COLUMN_JOURNAL_SERIES + " TEXT, " +
                COLUMN_JOURNAL_NOTES + " TEXT, " +
                COLUMN_JOURNAL_MATERIAL_TYPE + " TEXT, " +
                COLUMN_JOURNAL_SUBJECT + " TEXT " +
                ");";

        db.execSQL(journal);

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
                COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        onCreate(db);
    }

    public void addJournal(Journal journal){
        ContentValues values = new ContentValues();
        values.put(COLUMN_JOURNAL_ID,journal.get_id());
        values.put(COLUMN_JOURNAL_ARTICLE_TITLE,journal.get_article_title());
        values.put(COLUMN_JOURNAL_JOURNAL_TITLE,journal.get_journal_title());
        values.put(COLUMN_JOURNAL_AUTHOR,journal.get_author());
        values.put(COLUMN_JOURNAL_DATE_PUBLISHED,journal.get_date_published());
        values.put(COLUMN_JOURNAL_VOLUME_OR_NUMBER,journal.get_volume_or_number());
        values.put(COLUMN_JOURNAL_SERIES,journal.get_series());
        values.put(COLUMN_JOURNAL_NOTES,journal.get_notes());
        values.put(COLUMN_JOURNAL_MATERIAL_TYPE,journal.get_material_type());
        values.put(COLUMN_JOURNAL_SUBJECT,journal.get_subject());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_JOURNAL, null, values);
        db.close();
    }

    public void addBook(Book book){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID,book.get_id());
        values.put(COLUMN_BOOK_TITLE,book.get_title());
        values.put(COLUMN_BOOK_AUTHOR,book.get_author());
        values.put(COLUMN_BOOK_PUBLICATION,book.get_publication());
        values.put(COLUMN_BOOK_PHYSICAL_DESCRIPTION,book.get_physical_description());
        values.put(COLUMN_BOOK_SERIES,book.get_series());
        values.put(COLUMN_BOOK_NOTES,book.get_notes());
        values.put(COLUMN_BOOK_ISBN,book.get_isbn());
        values.put(COLUMN_BOOK_CALL_NUMBER,book.get_call_number());
        values.put(COLUMN_BOOK_MATERIAL_TYPE,book.get_material_type());
        values.put(COLUMN_BOOK_SUBJECT, book.get_subject());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    public void addLocation(Location location){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION_ACCESSION_NUMBER,location.get_accession_number());
        values.put(COLUMN_LOCATION_LOCATION,location.get_location());
        values.put(COLUMN_LOCATION_SECTION,location.get_section());
        values.put(COLUMN_LOCATION_STATUS,location.get_status());
        values.put(COLUMN_LOCATION_REFERENCE,location.get_reference());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOCATION, null, values);
        db.close();
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

    public Journal getJournalItem(String id){
        Journal journal = new Journal();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_JOURNAL + " WHERE id = '" + id + "'";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int iJournalID = c.getColumnIndex(COLUMN_JOURNAL_ID);
        int iJournalArticleTitle = c.getColumnIndex(COLUMN_JOURNAL_ARTICLE_TITLE);
        int iJournalJournalTitle = c.getColumnIndex(COLUMN_JOURNAL_JOURNAL_TITLE);
        int iJournalAuthor = c.getColumnIndex(COLUMN_JOURNAL_AUTHOR);
        int iJournalDatePublished = c.getColumnIndex(COLUMN_JOURNAL_DATE_PUBLISHED);
        int iJournalVolume = c.getColumnIndex(COLUMN_JOURNAL_VOLUME_OR_NUMBER);
        int iJournalSeries = c.getColumnIndex(COLUMN_JOURNAL_SERIES);
        int iJournalNotes = c.getColumnIndex(COLUMN_JOURNAL_NOTES);
        int iJournalMaterialType = c.getColumnIndex(COLUMN_JOURNAL_MATERIAL_TYPE);
        int iJournalSubject = c.getColumnIndex(COLUMN_JOURNAL_SUBJECT);

        journal.set_id(c.getString(iJournalID));
        journal.set_article_title(c.getString(iJournalArticleTitle));
        journal.set_journal_title(c.getString(iJournalJournalTitle));
        journal.set_author(c.getString(iJournalAuthor));
        journal.set_date_published(c.getString(iJournalDatePublished));
        journal.set_volume_or_number(c.getString(iJournalVolume));
        journal.set_series(c.getString(iJournalSeries));
        journal.set_notes(c.getString(iJournalNotes));
        journal.set_material_type(c.getString(iJournalMaterialType));
        journal.set_subject(c.getString(iJournalSubject));

        return journal;
    }

    public ArrayList<SearchResults> recentSearch(){
        ArrayList<SearchResults> lists = new ArrayList<SearchResults>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_RECENT + " ORDER BY id DESC";
        Cursor a = db.rawQuery(query, null);
        int iItem = a.getColumnIndex(COLUMN_RECENT_ITEM);
        int iItemType = a.getColumnIndex(COLUMN_RECENT_ITEM_TYPE);

        String item = null, type = null, q = null;
        if(a != null){
            for(a.moveToFirst(); !a.isAfterLast(); a.moveToNext()){
                item = a.getString(iItem);
                type = a.getString(iItemType);

                if(type.equals("1")){
                    q = "SELECT * FROM " + TABLE_BOOK + " WHERE id = '" + item + "'";
                    Cursor b = db.rawQuery(q,null);
                    b.moveToFirst();
                    int iBookID = b.getColumnIndex(COLUMN_BOOK_ID);
                    int iBookTitle = b.getColumnIndex(COLUMN_BOOK_TITLE);
                    int iBookAuthor = b.getColumnIndex(COLUMN_BOOK_AUTHOR);
                    int iBookCallNumber = b.getColumnIndex(COLUMN_BOOK_CALL_NUMBER);

                    SearchResults list = new SearchResults();
                    list.set_id(b.getString(iBookID));
                    list.set_author(b.getString(iBookAuthor));
                    list.set_call_number(b.getString(iBookCallNumber));
                    list.set_title(b.getString(iBookTitle));
                    list.set_status("Available");
                    list.set_type("book");

                    lists.add(list);
                }else if(type.equals("2")){
                    q = "SELECT * FROM " + TABLE_JOURNAL + " WHERE id = '" + item + "'";
                    Cursor b = db.rawQuery(q,null);
                    b.moveToFirst();
                    int iJournalID = b.getColumnIndex(COLUMN_JOURNAL_ID);
                    int iJournalTitle = b.getColumnIndex(COLUMN_JOURNAL_ARTICLE_TITLE);
                    int iJournalAuthor = b.getColumnIndex(COLUMN_JOURNAL_AUTHOR);

                    SearchResults list = new SearchResults();
                    list.set_id(b.getString(iJournalID));
                    list.set_author(b.getString(iJournalAuthor));
                    list.set_call_number("");
                    list.set_title(b.getString(iJournalTitle));
                    list.set_status("Available");
                    list.set_type("journal");

                    lists.add(list);
                }
            }
        }

        return lists;
    }

    public ArrayList<SearchResults> search(String search, String category){
        ArrayList<SearchResults> lists = new ArrayList<SearchResults>();
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();
        String bquery = "" , jquery = "";

        if(category.equals("title")){
            bquery = "SELECT * FROM " + TABLE_BOOK + " WHERE lower(title) LIKE '%" + search +"%'";
            jquery = "SELECT * FROM " + TABLE_JOURNAL + " WHERE lower(article_title) LIKE '%" + search +"%'";
        }else if(category.equals("author")){
            bquery = "SELECT * FROM " + TABLE_BOOK + " WHERE lower(author) LIKE '%" + search +"%'";
            jquery = "SELECT * FROM " + TABLE_JOURNAL + " WHERE lower(author) LIKE '%" + search +"%'";
        }else if(category.equals("subject")){
            bquery = "SELECT * FROM " + TABLE_BOOK + " WHERE lower(subject) LIKE '%" + search +"%'";
            jquery = "SELECT * FROM " + TABLE_JOURNAL + " WHERE lower(subject) LIKE '%" + search +"%'";
        }else if(category.equals("call number")){
            bquery = "SELECT * FROM " + TABLE_BOOK + " WHERE lower(call_number) LIKE '%" + search +"%'";
        }

        Cursor b = db.rawQuery(bquery, null);
        int iBookID = b.getColumnIndex(COLUMN_BOOK_ID);
        int iBookTitle = b.getColumnIndex(COLUMN_BOOK_TITLE);
        int iBookAuthor = b.getColumnIndex(COLUMN_BOOK_AUTHOR);
        int iBookCallNumber = b.getColumnIndex(COLUMN_BOOK_CALL_NUMBER);

        if(b != null){
            for(b.moveToFirst(); !b.isAfterLast(); b.moveToNext()){
                SearchResults list = new SearchResults();
                list.set_id(b.getString(iBookID));
                list.set_author(b.getString(iBookAuthor));
                list.set_call_number(b.getString(iBookCallNumber));
                list.set_title(b.getString(iBookTitle));
                list.set_status("Available");
                list.set_type("book");

                lists.add(list);
            }
        }

        if(!category.equals("call number")) {
            Cursor j = db1.rawQuery(jquery, null);
            int iJournalID = j.getColumnIndex(COLUMN_JOURNAL_ID);
            int iJournalTitle = j.getColumnIndex(COLUMN_JOURNAL_ARTICLE_TITLE);
            int iJournalAuthor = j.getColumnIndex(COLUMN_JOURNAL_AUTHOR);

            if (j != null) {
                for (j.moveToFirst(); !j.isAfterLast(); j.moveToNext()) {
                    SearchResults list = new SearchResults();
                    list.set_id(j.getString(iJournalID));
                    list.set_author(j.getString(iJournalAuthor));
                    list.set_call_number("");
                    list.set_title(j.getString(iJournalTitle));
                    list.set_status("Available");
                    list.set_type("journal");

                    lists.add(list);
                }
            }
        }

        return lists;
    }

    public void truncateDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        onCreate(db);
    }
}
