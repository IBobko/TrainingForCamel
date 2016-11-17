package de.skubware.opentraining.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashMap;

import de.skubware.opentraining.R;

public class EditWeightActivity extends ActionBarActivity {
    private HashMap<String,Object> data;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weight);
        editText = (EditText)findViewById(R.id.editText2);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            data = (HashMap<String,Object>)extras.getSerializable("data");
            editText.setText(data.get("weight").toString());
        }
        if (data == null) {
            data = new HashMap<>();
            data.put("id",0);
            data.put("weight",0);
            data.put("d",0);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabata_choosetime_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_done_item:
                Intent intent = new Intent();
                intent.putExtra("value", editText.getText());
                intent.putExtra("id", (Integer)data.get("id"));
                setResult(RESULT_OK, intent);
                data = null;
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
