package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Recent extends Activity {

    private Catalog catalog;
    private TextView no_result;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        no_result = (TextView) findViewById(R.id.no_result);
        list = (ListView) findViewById(R.id.search_items);

        setInvisibleList();
        setInvisibleNoResult();

        catalog = new Catalog(Recent.this, null, null, 1);

        ArrayList<SearchResults> result = new ArrayList<SearchResults>();
        result = catalog.recentSearch();

        if(result.size() == 0){
            setVisibleNoResult();
        }else {
            setInvisibleNoResult();
            setVisibleList();

            final SearchListAdapter searchListAdapter = new SearchListAdapter(Recent.this, result);
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
                                Intent i = new Intent(Recent.this, BookDetails.class);
                                i.putExtra("id", itemID);
                                startActivity(i);
                                //catalog.getBookItem(itemID);
                            }else if(type.equals("journal")){
                                Intent i = new Intent(Recent.this,JournalDetails.class);
                                i.putExtra("id",itemID);
                                startActivity(i);
                                //catalog.getJournalItem(itemID);
                            }
                        }
                    }
            );
        }

    }
    private void setVisibleList(){ list.setVisibility(View.VISIBLE); }
    private void setVisibleNoResult(){ no_result.setVisibility(View.VISIBLE); }
    private void setInvisibleList(){ list.setVisibility(View.INVISIBLE); }
    private void setInvisibleNoResult(){ no_result.setVisibility(View.INVISIBLE); }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Recent.this,MainApp.class);
        finish();
        startActivity(i);
    }
}
