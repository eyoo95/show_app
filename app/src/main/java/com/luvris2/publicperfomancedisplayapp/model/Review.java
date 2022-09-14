package com.luvris2.publicperfomancedisplayapp.model;

import java.util.List;

public class Review {
    // 공연 ID, 작성자, 리뷰 내용
    private String mt20id, userName, title, content, imgUrl;

    int UserId; // 작성자 식별 ID
    String prfName; // 통신을 줄이기 위한 공연 제목 저장 변수
    int verified; // 실제 관람 인증 여부
    private double rating; // 평점
    private String result; // API 결과 저장 변수
    private List<Review> resultList; // API 결과 저장 배열


    public Review() { }
    public Review(List<Review> resultList) {
        this.resultList = resultList;
    }
    public Review(String result) {
        this.result = result;
    }
    public Review(String title, String content, String imgUrl, String prfName, int verified, double rating) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.prfName = prfName;
        this.verified = verified;
        this.rating = rating;
    }

    public String getPrfName() {
        return prfName;
    }

    public void setPrfName(String prfName) {
        this.prfName = prfName;
    }

    public String getMt20id() {
        return mt20id;
    }

    public void setMt20id(String mt20id) {
        this.mt20id = mt20id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Review> getResultList() {
        return resultList;
    }

    public void setResultList(List<Review> resultList) {
        this.resultList = resultList;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }
}
