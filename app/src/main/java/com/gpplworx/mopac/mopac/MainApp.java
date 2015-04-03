package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainApp extends Activity {

    private Spinner spinner;
    private Button settings;
    private EditText search, query;

    private Catalog catalog;
    private LinearLayout home;
    private TextView no_result;
    private ListView list;
    private SearchListAdapter searchListAdapter;

    private ProgressDialog pDialog;

    private String searchItem, category;
    private String url = "http://192.168.43.62/mopac/search.php";
    private String urlDetails = "http://192.168.43.62/mopac/details.php";

    private ArrayList<SearchResults> lists = new ArrayList<SearchResults>();

    private JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        getIntents();

        ImageView logo = (ImageView) findViewById(R.id.imageView2);
        logo.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent i = new Intent(MainApp.this, URLSetting.class);
                        finish();
                        startActivity(i);
                        return true;
                    }
                }
        );

        initialize();
    }

    public void getIntents(){
        Bundle data = getIntent().getExtras();
        if(data==null){
            return;
        }
        url = "http://" + data.getString("url") + "/mopac/search.php";
        urlDetails = "http://" + data.getString("url") + "/mopac/details.php";
    }

    public void performSearch(){
        query = (EditText) findViewById(R.id.search_item);
        searchItem = query.getText().toString().toLowerCase();
        category = spinner.getSelectedItem().toString().toLowerCase();

        if(searchItem.equals("")){
            Toast.makeText(getBaseContext(), "Please enter your query.", Toast.LENGTH_SHORT).show();
        }else {
            new AttemptSearch().execute(searchItem, category);
        }
    }

    private void displaySearchItems(){
        home = (LinearLayout) findViewById(R.id.home);
        no_result = (TextView) findViewById(R.id.no_result);
        list = (ListView) findViewById(R.id.search_items);

        setInvisibleList();
        setInvisibleNoResult();
        setInvisibleHome();

        if(lists.size() == 0){
            setInvisibleList();
            setVisibleNoResult();
        }else{
            setInvisibleNoResult();
            setVisibleList();

            catalog = new Catalog(MainApp.this,null,null,1);

            searchListAdapter = new SearchListAdapter(MainApp.this, lists);
            list.setAdapter(searchListAdapter);

            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object o = searchListAdapter.getItem(position);
                            SearchResults e = (SearchResults) o;
                            String itemID = e.get_id();

                            RecentItem recent = new RecentItem();
                            recent.set_item(itemID);
                            recent.set_type("1");
                            catalog.addRecent(recent);

                            new AttemptGetDetails().execute(itemID);
                            Intent i = new Intent(MainApp.this, BookDetails.class);
                            i.putExtra("id", itemID);
                            startActivity(i);
                        }
                    }
            );
        }
    }

    class AttemptGetDetails extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainApp.this);
            pDialog.setMessage("Retrieving details...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;

            try {
                List<NameValuePair> p = new ArrayList<NameValuePair>();
                p.add(new BasicNameValuePair("id", params[0]));

                JSONObject json = jsonParser.makeHttpRequest(urlDetails, p, MainApp.this);
                if (json == null)
                    return "Cannot connect to server.";

                success = json.getInt("success");
                if(success == 1){
                    Catalog catalogDB = new Catalog(MainApp.this,null,null,1);

                    JSONArray details = json.getJSONArray("details");

                    JSONObject c = details.getJSONObject(0);
                    Book book = new Book();
                    book.set_id(c.getString("id"));
                    book.set_author(c.getString("author"));
                    book.set_title(c.getString("title"));
                    book.set_material_type(c.getString("itemType"));
                    book.set_publication(c.getString("publication"));
                    book.set_physical_description(c.getString("physical"));
                    book.set_call_number(c.getString("callnumber"));
                    book.set_isbn(c.getString("isbn"));
                    book.set_subject(c.getString("subjects"));
                    book.set_series(c.getString("itemnumbers"));

                    catalogDB.addBook(book);

                    Intent i = new Intent(MainApp.this, BookDetails.class);
                    i.putExtra("id", params[0]);
                    startActivity(i);

                    return json.getString("message");
                }else{
                    return json.getString("message");
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if (s != null && !s.equals("success")) {
                Toast.makeText(MainApp.this, s, Toast.LENGTH_LONG).show();
            }
        }
    }

    class AttemptSearch extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainApp.this);
            pDialog.setMessage("Retrieving search items...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;

            try {
                List<NameValuePair> p = new ArrayList<NameValuePair>();
                p.add(new BasicNameValuePair("keyword", params[0]));
                p.add(new BasicNameValuePair("search", params[1]));

                JSONObject json = jsonParser.makeHttpRequest(url, p, MainApp.this);
                if (json == null)
                    return "Cannot connect to server.";

                success = json.getInt("success");
                if(success == 1){
                    JSONArray searchItems = json.getJSONArray("searchItems");

                    lists.clear();//clear search list before populating

                    for(int i = 0; i < searchItems.length(); i++){
                        JSONObject c = searchItems.getJSONObject(i);
                        SearchResults list = new SearchResults();
                        list.set_id(c.getString("id"));
                        list.set_author(c.getString("author"));
                        list.set_title(c.getString("title"));
                        list.set_status("Available");

                        String type = c.getString("itemType").toUpperCase();
                        if(type.contains("BK") || type.contains("BO")){
                            list.set_type("book");
                        }else{
                            list.set_type("journal");
                        }

                        lists.add(list);
                    }
                    return json.getString("message");
                }else{
                    return json.getString("message");
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            displaySearchItems();
            pDialog.dismiss();
            if (s != null && !s.equals("success")) {
                Toast.makeText(MainApp.this, s, Toast.LENGTH_LONG).show();
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
        String[] category={"Title", "Author", "Subject"};
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
                        finish();
                        startActivity(i);
                    }
                }
        );
    }
}
