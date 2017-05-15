package com.zoomtrack.croquis;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CroquisActivity extends AppCompatActivity implements Communicator{

    private static String TAG = "CroquisActivity";

    private CroquisFragment croquisFragment;

    private LinearLayout shadowLayout;

    private FloatingActionButton addCarBtn;
    private FloatingActionButton addSignalBtn;
    private FloatingActionButton addTraceBtn;
    private FloatingActionButton addObjectBtn;
    private FloatingActionButton addVictimBtn;
    private android.support.design.widget.FloatingActionButton acceptBtn;

    private FloatingActionsMenu multipleActions;

    private List<MeasurementElement> measurementElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.croquis_main);

        measurementElements = new ArrayList<>();

        initGUI(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.croquis_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initGUI(Bundle savedInstanceState){

        if (savedInstanceState == null) {
            croquisFragment = new CroquisFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.map, croquisFragment, "croquisFragment")
                    .commit();
        }

        multipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        addCarBtn = (FloatingActionButton) findViewById(R.id.add_car_btn);
        addSignalBtn = (FloatingActionButton) findViewById(R.id.add_signal_btn);
        addTraceBtn = (FloatingActionButton) findViewById(R.id.add_trace_btn);
        addVictimBtn = (FloatingActionButton) findViewById(R.id.add_victim_btn);
        addObjectBtn = (FloatingActionButton) findViewById(R.id.add_object_btn);
        acceptBtn = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_accept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                croquisFragment.finishWithReferenceMarker();
            }
        });

        shadowLayout = (LinearLayout) findViewById(R.id.shadow);

        addCarBtn.setOnClickListener(addElementListener(Constants.car_resources200, Constants.car_titles, Constants.car_scales));
        addSignalBtn.setOnClickListener(addElementListener(Constants.signal_resources, Constants.siganl_titles, Constants.signal_scales));
        addTraceBtn.setOnClickListener(addElementListener(Constants.trace_resources, Constants.trace_titles, Constants.trace_scales));
        addVictimBtn.setOnClickListener(addElementListener(Constants.victim_resources, Constants.victim_titles, Constants.victim_scales));
        addObjectBtn.setOnClickListener(addElementListener(Constants.object_resources, Constants.object_titles, Constants.object_scales));

        if (croquisFragment.referenceMarker == null){
            LinearLayout first_shadow = (LinearLayout) findViewById(R.id.first_shadow);
            first_shadow.setVisibility(View.VISIBLE);
            acceptBtn.setVisibility(View.VISIBLE);
            multipleActions.setVisibility(View.INVISIBLE);

        }else{
            normalGui();
        }

    }

    public void normalGui(){
        LinearLayout first_shadow = (LinearLayout) findViewById(R.id.first_shadow);
        first_shadow.setVisibility(View.INVISIBLE);
        acceptBtn.setVisibility(View.GONE);
        multipleActions.setVisibility(View.VISIBLE);
    }


    public View.OnClickListener addElementListener(final int[] resources, final String[] titles, final float[] scales){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putIntArray(Constants.RESOURCES_200, resources);
                bundle.putStringArray(Constants.RESOURCES_TITLE, titles);
                bundle.putFloatArray(Constants.RESOURCES_SCALE, scales);
                openSelectMarkerDialog(bundle);
                multipleActions.collapse();
            }
        };
    }

    private void openSelectMarkerDialog(Bundle bundle){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DFCroquisElements dfCroquisElements = new DFCroquisElements();
        dfCroquisElements.setArguments(bundle);
        dfCroquisElements.show(fragmentManager, "dfCroquisElements");
    }

    private void openBottomSheetRotate(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CroquisActivity.this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_rotate, null);

        SeekBar gradesBar = (SeekBar) view.findViewById(R.id.marker_rotation);
        final TextView gradesTV = (TextView) view.findViewById(R.id.grades_text_view);

        gradesBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gradesTV.setText(progress + "°");
                croquisFragment.rotateTouchedMarker(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button removeIconBtn = (Button) view.findViewById(R.id.remove_icon_btn);
        removeIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CroquisActivity.this)
                        .setTitle("Remover icono")
                        .setMessage("Esta seguro de que desea removerlo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                croquisFragment.removeCroquisElement();
                                dialog.dismiss();
                                bottomSheetDialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }
        });

        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics())
        );
        bottomSheetDialog.show();
    }

    private void openMeasurementTable(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DFMeasurementTable dfMeasurementTable = new DFMeasurementTable();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MEASUREMENT_ELEMENTS, (Serializable) measurementElements);
        dfMeasurementTable.setArguments(bundle);
        dfMeasurementTable.show(fragmentManager, "dfMeasurementTable");
    }

    @Override
    public void onSelectCar(CroquisElement element) {
        if(element != null){
            Log.i(TAG, "onSelectCar: " + element.getTitle());
            croquisFragment.setSelectedElement(element);
            shadowLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideShadow() {
        shadowLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRotate(){
        openBottomSheetRotate();
    }

    @Override
    public void addMeasurement(MeasurementElement measurementElement) {
        measurementElements.add(measurementElement);
    }

    @Override
    public void rmvMeasurement(MeasurementElement measurementElement) {
        measurementElements.remove(measurementElement);
    }

    @Override
    public void editMeasurement(MeasurementElement oldMeasurementElement, MeasurementElement newMeasurementElement) {

        for (int i = 0; i < measurementElements.size(); i++) {
            if(measurementElements.get(i).equals(oldMeasurementElement)){
                measurementElements.remove(oldMeasurementElement);
                measurementElements.add(i, newMeasurementElement);
            }
        }

    }

    @Override
    public void finishReferenceMarker() {
        normalGui();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_measurement_table:
                Log.i(TAG, "onOptionsItemSelected: openTable");
                openMeasurementTable();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
