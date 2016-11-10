package de.skubware.opentraining.activity.manage_weight;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import de.skubware.opentraining.R;

public class ManageWeightActivity extends ActionBarActivity {

    String[] weight = {"05.11.16 - 92.5 kg", "04.11.16 - 90.9 kg", "03.11.16 - 91.2 kg", "02.11.16 - 92.1 kg", "01.11.16 - 90.5 kg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_weight);
        //
        ListView weightList = (ListView) findViewById(R.id.listview_weight);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weight);
        weightList.setAdapter(adapter);
    }
}
