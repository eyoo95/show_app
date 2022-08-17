package com.luvris2.publicperfomancedisplayapp.model;

import java.io.Serializable;

public class User implements Serializable {

    private String email; // 사용자 이메일
    private String password; // 사용자 비밀번호
    private String name; // 사용자의 이름
    private String nickname; // 사용자의 닉네임
    private int gender; // 사용자의 성별
    private int age; // 사용자의 나이

    public User(String email, String password, String name, String nickname, int gender, int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // Getter Setter
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
