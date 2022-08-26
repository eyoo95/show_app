package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class kopisApiDetail implements Serializable {

    private String  prfnm, prfpdfrom, prfpdto, fcltynm, poster;

    public String getPrfnm() {
        return prfnm;
    }

    public void setPrfnm(String prfnm) {
        this.prfnm = prfnm;
    }

    public String getPrfpdfrom() {
        return prfpdfrom;
    }

    public void setPrfpdfrom(String prfpdfrom) {
        this.prfpdfrom = prfpdfrom;
    }

    public String getPrfpdto() {
        return prfpdto;
    }

    public void setPrfpdto(String prfpdto) {
        this.prfpdto = prfpdto;
    }

    public String getFcltynm() {
        return fcltynm;
    }

    public void setFcltynm(String fcltynm) {
        this.fcltynm = fcltynm;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public kopisApiDetail() {
    }

}
