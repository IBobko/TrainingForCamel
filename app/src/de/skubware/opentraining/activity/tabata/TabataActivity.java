package de.skubware.opentraining.activity.tabata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.MainActivity;

public class TabataActivity extends ActionBarActivity {

    private Tabata tabata;
    private ListView lvMain;
    private TabataAdapter adapter;


    public static final String LOG_TAG = "myLOG";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabata_view_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_start:
                Intent intent= new Intent(TabataActivity.this.getApplicationContext(), TabataStartActivity.class);
                intent.putExtra("Tabata", tabata);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);
        tabata = new Tabata();
        //createTabataList();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        createTabataList();
    }


    private void createTabataList() {

        lvMain = (ListView) findViewById(R.id.tabata_listview);

        ChooseTimeActivity s = new ChooseTimeActivity();

        adapter = new TabataAdapter(TabataActivity.this, tabata);
        lvMain.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int result, Intent data){
        System.out.println("ehhf");
        int pos = data.getIntExtra("position",0);
        TabataItem ti = (TabataItem)data.getSerializableExtra("TabataItem");
        tabata.getTabataItemList().get(pos).setValue(ti.getValue());
    }



}
