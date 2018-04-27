package com.example.licencep.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activity_recup extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recup);

        TextView textView = (TextView)findViewById(R.id.recupr_textView);
        textView.setText("text to return");

        editText = (EditText)findViewById(R.id.recup_editText);

        Button button = (Button)findViewById(R.id.recup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String msg = editText.getText().toString();

                if(msg.isEmpty()){
                    msg = "no text enter";
                }

                intent.putExtra("message", msg);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
