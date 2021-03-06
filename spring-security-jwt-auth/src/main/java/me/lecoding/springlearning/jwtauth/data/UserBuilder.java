package me.lecoding.springlearning.jwtauth.data;

import me.lecoding.springlearning.jwtauth.auth.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBuilder {
    private String id;
    private String nickName;
    private String sex;
    private Date birthDay;
    private String password;
    private String email;
    private String mobile;
    private List<Role> roles = new ArrayList<>();

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

    public UserBuilder roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public User build() {
        return new User(id, nickName, sex, birthDay, password, email, mobile,roles);
    }
}