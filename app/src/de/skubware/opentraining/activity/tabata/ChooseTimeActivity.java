package de.skubware.opentraining.activity.tabata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.Serializable;

import de.skubware.opentraining.R;

public class ChooseTimeActivity extends ActionBarActivity {
    private TabataItem item;
    private TabataNumberPicker numberPicker;
    private TabataNumberPicker secPicker;
    private int position;

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
                saveValues();
                Intent intent = new Intent();
                intent.putExtra("TabataItem", this.item);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveValues(){
        if (item.isTime()) {
            item.setValue(numberPicker.getValue()*60 + secPicker.getValue() );
        }else {
            item.setValue(numberPicker.getValue());
        }
    }

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        numberPicker = (TabataNumberPicker) findViewById(R.id.numberPicker);
        secPicker = (TabataNumberPicker) findViewById(R.id.secondPicker);
        LinearLayout secLayout = (LinearLayout) findViewById(R.id.secondLayout);
        Intent i = getIntent();
        item = (TabataItem)i.getSerializableExtra("TabataItem");
        position = i.getIntExtra("position", 0);
        numberPicker.setDividerDrawable(getResources().getDrawable(R.color.material_blue_grey_800));
        numberPicker.setShowDividers(NumberPicker.SHOW_DIVIDER_NONE);

        if (!item.isTime()){
            secLayout.setVisibility(View.GONE);
            numberPicker.setMaxValue(item.getMaxValue());
            numberPicker.setMinValue(item.getMinValue());
            numberPicker.setValue(item.getValue());
            TextView tv = (TextView)findViewById(R.id.numberTextView);
            secPicker.setValue(0);
            tv.setText("Amount");
        }else if (item.getMaxValue() >= 60){
            secLayout.setVisibility(View.VISIBLE);
            secPicker.setMinValue(item.getMinValue());
            secPicker.setMaxValue(59);
            secPicker.setValue(item.getValue()%60);
            numberPicker.setValue(item.getValue()%60);
            numberPicker.setMaxValue(item.getMaxValue()/60);
        } else {
            secPicker.setMinValue(item.getMinValue());
            secPicker.setMaxValue(item.getMaxValue());
            secPicker.setValue(item.getValue());
        }

        numberPicker.setMinValue(item.getMinValue());

    }


}
