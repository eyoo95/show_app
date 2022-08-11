package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class Users implements Serializable {
    private String email; // 사용자 이메일
    private String password; // 사용자 비밀번호
    private String name; // 사용자의 이름
    private Boolean gender; // 사용자의 성별
    private int age; // 사용자의 나이

    public Users(String email, String password) {
    }
}
