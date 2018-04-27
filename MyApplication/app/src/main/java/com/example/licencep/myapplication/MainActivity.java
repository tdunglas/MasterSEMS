package com.example.licencep.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText)findViewById(R.id.et_login);
        password = (EditText)findViewById(R.id.et_password);

        Button send = (Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {

            String LOG = "sems";
            String PASS = "android";

            @Override
            public void onClick(View v) {

                Context ctx = getApplicationContext();
                String val_login = login.getText().toString();
                String val_password = password.getText().toString();
                int duration = Toast.LENGTH_SHORT;
                String text;

                if(val_login.equals(LOG) && val_password.equals(PASS)){

                    Intent intent = new Intent(ctx, activity_home.class);
                    intent.putExtra("login", val_login);
                    intent.putExtra("password", val_password);
                    startActivity(intent);
                    text = "SUCCESS";
                }
                else{
                    text = "FAILURE";
                }

                Toast toast = Toast.makeText(ctx, text, duration);
                toast.show();
            }
        });

    }
}
