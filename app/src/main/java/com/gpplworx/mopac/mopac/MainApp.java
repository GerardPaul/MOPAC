package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainApp extends Activity {

    private Spinner spinner;
    private Button settings;
    private EditText search;

    private Catalog catalog;
    private LinearLayout home;
    private TextView no_result;
    private ListView list;

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

        search.toLowerCase();

        if(search.equals("")){
            Toast.makeText(getBaseContext(), "Please enter your query.", Toast.LENGTH_SHORT).show();
        }else {
            home = (LinearLayout) findViewById(R.id.home);
            no_result = (TextView) findViewById(R.id.no_result);
            list = (ListView) findViewById(R.id.search_items);

            setInvisibleList();
            setInvisibleNoResult();
            setInvisibleHome();

            catalog = new Catalog(MainApp.this, null, null, 1);

            ArrayList<SearchResults> result = new ArrayList<SearchResults>();
            result = catalog.search(search, category.toLowerCase());

            if(result.size() == 0){
                setVisibleNoResult();
            }else{
                setInvisibleNoResult();
                setVisibleList();

                final SearchListAdapter searchListAdapter = new SearchListAdapter(MainApp.this, result);
                list.setAdapter(searchListAdapter);

                list.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Object o = searchListAdapter.getItem(position);
                                SearchResults e = (SearchResults) o;
                                String itemID = e.get_id();
                                String type = e.get_type();

                                if(type.equals("book")){
                                    catalog.getBookItem(itemID);
                                }else if(type.equals("journal")){
                                    catalog.getJournalItem(itemID);
                                }
                            }
                        }
                );
            }
        }
    }

    private void setVisibleHome(){ home.setVisibility(View.VISIBLE); }
    private void setVisibleList(){ list.setVisibility(View.VISIBLE); }
    private void setVisibleNoResult(){ no_result.setVisibility(View.VISIBLE); }
    private void setInvisibleHome(){ home.setVisibility(View.INVISIBLE);}
    private void setInvisibleList(){ list.setVisibility(View.INVISIBLE); }
    private void setInvisibleNoResult(){ no_result.setVisibility(View.INVISIBLE); }

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
                        Intent i = new Intent(MainApp.this,Settings.class);
                        startActivity(i);
                    }
                }
        );
    }
}
