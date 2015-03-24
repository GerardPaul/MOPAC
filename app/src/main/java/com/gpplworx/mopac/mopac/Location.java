package com.gpplworx.mopac.mopac;

public class Location {

    private String _id;
    private String _accession_number;
    private String _location;
    private String _section;
    private String _status;
    private String _reference;

    public Location(){

    }

    public Location(String accession_number, String location, String section, String status, String reference) {
        this._accession_number = accession_number;
        this._location = location;
        this._section = section;
        this._status = status;
        this._reference = reference;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_accession_number() {
        return _accession_number;
    }

    public void set_accession_number(String _accession_number) {
        this._accession_number = _accession_number;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_section() {
        return _section;
    }

    public void set_section(String _section) {
        this._section = _section;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_reference() {
        return _reference;
    }

    public void set_reference(String _reference) {
        this._reference = _reference;
    }
}
