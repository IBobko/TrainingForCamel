package de.skubware.opentraining.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.skubware.opentraining.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.row_weight, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row_weight, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.weight_txt);
        txtTitle.setText(web[position]);
        return rowView;
    }
}
