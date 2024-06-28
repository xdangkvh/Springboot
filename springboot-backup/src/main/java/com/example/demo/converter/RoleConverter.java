package com.example.demo.converter;

import org.springframework.stereotype.Component;

import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.RoleEntity;

@Component
public class RoleConverter {
    public RoleEntity toEntity(RoleDTO dto) {
        RoleEntity entity = new RoleEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }

    public RoleDTO toDTO(RoleEntity entity) {
        RoleDTO dto = new RoleDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }

    public RoleEntity toEntity(RoleDTO dto, RoleEntity entity) {
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
