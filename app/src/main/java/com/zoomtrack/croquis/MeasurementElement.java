package com.zoomtrack.croquis;

import java.io.Serializable;

/**
 * Created by Andoresu on 15/05/2017.
 */

public class MeasurementElement implements Serializable{

    double x;
    double y;
    int imageResource;
    String description;


    public MeasurementElement(double x, double y, int imageResource, String description) {
        this.x = x;
        this.y = y;
        this.imageResource = imageResource;
        this.description = description;
    }
}
