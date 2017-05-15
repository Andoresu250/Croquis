package com.zoomtrack.croquis;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CroquisFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        LocationListener,
        GoogleMap.OnMarkerDragListener
        {

    private static String TAG = "CroquisFragment";
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LatLng latLng;
    GoogleMap mGoogleMap;
    private CroquisElement selectedElement = null;
    private Marker touchedMarker = null;
    Communicator communicator;
    public Marker referenceMarker = null;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

            @Override
            public void onAttach(Activity activity) {
                super.onAttach(activity);
                communicator = (Communicator) activity;
            }

            @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
        if (status == ConnectionResult.SUCCESS){
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            this.getMapAsync(this);
        }else{
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 10).show();
        }
    }



    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getActivity().getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            initCamera(mLastLocation);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMarkerDragListener(this);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);
        mGoogleMap.setOnMapClickListener(clickListener());
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity().getApplicationContext(), R.raw.style_json));
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(false);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    private GoogleMap.OnMapClickListener clickListener(){
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i(TAG, "onMapClick: " + latLng.toString());
                if (referenceMarker == null){
                    referenceMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Punto de referencia").draggable(true));
                }else{
                    setMarker(latLng);
                }
            }
        };
    }

    public void finishWithReferenceMarker(){
        referenceMarker.setDraggable(false);
        communicator.finishReferenceMarker();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }




    private void initCamera(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(getCameraPosition()), null);

        mGoogleMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mGoogleMap.setMyLocationEnabled(true);
    }



    public void setMarker(LatLng latLng) {
        if (selectedElement == null)
            return;
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(selectedElement.getTitle())
                .draggable(true)
                .icon(getIcon(selectedElement));
        Marker marker = mGoogleMap.addMarker(options);



        Log.i(TAG, "setMarker: X " + getX(referenceMarker, marker));
        Log.i(TAG, "setMarker: Y " + getY(referenceMarker, marker));
        MeasurementElement measurementElement = new MeasurementElement(getX(referenceMarker, marker), getY(referenceMarker, marker), selectedElement.getIcon200(), selectedElement.getTitle());
        communicator.addMeasurement(measurementElement);
        marker.setTag(measurementElement);
        selectedElement = null;
        communicator.hideShadow();
    }


    private BitmapDescriptor getIcon(CroquisElement element){
        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(element.getIcon200())).getBitmap();
        element.width = bitmap.getWidth();
        element.height = bitmap.getHeight();
        element.reSize();
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, element.width, element.height, false));
    }

    public void removeCroquisElement(){
        communicator.rmvMeasurement((MeasurementElement) touchedMarker.getTag());
        touchedMarker.remove();
        touchedMarker = null;
    }

    private double getX(Marker a, Marker b){
        double earthCircumference = 40075 * 1000;
        return ((b.getPosition().longitude - a.getPosition().longitude) * earthCircumference * Math.cos((a.getPosition().latitude + b.getPosition().latitude) * Math.PI/360)/360);
    }

    private double getY(Marker a, Marker b){
        double earthCircumference = 40075 * 1000;
        return (b.getPosition().latitude - a.getPosition().latitude) * earthCircumference / 360;
    }

    public static float distFrom(LatLng p1, LatLng p2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(p2.latitude - p1.latitude);
        double dLng = Math.toRadians(p2.longitude - p1.longitude );
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(p1.latitude)) * Math.cos(Math.toRadians(p2.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    public void setSelectedElement(CroquisElement element){
        this.selectedElement = element;
        Log.i(TAG, "onSelectCar: " + selectedElement.getTitle());
    }

    private boolean isInFirstStep(){
        return true;
    }

    public void rotateTouchedMarker(float grades){
        touchedMarker.setRotation(grades);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(getCameraPosition()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private CameraPosition getCameraPosition(){
        return new CameraPosition.Builder()
                .target(latLng).zoom(20.9f).build();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        touchedMarker = marker;
        communicator.showRotate();
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d("System out", "onMarkerDragStart..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d("System out", "onMarkerDragStart..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);
        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.i("System out", "onMarkerDrag...");
        if(marker.equals(referenceMarker))
           return;
        MeasurementElement oldMeasurementElement = (MeasurementElement) marker.getTag();
        MeasurementElement newMeasurementElement = new MeasurementElement(getX(referenceMarker, marker), getY(referenceMarker, marker), oldMeasurementElement.imageResource, oldMeasurementElement.description);
        marker.setTag(newMeasurementElement);
        communicator.editMeasurement(oldMeasurementElement, newMeasurementElement);
    }



}

