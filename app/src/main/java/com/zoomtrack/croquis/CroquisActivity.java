package com.zoomtrack.croquis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
                openSelectCarDialog();
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

    private void openSelectCarDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DFCars dfCars = new DFCars();
        dfCars.show(fragmentManager, "DFCars");
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


}
