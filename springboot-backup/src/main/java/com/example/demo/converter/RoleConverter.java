package com.example.demo.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PermissionDTO;
import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.PermissionEntity;
import com.example.demo.entity.RoleEntity;

@Component
public class RoleConverter {

    private PermissionConverter permissionConverter = new PermissionConverter();

    public RoleEntity toEntity(RoleDTO dto) {
        RoleEntity entity = new RoleEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setPermissions(toPermissionEntitySet(dto.getPermissions()));
        return entity;
    }

    public RoleDTO toDTO(RoleEntity entity) {
        RoleDTO dto = new RoleDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setPermissions(toPermissionDTOSet(entity.getPermissions()));
        return dto;
    }

    public RoleEntity toEntity(RoleDTO dto, RoleEntity entity) {
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setPermissions(toPermissionEntitySet(dto.getPermissions()));
        return entity;
    }

    private Set<PermissionDTO> toPermissionDTOSet(Set<PermissionEntity> permissionEntities) {
        return permissionEntities.stream()
                .map(permissionConverter::toDTO)
                .collect(Collectors.toSet());
    }

    private Set<PermissionEntity> toPermissionEntitySet(Set<PermissionDTO> permissionDTOs) {
        return permissionDTOs.stream()
                .map(permissionConverter::toEntity)
                .collect(Collectors.toSet());
    }
}
