package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Recent extends Activity {

    private Catalog catalog;
    private TextView no_result;
    private ListView list;

    private String url = "http://192.168.43.62/mopac/data.php";
    private String ip = "192.168.43.62";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        getIntents();

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

                            Intent i = new Intent(Recent.this, BookDetails.class);
                            i.putExtra("id", itemID);
                            i.putExtra("url", ip);
                            startActivity(i);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        }
                    }
            );
        }
        Button clear = (Button) findViewById(R.id.btn_clear);
        clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Recent.this);
                        alertDialog.setTitle("Clear Recent List");
                        alertDialog.setMessage("Do you want to clear recent searches?");
                        alertDialog.setIcon(R.drawable.ic_launcher);
                        alertDialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        catalog.truncateDB();
                                        Toast.makeText(Recent.this, "Recent searches cleared.", Toast.LENGTH_LONG).show();
                                        Intent i = getIntent();
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        finish();
                                        startActivity(i);
                                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
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
                    }
                }
        );
    }
    private void setVisibleList(){ list.setVisibility(View.VISIBLE); }
    private void setVisibleNoResult(){ no_result.setVisibility(View.VISIBLE); }
    private void setInvisibleList(){ list.setVisibility(View.INVISIBLE); }
    private void setInvisibleNoResult(){ no_result.setVisibility(View.INVISIBLE); }

    public void getIntents(){
        Bundle data = getIntent().getExtras();
        if(data==null){
            return;
        }
        url = "http://" + data.getString("url") + "/mopac/data.php";
        ip = data.getString("ip");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Recent.this,MainApp.class);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
