package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Settings extends Activity {

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private String url = "http://192.168.43.62/mopac/data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getIntents();

        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent i = new Intent(Settings.this, URLSetting.class);
                        startActivity(i);
                        return true;
                    }
                }
        );

        final ListView settings_list = (ListView) findViewById(R.id.list_settings);
        String[] values = new String[]{"Update Catalog", "About"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,values);
        settings_list.setAdapter(adapter);

        settings_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String itemValue = (String) settings_list.getItemAtPosition(position);
                        if(itemValue.equals("Update Catalog")){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Settings.this);
                            alertDialog.setTitle("Update Catalog");
                            alertDialog.setMessage("Update catalog data?");
                            alertDialog.setIcon(R.drawable.ic_launcher);
                            alertDialog.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new AttemptUpdate().execute();
                                        }

                                    });
                            alertDialog.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            alertDialog.show();
                        }else if(itemValue.equals("About")){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Settings.this);
                            alertDialog.setTitle("About MOPAC");
                            alertDialog.setMessage("This app allows MSU students and researchers to search our database for resources in our various library units.");
                            alertDialog.setIcon(R.drawable.ic_launcher);
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
                }
        );
    }

    public void getIntents(){
        Bundle data = getIntent().getExtras();
        if(data==null){
            return;
        }
        url = "http://" + data.getString("url") + "/mopac/data.php";
    }

    class AttemptUpdate extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Settings.this);
            pDialog.setMessage("Updating catalog...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;

            try {
                List<NameValuePair> p = new ArrayList<NameValuePair>();
                p.add(new BasicNameValuePair("catalog", url));

                JSONObject json = jsonParser.makeHttpRequest(url, p, Settings.this);
                if (json == null)
                    return "Cannot connect to server.";

                success = json.getInt("success");
                if(success == 1){
                    Catalog catalog = new Catalog(Settings.this,null,null,1);
                    catalog.truncateDB();

                    JSONArray books = json.getJSONArray("book");
                    JSONArray journals = json.getJSONArray("journal");

                    for(int i = 0; i < books.length(); i++){
                        JSONObject c = books.getJSONObject(i);
                        Book book = new Book();
                        book.set_title(c.getString("title"));
                        book.set_author(c.getString("author"));
                        book.set_edition(c.getString("edition"));
                        book.set_publication(c.getString("publication"));
                        book.set_physical_description(c.getString("physical_description"));
                        book.set_series(c.getString("series"));
                        book.set_notes(c.getString("notes"));
                        book.set_isbn(c.getString("isbn"));
                        book.set_call_number(c.getString("call_number"));
                        book.set_material_type(c.getString("material_type"));
                        book.set_subject(c.getString("subject"));

                        catalog.addBook(book);
                    }

                    for(int i = 0; i < journals.length(); i++){
                        JSONObject c = journals.getJSONObject(i);
                        Journal journal = new Journal();
                        journal.set_article_title(c.getString("article_title"));
                        journal.set_journal_title(c.getString("journal_title"));
                        journal.set_author(c.getString("author"));
                        journal.set_date_published(c.getString("date_published"));
                        journal.set_volume_or_number(c.getString("volume_or_number"));
                        journal.set_series(c.getString("series"));
                        journal.set_notes(c.getString("notes"));
                        journal.set_material_type(c.getString("material_type"));
                        journal.set_subject(c.getString("subject"));

                        catalog.addJournal(journal);
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
            pDialog.dismiss();
            if (s != null && !s.equals("success")) {
                Toast.makeText(Settings.this, s, Toast.LENGTH_LONG).show();
            }
        }
    }

}
