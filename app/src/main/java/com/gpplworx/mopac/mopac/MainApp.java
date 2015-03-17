package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        String[] category={"Author", "Title", "Category"};
        ArrayAdapter<String> stringArrayAdapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category);
        Spinner spinner = (Spinner)  findViewById(R.id.category);
        spinner.setAdapter(stringArrayAdapter);

        EditText search = (EditText) findViewById(R.id.search_item);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    public void performSearch(){

    }
}
