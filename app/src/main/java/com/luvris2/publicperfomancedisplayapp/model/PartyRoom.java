package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class PartyRoom implements Serializable {

    int id;
    int userId;
    String mt20id;
    String prfnm;
    String title;
    String createdAt;
    String updatedAt;

    public PartyRoom(int id, int userId, String mt20id, String prfnm, String title, String createdAt, String updatedAt) {
        this.prfnm = prfnm;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMt20id() {
        return mt20id;
    }

    public void setMt20id(String mt20id) {
        this.mt20id = mt20id;
    }

    public String getPrfnm() {
        return prfnm;
    }

    public void setPrfnm(String prfnm) {
        this.prfnm = prfnm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
