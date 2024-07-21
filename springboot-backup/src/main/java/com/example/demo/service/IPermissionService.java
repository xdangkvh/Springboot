package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PermissionDTO;

public interface IPermissionService {
    PermissionDTO save(PermissionDTO permissionDTO);

    // void delete(Long id);
    // void delete(long[] ids);
    boolean delete(List<Long> ids);

    PermissionDTO getPermissionById(Long id);

    List<PermissionDTO> getPermissionsByIds(List<Long> ids);

    List<PermissionDTO> getAllPermissions();
}
