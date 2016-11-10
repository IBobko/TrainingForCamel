package de.skubware.opentraining.activity.tabata;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.tabata.TabataItem;

/**
 * Created by ildarworld on 10/11/2016.
 */

public class TabataAdapter extends ArrayAdapter<TabataItem> {

    private final Context context;
    private final ArrayList<TabataItem> itemsArrayList;

    public TabataAdapter(Context context, ArrayList<TabataItem> itemsArrayLists) {
        super(context, R.layout.tabata_list_row, itemsArrayLists);

        this.context = context;
        this.itemsArrayList = itemsArrayLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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

                                            }
                                        }


        );


        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getDiscription());
        valueView.setText(itemsArrayList.get(position).getValue());

        // 5. retrn rowView
        return rowView;
    }
}