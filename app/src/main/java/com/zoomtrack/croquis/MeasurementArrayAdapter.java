package com.zoomtrack.croquis;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Andoresu on 15/05/2017.
 */

public class MeasurementArrayAdapter extends ArrayAdapter<MeasurementElement>{

    TextView idTv;
    TextView xTv;
    TextView yTv;
    TextView descriptionTv;
    ImageView imageView;



    public MeasurementArrayAdapter(Context context, List<MeasurementElement> objects){
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_measurement,
                    parent,
                    false);
        }

        idTv = (TextView) convertView.findViewById(R.id.measurement_id);
        xTv = (TextView) convertView.findViewById(R.id.measurement_x);
        yTv = (TextView) convertView.findViewById(R.id.measurement_y);
        descriptionTv = (TextView) convertView.findViewById(R.id.measurement_description);
        imageView = (ImageView) convertView.findViewById(R.id.measurement_image);

        MeasurementElement measurementElement = getItem(position);

        idTv.setText((position + 1) + "");
        xTv.setText(String.format("%.2f", measurementElement.x));
        yTv.setText(String.format("%.2f", measurementElement.y));
        descriptionTv.setText(measurementElement.description);
        imageView.setImageDrawable(getContext().getResources().getDrawable(measurementElement.imageResource));

        return convertView;
    }

    @Override
    public void add(@Nullable MeasurementElement object) {
        super.add(object);
    }

    @Nullable
    @Override
    public MeasurementElement getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
