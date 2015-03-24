package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class JournalDetails extends Activity {

    private String id;
    private Catalog catalog;
    private TextView article_title, journal_title, author, date_published, volume, series, notes, material_type, subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);

        getIntents();

        catalog = new Catalog(JournalDetails.this, null, null, 1);
        Journal journal = new Journal();
        journal = catalog.getJournalItem(id);

        article_title = (TextView) findViewById(R.id.tv_article_title);
        journal_title = (TextView) findViewById(R.id.tv_journal_title);
        author = (TextView) findViewById(R.id.tv_author);
        date_published = (TextView) findViewById(R.id.tv_date_published);
        volume = (TextView) findViewById(R.id.tv_volume);
        series = (TextView) findViewById(R.id.tv_series);
        notes = (TextView) findViewById(R.id.tv_notes);
        material_type = (TextView) findViewById(R.id.tv_material_type);
        subjects = (TextView) findViewById(R.id.tv_subject);

        article_title.setText(journal.get_article_title());
        journal_title.setText(journal.get_journal_title());
        author.setText(journal.get_author());
        date_published.setText(journal.get_date_published());
        volume.setText(journal.get_volume_or_number());
        series.setText(journal.get_series());
        notes.setText(journal.get_notes());
        material_type.setText(journal.get_material_type());
        subjects.setText(journal.get_subject());

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

                TextView accession = new TextView(this);
                accession.setText("Accession Number: " + locations.get(i).get_accession_number());
                accession.setTextColor(getResources().getColor(R.color.white));

                TextView location = new TextView(this);
                location.setText("Location: " + locations.get(i).get_location());
                location.setTextColor(getResources().getColor(R.color.white));

                TextView section = new TextView(this);
                section.setText("Section: " + locations.get(i).get_section());
                section.setTextColor(getResources().getColor(R.color.white));

                TextView status = new TextView(this);
                status.setText("Status: " + locations.get(i).get_status());
                status.setTextColor(getResources().getColor(R.color.white));

                container.addView(accession);
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
    }

}
