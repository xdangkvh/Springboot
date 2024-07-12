package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RoleEntity;
// import com.example.demo.entity.UserEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findOneById(Long id);

    Optional<RoleEntity> findById(Long id);
}
