package com.zoomtrack.croquis;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class CroquisActivity extends AppCompatActivity implements Communicator{

    private static String TAG = "CroquisActivity";

    private CroquisFragment croquisFragment;

    private LinearLayout shadowLayout;

    private FloatingActionButton addCarBtn;
    private FloatingActionButton addSignalBtn;
    private FloatingActionButton addTraceBtn;

    private FloatingActionsMenu multipleActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.croquis_main);

        initGUI(savedInstanceState);

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

        shadowLayout = (LinearLayout) findViewById(R.id.shadow);

        addCarBtn.setOnClickListener(addCarListener());
        addSignalBtn.setOnClickListener(addCSignalListener());
        addTraceBtn.setOnClickListener(addTraceListener());

    }

    private View.OnClickListener addCarListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putIntArray(Constants.RESOURCES_200, Constants.car_resources200);
                bundle.putStringArray(Constants.RESOURCES_TITLE, Constants.car_titles);
                bundle.putFloatArray(Constants.RESOURCES_SCALE, Constants.car_scales);
                openSelectMarkerDialog(bundle);
                multipleActions.collapse();
            }
        };
    }

    private View.OnClickListener addCSignalListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + "click");
            }
        };
    }

    private View.OnClickListener addTraceListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + "click");
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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CroquisActivity.this);
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

        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics())
        );
        bottomSheetDialog.show();
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


}
