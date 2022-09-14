package com.luvris2.publicperfomancedisplayapp.model;

import java.util.List;

public class PostRes {

    private String result;
    private List<PostRecomm> resultList;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<PostRecomm> getResultList() {
        return resultList;
    }

    public void setResultList(List<PostRecomm> resultList) {
        this.resultList = resultList;
    }
}
