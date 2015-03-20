package com.gpplworx.mopac.mopac;

public class Book {

    private int _id;
    private String _title;
    private String _author;
    private String _edition;
    private String _publication;
    private String _distribution;
    private String _physical_description;
    private String _series;
    private String _notes;
    private String _isbn;
    private String _call_number;
    private String _material_type;
    private String _subject;

    public Book(){

    }

    public Book(String title, String author, String edition, String publication,
                String distribution, String physical_description, String series,
                String notes, String isbn, String call_number,
                String material_type, String subject) {
        this._title = title;
        this._author = author;
        this._edition = edition;
        this._publication = publication;
        this._distribution = distribution;
        this._physical_description = physical_description;
        this._series = series;
        this._notes = notes;
        this._isbn = isbn;
        this._call_number = call_number;
        this._material_type = material_type;
        this._subject = subject;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public String get_edition() {
        return _edition;
    }

    public void set_edition(String _edition) {
        this._edition = _edition;
    }

    public String get_publication() {
        return _publication;
    }

    public void set_publication(String _publication) {
        this._publication = _publication;
    }

    public String get_distribution() {
        return _distribution;
    }

    public void set_distribution(String _distribution) {
        this._distribution = _distribution;
    }

    public String get_physical_description() {
        return _physical_description;
    }

    public void set_physical_description(String _physical_description) {
        this._physical_description = _physical_description;
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

    public String get_isbn() {
        return _isbn;
    }

    public void set_isbn(String _isbn) {
        this._isbn = _isbn;
    }

    public String get_call_number() {
        return _call_number;
    }

    public void set_call_number(String _call_number) {
        this._call_number = _call_number;
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
