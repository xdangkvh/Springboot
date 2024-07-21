package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.converter.PermissionConverter;
import com.example.demo.dto.PermissionDTO;
import com.example.demo.entity.PermissionEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.Error;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.service.IPermissionService;

public class PermissionService implements IPermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionConverter permissionConverter;

    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        PermissionEntity permissionEntity = new PermissionEntity();
        if (permissionDTO.getId() != null) {
            PermissionEntity oldPermissionEntity = permissionRepository.findOneById(permissionDTO.getId());
            permissionEntity = permissionConverter.toEntity(permissionDTO, oldPermissionEntity);
        } else {
            permissionEntity = permissionConverter.toEntity(permissionDTO);
        }

        permissionEntity = permissionRepository.save(permissionEntity);
        return permissionConverter.toDTO(permissionEntity);
    }

    @Override
    public boolean delete(List<Long> ids) {
        List<PermissionEntity> permissionsToDelete = permissionRepository.findAllById(ids);

        if (permissionsToDelete.size() != ids.size()) {
            // Log the IDs that were not found
            ids.removeAll(permissionsToDelete.stream().map(PermissionEntity::getId).collect(Collectors.toList()));
            System.out.println("IDs not found: " + ids);
            return false; // Some IDs were not found
        }
        permissionRepository.deleteAll(permissionsToDelete);
        return true;
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {
        PermissionEntity permissionEntity = permissionRepository.findOneById(id);
        if (permissionEntity == null) {
            throw new AppException(Error.USER_NOT_EXISTED);
        }
        // permissionEntity.getRoles().size(); // Force loading of roles
        return permissionConverter.toDTO(permissionEntity);
    }

    // New method to get multiple users by IDs
    @Override
    public List<PermissionDTO> getPermissionsByIds(List<Long> ids) {
        List<PermissionEntity> permissionEntities = permissionRepository.findAllById(ids);
        if (permissionEntities.isEmpty()) {
            throw new AppException(Error.USER_NOT_EXISTED);
        }
        return permissionEntities.stream().map(permissionConverter::toDTO).collect(Collectors.toList());
    }

    // New method to get all users
    @Override
    public List<PermissionDTO> getAllPermissions() {
        List<PermissionEntity> permissionEntities = permissionRepository.findAll();
        return permissionEntities.stream().map(permissionConverter::toDTO).collect(Collectors.toList());
    }
}
