package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long>{
    
}
