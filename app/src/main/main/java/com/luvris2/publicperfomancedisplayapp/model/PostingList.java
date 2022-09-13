package com.luvris2.publicperfomancedisplayapp.model;

import java.util.List;

// 최지훈
public class PostingList {

    private String result;
    private List<Posting> resultList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Posting> getResultList() {
        return resultList;
    }

    public void setResultList(List<Posting> resultList) {
        this.resultList = resultList;
    }
}
