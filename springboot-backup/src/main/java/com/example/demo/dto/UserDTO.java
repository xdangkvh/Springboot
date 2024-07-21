package com.example.demo.dto;

// import java.util.ArrayList;

import java.util.Set;

// import com.example.demo.entity.RoleEntity;

public class UserDTO extends AbstractDTO<UserDTO> {
    private String userName;
    private String fullName;
    private String password;
    private Integer status;
    private Set<RoleDTO> roles;

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
