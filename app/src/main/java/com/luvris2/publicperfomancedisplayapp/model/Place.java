package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class Place implements Serializable {

    private com.luvris2.publicperfomancedisplayapp.model.Geometry geometry;
    private String name;
    private String vicinity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public com.luvris2.publicperfomancedisplayapp.model.Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(com.luvris2.publicperfomancedisplayapp.model.Geometry geometry) {
        this.geometry = geometry;
    }
}
