package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Settings extends Activity {

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private String url = "http://192.168.43.62/mopac/data.php";
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getIntents();

        final ListView settings_list = (ListView) findViewById(R.id.list_settings);
        String[] values = new String[]{"Recent Searches", "About"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,values);
        settings_list.setAdapter(adapter);

        settings_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String itemValue = (String) settings_list.getItemAtPosition(position);
                        if(itemValue.equals("About")){
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
                        }else if(itemValue.equals("Recent Searches")){
                            Intent i = new Intent(Settings.this, Recent.class);
                            i.putExtra("ip",ip);
                            finish();
                            startActivity(i);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
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
        ip = data.getString("ip");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Settings.this,MainApp.class);
        i.putExtra("url",ip);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
