package de.skubware.opentraining.activity.manage_weight;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.EditWeightActivity;
import de.skubware.opentraining.database.DatabaseForCamel;
import de.skubware.opentraining.database.WeightEntity;

public class ManageWeightActivity extends ListActivity {
    private final DatabaseForCamel databaseForCamel = new DatabaseForCamel(this);
    private final List<String> items = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            Object v = intent.getExtras().get("value");
            if (v == null) return;
            String value = v.toString();
            Integer id = (Integer) intent.getExtras().getSerializable("id");
            if (id == null) return;
            SQLiteDatabase database = databaseForCamel.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("w", value);
            contentValues.put("d", new Date().getTime());
            if (id == 0) {
                database.insert(WeightEntity.TABLE_NAME, null, contentValues);
            } else {
                database.update(WeightEntity.TABLE_NAME, contentValues, "_id = " + id, null);
            }
        }
        update();
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_weight_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
    final List<Map<String, ?>> weightArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_weight);
        update();
            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Integer _id = (Integer)weightArray.get(position).get("id");
                    databaseForCamel.getWritableDatabase().delete(WeightEntity.TABLE_NAME,"_id = " + _id,null);
                    update();
                    return false;
                }
            });
    }

    public void update() {
        final DatabaseForCamel databaseForCamel = new DatabaseForCamel(getApplicationContext());
        Cursor cursor = databaseForCamel.getReadableDatabase().rawQuery("SELECT * FROM weight", null);
        weightArray.clear();
        items.clear();
        while (!cursor.isLast() && cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            Double weight = cursor.getDouble(cursor.getColumnIndex("W"));
            int timestamp = cursor.getInt(cursor.getColumnIndex("D"));
            Date date = new Date();
            date.setTime(timestamp);
            Map<String, Object> h = new HashMap<>();
            h.put("id", id);
            h.put("weight", weight);
            h.put("date", date);
            weightArray.add(h);
            items.add(DateFormat.getDateTimeInstance().format(date) + " " + weight.toString());
        }
        cursor.close();
        setListAdapter(new ArrayAdapter<>(
                this, R.layout.row_weight,
                R.id.weight_txt, items.toArray()));
    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), EditWeightActivity.class);
        intent.putExtra("data",(Serializable) weightArray.get(position));
        startActivityForResult(intent, 1);
    }

    public void OnAddClick(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), EditWeightActivity.class);
        startActivityForResult(intent, 1);
    }
}
