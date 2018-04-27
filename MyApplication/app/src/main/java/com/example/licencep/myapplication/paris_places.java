package com.example.licencep.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class paris_places extends AppCompatActivity implements TextWatcher{

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paris_places);

        setTitle("PARIS SPECIAL PLACES");

        ArrayList<String> items = new ArrayList<String>();

        ListView listView = (ListView)findViewById(R.id.paris_list_zone);
        EditText search = (EditText)findViewById(R.id.paris_research);

        items.add("Arc de Triomphe");
        items.add("Tour Eiffel");
        items.add("Notre Dame de Paris");
        items.add("Musee du Louvre");
        items.add("Jardin des tuileries");
        items.add("Butte Monmartre");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        search.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
