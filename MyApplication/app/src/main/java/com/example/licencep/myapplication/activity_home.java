package com.example.licencep.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_home extends AppCompatActivity {

    int CODE_RECUP = 0;
    TextView tv_recup;
    String recup_prev_text = "text recupere : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String message = intent.getStringExtra("login");


        TextView textView = (TextView)findViewById(R.id.tv_text2);

        tv_recup = (TextView)findViewById(R.id.tv_recup);
        tv_recup.setText(recup_prev_text);

        Button map = (Button)findViewById(R.id.map);
        Button nav = (Button)findViewById(R.id.nav);
        Button coucou = (Button)findViewById(R.id.coucou);
        final Button recup = (Button)findViewById(R.id.btn_recup);
        Button paris = (Button)findViewById(R.id.Paris);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentActionView("geo:48.860522, 2.357919?q=60+Rue+Réaumur+75003+Paris");
            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentActionView("https://www.google.fr/");
            }
        });

        coucou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("monaction.coucou");

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        recup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_recup.class);
                startActivityForResult(intent, CODE_RECUP);
            }
        });

        paris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), paris.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == CODE_RECUP) {

            String s = data.getStringExtra("message");
            tv_recup.setText(recup_prev_text + s);

        }
    }

    public void startIntentActionView(String val){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(val));

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

}
