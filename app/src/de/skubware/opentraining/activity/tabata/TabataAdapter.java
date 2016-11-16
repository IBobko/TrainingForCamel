package de.skubware.opentraining.activity.tabata;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.tabata.TabataItem;

/**
 * Created by ildarworld on 10/11/2016.
 */

public class TabataAdapter extends ArrayAdapter {

    private final Context context;
    private Tabata tabata;

    public TabataAdapter(Context context, Tabata tabata) {
        super(context, R.layout.tabata_list_row, tabata.getTabataItemList());

        this.context = context;
        this.tabata = tabata;
    }

    public TabataItem getItem(int position){
        return tabata.getTabataItemList().get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.tabata_list_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.tvDesc);
        TextView valueView = (TextView) rowView.findViewById(R.id.tvSec);
        ImageButton detailButton = (ImageButton) rowView.findViewById(R.id.ibChoose);

        detailButton.setOnClickListener(new ImageButton.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                System.out.println("yeeees");
                                                Intent intent= new Intent(context, ChooseTimeActivity.class);
                                                intent.putExtra("TabataItem", tabata.getTabataItemList().get(position));
                                                intent.putExtra("position", position);

                                                ((ActionBarActivity)context).startActivityForResult(intent, 0);
                                            }


                                        }
        );

        // 4. Set the text for textView
        TabataItem ti = tabata.getTabataItemList().get(position);
        labelView.setText(ti.getDiscription());
        valueView.setText(ti.getStringValue());

        // 5. retrn rowView
        return rowView;
    }
}