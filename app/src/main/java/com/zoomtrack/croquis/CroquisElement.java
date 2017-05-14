package com.zoomtrack.croquis;

/**
 * Created by Andoresu on 13/05/2017.
 */

public class CroquisElement {
    int icon200;
    int icon32;
    String title;

    public CroquisElement(int icon200, int icon32, String title) {
        this.icon200 = icon200;
        this.icon32 = icon32;
        this.title = title;
    }

    public int getIcon200() {
        return icon200;
    }

    public int getIcon32() {
        return icon32;
    }

    public String getTitle() {
        return title;
    }
}
