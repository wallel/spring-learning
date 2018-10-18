package me.lecoding.springlearning.session.data;

import java.util.Date;

public class UserBuilder {
    private String id;
    private String nickName;
    private String sex;
    private Date birthDay;
    private String password;
    private String email;
    private String mobile;

    public UserBuilder id(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public UserBuilder nex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserBuilder nirthDay(Date birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public User build() {
        return new User(id, nickName, sex, birthDay, password, email, mobile);
    }
}