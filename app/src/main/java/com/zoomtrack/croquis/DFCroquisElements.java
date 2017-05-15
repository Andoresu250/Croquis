package com.zoomtrack.croquis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andoresu on 13/05/2017.
 */

public class DFCroquisElements extends android.support.v4.app.DialogFragment {

    Communicator communicator;
    CroquisElement selectElement = null;
    ImageView selectImageView;
    TextView selectTitle;

    int[] resources200;
    String[] titles;
    float[] scales;

    private static String TAG = "DFCroquisElements";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        resources200 = getArguments().getIntArray(Constants.RESOURCES_200);
        titles = getArguments().getStringArray(Constants.RESOURCES_TITLE);
        scales = getArguments().getFloatArray(Constants.RESOURCES_SCALE);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.dialog_fragment_croquis_elements, null);
        selectImageView = (ImageView) layout.findViewById(R.id.select_image_view);
        selectTitle = (TextView) layout.findViewById(R.id.select_title);
        setImageViews(layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout)
            .setPositiveButton("Aceptar", positiveListener())
            .setTitle("Seleccione un vehiculo");


        return builder.create();
    }

    private DialogInterface.OnClickListener positiveListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                communicator.onSelectCar(selectElement);
                dialog.dismiss();
            }
        };
    }

    private ImageView generateImageView(CroquisElement element){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        imageView.setPadding(10,10,10,10);
        imageView.setImageDrawable(getResources().getDrawable(element.getIcon200()));
        imageView.setOnClickListener(selectImageListener(element));
        return imageView;
    }

    private void setImageViews(View layout){

        LinearLayout col1 = (LinearLayout) layout.findViewById(R.id.col1);
        LinearLayout col2 = (LinearLayout) layout.findViewById(R.id.col2);
        LinearLayout col3 = (LinearLayout) layout.findViewById(R.id.col3);
        LinearLayout col4 = (LinearLayout) layout.findViewById(R.id.col4);

        ArrayList<CroquisElement> croquisElements = new ArrayList<>();
        for (int i = 0; i < resources200.length; i++) {
            CroquisElement element = new CroquisElement(resources200[i], titles[i], scales[i]);
            croquisElements.add(element);
            ImageView imageView = generateImageView(element);
            switch (i % 4){
                case 0:
                    col1.addView(imageView);
                    break;
                case 1:
                    col2.addView(imageView);
                    break;
                case 2:
                    col3.addView(imageView);
                    break;
                case 3:
                    col4.addView(imageView);
                    break;
            }
        }

    }

    private View.OnClickListener selectImageListener(final CroquisElement element){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectElement = element;
                selectImageView.setImageDrawable(getResources().getDrawable(selectElement.getIcon200()));
                selectTitle.setText(selectElement.getTitle());
            }
        };
    }



}
