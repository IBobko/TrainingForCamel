package de.skubware.opentraining.activity.tabata;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.skubware.opentraining.R;

public class TabataActivity extends ActionBarActivity {

    private Tabata tabata;
    private ListView lvMain;
    private TabataAdapter adapter;


    public static final String LOG_TAG = "myLOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);
        tabata = new Tabata();
        createTabataList();
    }

    private void createTabataList() {

        lvMain = (ListView) findViewById(R.id.tabata_listview);

        adapter = new TabataAdapter(this, tabata.getTabataItemList());
        lvMain.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                startActivity(new Intent(TabataActivity.this.getApplicationContext(), ChooseTimeActivity.class));
            }
        };
        lvMain.setOnItemClickListener(itemClickListener);
    }




}
