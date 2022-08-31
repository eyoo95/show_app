package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GoogleMapPlace implements Serializable {
    public GoogleMapPlace(ArrayList<GoogleMapPlace> results) {
        this.results = results;
    }

    private ArrayList<GoogleMapPlace> results;
    private String formatted_address;


    public String getFormatted_address() {
        return formatted_address;
    }
    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
    public ArrayList<GoogleMapPlace> getResults() {
        return results;
    }
    public void setResults(ArrayList<GoogleMapPlace> results) {
        this.results = results;
    }
}
