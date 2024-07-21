package com.example.demo.repository;

import com.example.demo.entity.PermissionEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findOneById(Long id);

    Optional<PermissionEntity> findById(Long id);

    Optional<PermissionEntity> findByName(String roleName);
}
