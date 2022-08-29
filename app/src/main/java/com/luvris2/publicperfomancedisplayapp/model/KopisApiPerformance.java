package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class KopisApiPerformance implements Serializable {
    private String mt20id, prfnm, prfStartDate, prfEndDate, fcltynm, poster, genrenm, prfstate;
    private String prfpdfrom, prfpdto;
    private ArrayList<KopisApiPerformance> resultList;
    private KopisApiPerformance resultListObj;


    public KopisApiPerformance() {
    }
    public KopisApiPerformance(ArrayList<KopisApiPerformance> resultList) {
        this.resultList = resultList;
    }
    public KopisApiPerformance(KopisApiPerformance resultList) {
        this.resultListObj = resultList;
    }


    public ArrayList<KopisApiPerformance> getResultList() {
        return resultList;
    }
    public void setResultList(ArrayList<KopisApiPerformance> resultList) {
        this.resultList = resultList;
    }

    public KopisApiPerformance getResultListObj() {
        return resultListObj;
    }
    public void setResultList(KopisApiPerformance resultList) {
        this.resultListObj = resultList;
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

    public String getPrfId() {
        return mt20id;
    }
    public void setPrfId(String mt20id) {
        this.mt20id = mt20id;
    }

    public String getPrfName() {
        return prfnm;
    }
    public void setPrfName(String prfnm) {
        this.prfnm = prfnm;
    }

    public String getPrfStartDate() {
        return prfStartDate;
    }
    public void setPrfStartDate(String prfStartDate) {
        this.prfStartDate = prfStartDate;
    }

    public String getPrfEndDate() {
        return prfEndDate;
    }
    public void setPrfEndDate(String prfEndDate) {
        this.prfEndDate = prfEndDate;
    }

    public String getPrfPlace() {
        return fcltynm;
    }
    public void setPrfPlace(String fcltynm) {
        this.fcltynm = fcltynm;
    }

    public String getPosterUrl() {
        return poster;
    }
    public void setPosterUrl(String poster) {
        this.poster = poster;
    }

    public String getPrfGenre() {
        return genrenm;
    }
    public void setPrfGenre(String genrenm) {
        this.genrenm = genrenm;
    }

    public String getPrfState() {
        return prfstate;
    }
    public void setPrfState(String prfstate) {
        this.prfstate = prfstate;
    }
}
