package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicPerfomanceDisplay implements Serializable {

    private int seq; // 일련번호
    private String title; // 제목
    private String startDate, endDate; // 시작일, 마감일
    private String place; // 장소
    private String realmName; // 분류명
    private int area; // 장소
    private String thumbnail; // 썸네일
    private double gpsX, gpsY; // GPS의 X,Y좌표

}
