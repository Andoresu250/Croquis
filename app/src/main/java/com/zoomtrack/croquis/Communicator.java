package com.zoomtrack.croquis;

/**
 * Created by Andoresu on 13/05/2017.
 */

public interface Communicator {

    void onSelectCar(CroquisElement element);

    void hideShadow();

    void showRotate();

    void addMeasurement(MeasurementElement measurementElement);

    void rmvMeasurement(MeasurementElement measurementElement);

    void editMeasurement(MeasurementElement oldMeasurementElement, MeasurementElement newMeasurementElement);

    void finishReferenceMarker();

}
