package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


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
    }

}
