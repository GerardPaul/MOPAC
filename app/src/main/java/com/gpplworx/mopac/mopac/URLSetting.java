package com.gpplworx.mopac.mopac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class URLSetting extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsetting);

        final EditText url = (EditText) findViewById(R.id.url);
        Button set = (Button) findViewById(R.id.btn_set);

        set.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ip = url.getText().toString();
                        Intent i = new Intent(URLSetting.this, MainApp.class);
                        i.putExtra("url", ip);
                        Toast.makeText(URLSetting.this, "URL updated!", Toast.LENGTH_LONG).show();
                        finish();
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        startActivity(i);
                    }
                }
        );
    }

}
