package de.skubware.opentraining.activity.tabata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import de.skubware.opentraining.database.DatabaseForCamel;
import de.skubware.opentraining.database.OptionEntity;
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
        databaseForCamel = new DatabaseForCamel(this);
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
    private DatabaseForCamel databaseForCamel;

    @Override
    protected void onActivityResult(int requestCode, int result, Intent data){
        if (data == null) return;
        int pos = data.getIntExtra("position",0);
        TabataItem ti = (TabataItem)data.getSerializableExtra("TabataItem");
        tabata.getTabataItemList().get(pos).setValue(ti.getValue());
        final String key = ti.getDiscription();
        final Integer value = ti.getValue();
        ContentValues contentValues = new ContentValues();
        contentValues.put("key",key);
        contentValues.put("value",value);
        final Cursor cursor = databaseForCamel.getReadableDatabase().query(OptionEntity.TABLE_NAME, new String[]{"key", "value"}, "key = ?", new String[]{key}, "", "", "");
        if (cursor.moveToNext()) {
            databaseForCamel.getWritableDatabase().update(OptionEntity.TABLE_NAME,contentValues,"key=?",new String[]{key});
        } else {
            databaseForCamel.getWritableDatabase().insert(OptionEntity.TABLE_NAME,null,contentValues);
        }
    }
}
