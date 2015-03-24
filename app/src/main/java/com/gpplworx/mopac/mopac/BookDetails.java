package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class BookDetails extends Activity {

    private String id;
    private Catalog catalog;
    private TextView title, author, edition, publication, physical_description, series, notes, isbn, call_number, material_type, subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getIntents();

        catalog = new Catalog(BookDetails.this, null, null, 1);
        Book book = new Book();
        book = catalog.getBookItem(id);

        title = (TextView) findViewById(R.id.tv_title);
        author = (TextView) findViewById(R.id.tv_author);
        edition = (TextView) findViewById(R.id.tv_edition);
        publication = (TextView) findViewById(R.id.tv_publication);
        physical_description = (TextView) findViewById(R.id.tv_physical_description);
        series = (TextView) findViewById(R.id.tv_series);
        notes = (TextView) findViewById(R.id.tv_notes);
        isbn = (TextView) findViewById(R.id.tv_isbn);
        call_number = (TextView) findViewById(R.id.tv_call_number);
        material_type = (TextView) findViewById(R.id.tv_material_type);
        subjects = (TextView) findViewById(R.id.tv_subject);

        title.setText(book.get_title());
        author.setText(book.get_author());
        edition.setText(book.get_edition());
        publication.setText(book.get_publication());
        physical_description.setText(book.get_physical_description());
        series.setText(book.get_series());
        notes.setText(book.get_notes());
        isbn.setText(book.get_isbn());
        call_number.setText(book.get_call_number());
        material_type.setText(book.get_material_type());
        subjects.setText(book.get_subject());

        ArrayList<Location> locations = new ArrayList<Location>();
        locations = catalog.getLocationItem(id);

        LinearLayout container = (LinearLayout) findViewById(R.id.body);
        TextView loc = new TextView(this);
        loc.setText("Location/s:");
        loc.setTextColor(getResources().getColor(R.color.white));
        loc.setTypeface(null,Typeface.BOLD);
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
