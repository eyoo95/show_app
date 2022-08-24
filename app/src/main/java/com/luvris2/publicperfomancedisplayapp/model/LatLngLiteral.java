package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class LatLngLiteral implements Serializable {

    private double gpsX;
    private double gpsY;

    public double getLat() {
        return gpsX;
    }

    public void setLat(double lat) {
        this.gpsX = lat;
    }

    public double getLng() {
        return gpsY;
    }

    public void setLng(double lng) {
        this.gpsY = lng;
    }
}
