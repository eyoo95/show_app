package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class User implements Serializable {

    private String email; // 사용자 이메일
    private String password; // 사용자 비밀번호
    private String nickname; // 사용자의 닉네임
    private int gender; // 사용자의 성별
    private int age; // 사용자의 나이

    public User() {

    }

    public User(String email, String password, String nickname, int gender, int age) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public User(String password, String nickname, int gender, int age) {
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter Setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
