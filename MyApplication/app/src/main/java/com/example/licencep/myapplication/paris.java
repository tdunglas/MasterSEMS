package com.example.licencep.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class paris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paris);

        setTitle("VISITE PARIS");

        ArrayList<String> items = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        ListView listView = (ListView)findViewById(R.id.listview);

        items.add("where I am");
        items.add("official website");
        items.add("places to visit");
        items.add("Paris description");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "id : " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);

                int val = (int)id;
                switch(val){
                    case 0 :
                        intent.setData(Uri.parse("geo:48.860522, 2.357919?q=60+Rue+Réaumur+75003+Paris"));
                        break;
                    case 1 :
                        intent.setData(Uri.parse("https://en.parisinfo.com/"));
                        break;
                    case 2 :
                        //intent.setAction("monaction.paris_zone");
                        //intent = new Intent(getApplicationContext(), paris_places.class);
                        intent = new Intent(getApplicationContext(), ListingDesLieuxActivity.class);
                        break;
                    case 3 :
                        intent.setData(Uri.parse("https://fr.wikipedia.org/wiki/Paris"));
                        break;
                }

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }
}
