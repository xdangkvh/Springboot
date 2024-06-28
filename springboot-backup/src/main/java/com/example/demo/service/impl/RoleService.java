package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.RoleConverter;
import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.RoleEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IRoleService;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        RoleEntity roleEntity = new RoleEntity();
        if (roleDTO.getId() != null) {
            RoleEntity oldRoleEntity = roleRepository.findOneById(roleDTO.getId());
            roleEntity = roleConverter.toEntity(roleDTO, oldRoleEntity);
        } else {
            roleEntity = roleConverter.toEntity(roleDTO);
        }

        roleEntity = roleRepository.save(roleEntity);
        return roleConverter.toDTO(roleEntity);
    }

    @Override
    public boolean delete(List<Long> ids) {
        List<RoleEntity> rolesToDelete = roleRepository.findAllById(ids);

        if (rolesToDelete.size() != ids.size()) {
            // Log the IDs that were not found
            ids.removeAll(rolesToDelete.stream().map(RoleEntity::getId).collect(Collectors.toList()));
            System.out.println("IDs not found: " + ids);
            return false; // Some IDs were not found
        }
        roleRepository.deleteAll(rolesToDelete);
        return true;
    }
}
