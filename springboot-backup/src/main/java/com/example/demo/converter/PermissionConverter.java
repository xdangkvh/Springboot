package com.example.demo.converter;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PermissionDTO;
import com.example.demo.entity.PermissionEntity;

@Component
public class PermissionConverter {
    public PermissionDTO toDTO(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(entity.getId());
        permissionDTO.setName(entity.getName());

        return permissionDTO;
    }

    public PermissionEntity toEntity(PermissionDTO dto) {
        if (dto == null) {
            return null;
        }

        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(dto.getId());
        permissionEntity.setName(dto.getName());

        return permissionEntity;
    }

    public PermissionEntity toEntity(PermissionDTO dto, PermissionEntity entity) {
        entity.setName(dto.getName());
        return entity;
    }
}
