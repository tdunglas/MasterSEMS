package com.example.licencep.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ListingDesLieuxActivity extends AppCompatActivity {

    private ListView  listing;
    private LieuxParser sp;
    private ProgressDialog progress;
    private LieuxAdapter lieuxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtre_lieux);

        listing = (ListView) findViewById(R.id.listviewFiltreLieux);

        class ChargementDesLieuxTache extends AsyncTask<Void, Void, Void> {


            protected void onPreExecute() {
                progress = ProgressDialog.show(ListingDesLieuxActivity.this,
                        getResources().getString(R.string.app_name),
                        getResources().getString(R.string.chargement_message),
                        true);
            }

            protected Void doInBackground(Void... params) {
                try {
                    sp = new LieuxParser();
                    lieuxAdapter = new LieuxAdapter(getBaseContext(), sp.getList());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            protected void onProgressUpdate(Void... aAfficher) { }

            protected void onPostExecute(Void result) {
                // arréter le progressDialog
                progress.dismiss();
                // mettre à jour la ListView des lieux
                listing.setAdapter(lieuxAdapter);
            }
        }

        new ChargementDesLieuxTache().execute();
    }



}
