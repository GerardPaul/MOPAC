package com.gpplworx.mopac.mopac;

public class SearchResults {

    private String _id;
    private String _title;
    private String _author;
    private String _call_number;
    private String _status;
    private String _type;

    public SearchResults(){

    }

    public SearchResults(String title, String author, String call_number, String status) {
        this._title = title;
        this._author = author;
        this._call_number = call_number;
        this._status = status;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
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

    public String get_call_number() {
        return _call_number;
    }

    public void set_call_number(String _call_number) {
        this._call_number = _call_number;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }
}
