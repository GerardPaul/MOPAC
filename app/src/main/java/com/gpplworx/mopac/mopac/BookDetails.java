package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BookDetails extends Activity {

    private String id, url, ip;
    private Catalog catalog;
    private TextView title, author, publication, physical_description, isbn, call_number, material_type, subjects;
    private Button available;

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getIntents();
        loadDetails();

    }

    private void loadDetails(){
        catalog = new Catalog(BookDetails.this, null, null, 1);
        Book book = new Book();
        book = catalog.getBookItem(id);

        title = (TextView) findViewById(R.id.tv_title);
        author = (TextView) findViewById(R.id.tv_author);
        publication = (TextView) findViewById(R.id.tv_publication);
        physical_description = (TextView) findViewById(R.id.tv_physical_description);
        isbn = (TextView) findViewById(R.id.tv_isbn);
        call_number = (TextView) findViewById(R.id.tv_call_number);
        material_type = (TextView) findViewById(R.id.tv_material_type);
        subjects = (TextView) findViewById(R.id.tv_subject);

        title.setText(book.get_title());
        author.setText(book.get_author());
        publication.setText(book.get_publication());
        physical_description.setText(book.get_physical_description());
        isbn.setText(book.get_isbn());
        call_number.setText(book.get_call_number());
        material_type.setText(book.get_material_type());
        subjects.setText(book.get_subject());

        available = (Button) findViewById(R.id.btn_available);

        available.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AttemptGetLocation().execute(id);
                    }
                }
        );
    }

    class AttemptGetLocation extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BookDetails.this);
            pDialog.setMessage("Retrieving location...");
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

                JSONObject json = jsonParser.makeHttpRequest(url, p, BookDetails.this);
                if (json == null)
                    return "Cannot connect to server.";

                success = json.getInt("success");
                if(success == 1){
                    Catalog catalog = new Catalog(BookDetails.this,null,null,1);

                    JSONArray location = json.getJSONArray("location");

                    for(int i = 0; i < location.length(); i++){
                        JSONObject c = location.getJSONObject(i);
                        Location loc = new Location();
                        loc.set_id(c.getString("itemnumber"));
                        loc.set_location(c.getString("location"));
                        loc.set_section(c.getString("section"));
                        loc.set_status(c.getString("status"));
                        loc.set_reference(c.getString("reference"));

                        catalog.addLocation(loc);
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
                Toast.makeText(BookDetails.this, s, Toast.LENGTH_LONG).show();
                available.setVisibility(View.GONE);
                displayLocation();
            }
        }
    }

    private void displayLocation(){
        ArrayList<Location> locations = new ArrayList<Location>();
        locations = catalog.getLocationItem(id);

        LinearLayout container = (LinearLayout) findViewById(R.id.body);
        TextView loc = new TextView(this);
        loc.setText("Location/s:");
        loc.setTextColor(getResources().getColor(R.color.white));
        loc.setTypeface(null, Typeface.BOLD);
        loc.setBackgroundColor(getResources().getColor(R.color.darker_red));
        container.addView(loc);
        if(locations.size()>0){
            for(int i = 0; i < locations.size(); i++){
                TextView line = new TextView(this);
                line.setText("___________________________");
                line.setTextColor(getResources().getColor(R.color.white));

                TextView location = new TextView(this);
                location.setText("Location: " + locations.get(i).get_location());
                location.setTextColor(getResources().getColor(R.color.white));

                TextView section = new TextView(this);
                section.setText("Section: " + locations.get(i).get_section());
                section.setTextColor(getResources().getColor(R.color.white));

                TextView status = new TextView(this);
                status.setText("Status: " + locations.get(i).get_status());
                status.setTextColor(getResources().getColor(R.color.white));

                container.addView(location);
                container.addView(section);
                container.addView(status);
                container.addView(line);
            }
        }
    }

    public void getIntents(){
        Bundle data = getIntent().getExtras();
        if(data==null){
            return;
        }
        id = data.getString("id");
        ip = data.getString("url");
        url = "http://" + data.getString("url") + "/mopac/location.php";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
