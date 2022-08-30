package com.luvris2.publicperfomancedisplayapp.model;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;

public class KopisApiPerformance implements Serializable {
    //    mt20id	    공연ID
    //    prfnm	        공연명
    //    prfpdfrom	    공연시작일
    //    prfpdto	    공연종료일
    //    fcltynm	    공연시설명(공연장명)
    //    prfcast	    공연출연진
    //    prfcrew	    공연제작진
    //    prfruntime    공연 런타임
    //    prfage	    공연 관람 연령
    //    entrpsnm	    제작사
    //    pcseguidance  티켓가격
    //    poster	    포스터이미지경로
    //    genrenm	장르
    //    prfstate	공연상태
    //    dtguidance	공연시간
    // latitude, longitude 내 위도 경도
    // resultList API 통신시 배열로 오는 응답 결과를 저장하는 변수
    // result API 통신시 오브젝트로 오는 응답 결과를 저장하는 변수
    private String mt20id, prfnm, fcltynm, poster, genrenm, prfstate;
    private String prfcast, prfcrew, prfruntime, prfage, entrpsnm, pcseguidance, dtguidance;
    private String prfpdfrom, prfpdto;
    private String latitude, longitude;
    private ArrayList<KopisApiPerformance> resultList;
    private KopisApiPerformance result;


    public KopisApiPerformance() {
    }
    public KopisApiPerformance(ArrayList<KopisApiPerformance> resultList) {
        this.resultList = resultList;
    }
    public KopisApiPerformance(KopisApiPerformance result) {
        this.result = result;
    }


    public ArrayList<KopisApiPerformance> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<KopisApiPerformance> resultList) {
        this.resultList = resultList;
    }

    public KopisApiPerformance getResult() {
        return result;
    }

    public void setResult(KopisApiPerformance result) {
        this.result = result;
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

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrfcast() {
        return prfcast;
    }

    public void setPrfcast(String prfcast) {
        this.prfcast = prfcast;
    }

    public String getPrfcrew() {
        return prfcrew;
    }

    public void setPrfcrew(String prfcrew) {
        this.prfcrew = prfcrew;
    }

    public String getPrfruntime() {
        return prfruntime;
    }

    public void setPrfruntime(String prfruntime) {
        this.prfruntime = prfruntime;
    }

    public String getPrfage() {
        return prfage;
    }

    public void setPrfage(String prfage) {
        this.prfage = prfage;
    }

    public String getEntrpsnm() {
        return entrpsnm;
    }

    public void setEntrpsnm(String entrpsnm) {
        this.entrpsnm = entrpsnm;
    }

    public String getPcseguidance() {
        return pcseguidance;
    }

    public void setPcseguidance(String pcseguidance) {
        this.pcseguidance = pcseguidance;
    }

    public String getDtguidance() {
        return dtguidance;
    }

    public void setDtguidance(String dtguidance) {
        this.dtguidance = dtguidance;
    }
}
