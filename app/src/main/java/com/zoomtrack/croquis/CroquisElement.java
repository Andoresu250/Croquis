package com.zoomtrack.croquis;

import android.util.Log;

/**
 * Created by Andoresu on 13/05/2017.
 */

public class CroquisElement {

    private static String TAG = "CroquisElement";

    int icon200;
    String title;
    int height = 32*2;
    int width = 32*2;
    float scale = 1;

    public CroquisElement(int icon200, String title) {
        this.icon200 = icon200;
        this.title = title;
    }

    public CroquisElement(int icon200, String title, float scale) {
        this.icon200 = icon200;
        this.title = title;
        this.scale = scale;
    }

    public CroquisElement(int icon200, String title, int height, int width) {
        this.icon200 = icon200;
        this.title = title;
        this.height = height;
        this.width = width;
    }

    public void reSize(){
        float x = 32.0f / (float) this.height;
        this.height = 32;
        this.width *= x;
        this.height *= this.scale;
        this.width *= this.scale;
    }


    public int getIcon200() {
        return icon200;
    }


    public String getTitle() {
        return title;
    }

}
