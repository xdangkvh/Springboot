package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoleDTO;

public interface IRoleService {
    RoleDTO save(RoleDTO roleDTO);

    // void delete(Long id);
    // void delete(long[] ids);
    boolean delete(List<Long> ids);
}
