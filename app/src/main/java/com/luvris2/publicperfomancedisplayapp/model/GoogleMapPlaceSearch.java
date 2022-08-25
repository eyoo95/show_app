package com.luvris2.publicperfomancedisplayapp.model;

import java.util.List;

public class GoogleMapPlaceSearch {

    public GoogleMapPlaceSearch() {
    }

    private List<GoogleMapPlaceSearch> results;

    private GoogleMapPlaceSearch geometry;

    private GoogleMapPlaceSearch location;

    private double lat, lng;

    public List<GoogleMapPlaceSearch> getResults() {
        return results;
    }

    public void setResults(List<GoogleMapPlaceSearch> results) {
        this.results = results;
    }

    public GoogleMapPlaceSearch getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleMapPlaceSearch geometry) {
        this.geometry = geometry;
    }

    public GoogleMapPlaceSearch getLocation() {
        return location;
    }

    public void setLocation(GoogleMapPlaceSearch location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
