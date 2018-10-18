package me.lecoding.springlearning.session.data;

import me.lecoding.springlearning.session.auth.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RedisHash("hash")
public class User implements Serializable {
    @Id
    private String id;

    private String nickName;
    private String sex;
    private Date   birthDay;
    private String password;
    private String email;
    private String mobile;

    private List<Role> roles = new ArrayList<>();

    public User(){
    }

    public User(String id,String nickName, String sex, Date birthDay, String password, String email, String mobile) {
        this.id = id;

        this.nickName = nickName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.password = password;
        this.email = email;
        this.mobile = mobile;

    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static UserBuilder builder(){return new UserBuilder();}
}
