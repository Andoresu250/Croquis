package com.zoomtrack.croquis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andoresu on 15/05/2017.
 */

public class DFMeasurementTable extends DialogFragment{

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.dialog_fragment_measurement_table, null);

        ListView listView = (ListView) layout.findViewById(R.id.measurement_list);

        List<MeasurementElement> measurementElements = (List<MeasurementElement>) getArguments().getSerializable(Constants.MEASUREMENT_ELEMENTS);

        MeasurementArrayAdapter measurementArrayAdapter = new MeasurementArrayAdapter(getActivity().getApplicationContext(), measurementElements);

        listView.setAdapter(measurementArrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout)
                .setPositiveButton("Cerrar", positiveListener())
                .setTitle("Tabla de medidas");

        return builder.create();
    }

    private DialogInterface.OnClickListener positiveListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
    }


}
