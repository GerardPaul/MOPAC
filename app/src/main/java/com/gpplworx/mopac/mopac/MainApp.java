package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainApp extends Activity {

    private Spinner spinner;
    private Button settings;
    private EditText search;

    private Catalog catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        initialize();
    }

    public void performSearch(){
        EditText query = (EditText) findViewById(R.id.search_item);
        String search = query.getText().toString();
        String category = spinner.getSelectedItem().toString();

        LinearLayout home = (LinearLayout) findViewById(R.id.home);
        home.setVisibility(View.INVISIBLE);

        ListView list = (ListView) findViewById(R.id.search_items);
        list.setVisibility(View.VISIBLE);

        catalog = new Catalog(MainApp.this, null, null, 1);

        ArrayList<SearchResults> result = new ArrayList<SearchResults>();
        result = catalog.search(search, category);


        SearchListAdapter searchListAdapter = new SearchListAdapter(MainApp.this, result);
        list.setAdapter(searchListAdapter);
    }

    private void initialize(){
        //=================initialize spinner=========================
        String[] category={"Title", "Author", "Subject", "Call Number"};
        ArrayAdapter<String> stringArrayAdapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category);
        spinner = (Spinner)  findViewById(R.id.category);
        spinner.setAdapter(stringArrayAdapter);

        //================initialize search==========================
        search = (EditText) findViewById(R.id.search_item);
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
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        performSearch();
                        return true;
                    }
                }
                return false;
            }
        });

        //=================initialize button====================
        settings = (Button) findViewById(R.id.btn_settings);
        settings.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainApp.this);
                        alertDialog.setTitle("MOPAC Settings");
                        alertDialog.setMessage("Application Settings");
                        alertDialog.setIcon(R.drawable.ic_launcher);
                        alertDialog.setPositiveButton("Settings",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(MainApp.this,Settings.class);
                                        MainApp.this.finish();
                                        startActivity(i);
                                    }
                                });
                        alertDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        alertDialog.show();
                    }
                }
        );
    }
}
