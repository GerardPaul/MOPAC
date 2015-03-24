package com.gpplworx.mopac.mopac;

public class RecentItem {
    private String _id;
    private String _item;
    private String _type;

    public RecentItem(){

    }

    public RecentItem(String type, String item) {
        this._type = type;
        this._item = item;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_item() {
        return _item;
    }

    public void set_item(String _item) {
        this._item = _item;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }
}
