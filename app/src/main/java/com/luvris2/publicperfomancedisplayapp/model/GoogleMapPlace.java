package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class GoogleMapPlace implements Serializable {

    public GoogleMapPlace(Object plus_code) {
        this.plus_code = plus_code;
    }

    private Object plus_code;
    private String compound_code;


    public String getCompound_code() {
        return compound_code;
    }
    public void setCompound_code(String compound_code) {
        this.compound_code = compound_code;
    }
    public Object getPlus_code() {
        return plus_code;
    }
    public void setPlus_code(Object plus_code) {
        this.plus_code = plus_code;
    }
}
