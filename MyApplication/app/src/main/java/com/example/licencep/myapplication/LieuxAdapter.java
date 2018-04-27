package com.example.licencep.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LieuxAdapter extends ArrayAdapter<LieuxAVisiter> {

    private ArrayList<LieuxAVisiter> list;

    public LieuxAdapter(Context ctx, ArrayList<LieuxAVisiter> arr) {
        super(ctx, R.layout.ligne_lieu, arr);
        this.list = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.ligne_lieu, null);
        TextView label = (TextView) row.findViewById(R.id.item1);
        label.setText(list.get(position).getNom());

        return row;
    }
}
