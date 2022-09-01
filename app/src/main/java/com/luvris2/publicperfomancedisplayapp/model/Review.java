package com.luvris2.publicperfomancedisplayapp.model;

public class Review {

    // todo : 행사 이름, 작성자, 리뷰 내용, 리뷰 쓴 날짜, 행사 포스터 이미지
    private String mt20id; //
    private String id;
    private String content; // 리뷰내용


    public String getMt20id() {
        return mt20id;
    }

    public void setMt20id(String mt20id) {
        this.mt20id = mt20id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl; // 리뷰 사진

}
