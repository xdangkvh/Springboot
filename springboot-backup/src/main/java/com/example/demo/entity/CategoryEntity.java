package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Column
    private String name;
    @Column
    private String code;
    @OneToMany(mappedBy = "category")
    private List<NewEntity> news = new ArrayList<>();

    public List<NewEntity> getNews() {
        return news;
    }

    public void setNews(List<NewEntity> news) {
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}