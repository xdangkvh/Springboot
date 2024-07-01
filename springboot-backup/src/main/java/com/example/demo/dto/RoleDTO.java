package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.UserEntity;

public class RoleDTO extends AbstractDTO<RoleDTO> {
    private String code;
    private String name;
    private List<UserEntity> user_id = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<UserEntity> user_id) {
        this.user_id = user_id;
    }

}
