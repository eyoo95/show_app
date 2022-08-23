package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;
import java.util.List;

public class GoogleMapPlace implements Serializable {

    public GoogleMapPlace(List<GoogleMapPlace> results) {
        this.results = results;
    }

    private String long_name;

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    private List<GoogleMapPlace> address_components;

    public List<GoogleMapPlace> getAddress_components() {
        return address_components;
    }

    private List<GoogleMapPlace> results;

    public List<GoogleMapPlace> getResults() {
        return results;
    }

    public void setResults(List<GoogleMapPlace> results) {
        this.results = results;
    }

    public void setAddress_components(List<GoogleMapPlace> address_components) {
        this.address_components = address_components;
    }
}
