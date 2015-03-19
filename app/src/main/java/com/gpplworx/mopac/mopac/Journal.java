package com.gpplworx.mopac.mopac;

public class Journal {

    private int _id;
    private String _article_title;
    private String _journal_title;
    private String _author;
    private String _date_published;
    private String _volume_or_number;
    private String _series;
    private String _notes;
    private String _material_type;
    private String _subject;

    public Journal(){

    }

    public Journal(String article_title, String journal_title, String author, String date_published,
                   String volume_or_number, String series, String notes, String material_type, String subject) {
        this._article_title = article_title;
        this._journal_title = journal_title;
        this._author = author;
        this._date_published = date_published;
        this._volume_or_number = volume_or_number;
        this._series = series;
        this._notes = notes;
        this._material_type = material_type;
        this._subject = subject;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_article_title() {
        return _article_title;
    }

    public void set_article_title(String _article_title) {
        this._article_title = _article_title;
    }

    public String get_journal_title() {
        return _journal_title;
    }

    public void set_journal_title(String _journal_title) {
        this._journal_title = _journal_title;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public String get_date_published() {
        return _date_published;
    }

    public void set_date_published(String _date_published) {
        this._date_published = _date_published;
    }

    public String get_volume_or_number() {
        return _volume_or_number;
    }

    public void set_volume_or_number(String _volume_or_number) {
        this._volume_or_number = _volume_or_number;
    }

    public String get_series() {
        return _series;
    }

    public void set_series(String _series) {
        this._series = _series;
    }

    public String get_notes() {
        return _notes;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public String get_material_type() {
        return _material_type;
    }

    public void set_material_type(String _material_type) {
        this._material_type = _material_type;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    }
}
