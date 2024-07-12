package com.example.demo.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.RoleEntity;
// import com.example.demo.entity.NewEntity;
import com.example.demo.entity.UserEntity;

@Component
public class UserConverter {

    public UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setFullName(dto.getFullName());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        // entity.setRoles(dto.getRole_id());
        return entity;
    }

    public UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setUserName(entity.getUserName());
        dto.setFullName(entity.getFullName());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());

        // Lấy danh sách roleIds từ các RoleEntity
        List<Long> roleIds = entity.getRoles().stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toList());
        dto.setRoleIds(roleIds);

        return dto;
    }

    public UserEntity toEntity(UserDTO dto, UserEntity entity) {
        entity.setUserName(dto.getUserName());
        entity.setFullName(dto.getFullName());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
