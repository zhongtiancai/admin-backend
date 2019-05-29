package com.zhongtiancai.admin.vm;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by zhongtiancai on 2019/5/28.
 */

public class AdminVM  {

    private Long id;
    private String username;
    private String nickName;
    private String password;
    private String icon;
    private String email;

    private Set<Long> roles;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
