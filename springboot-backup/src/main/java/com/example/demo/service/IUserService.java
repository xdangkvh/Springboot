package com.example.demo.service;

import java.util.List;
// import java.util.Optional;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;

public interface IUserService {
    UserDTO save(UserDTO userDTO);

    void delete(Long id);

    // void delete(long[] ids);
    boolean delete(List<Long> ids);

    UserDTO getUserById(Long id);

    List<UserDTO> getUsersByIds(List<Long> ids);

    List<UserDTO> getAllUsers();
    // boolean authenticate(Authentication)

    void addRoleToUser(String userName, String roleName);

    UserEntity loadUserById(Long id);
}
