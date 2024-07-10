package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.converter.UserConverter;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO save(UserDTO userDTO) {
        // Check if username already exists

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        // Check if username already exists
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        UserEntity userEntity = new UserEntity();
        if (userDTO.getId() != null) {
            UserEntity oldUserEntity = userRepository.findOneById(userDTO.getId());
            userEntity = userConverter.toEntity(userDTO, oldUserEntity);
        } else {
            userEntity = userConverter.toEntity(userDTO);
        }
        int strength = 10;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity = userRepository.save(userEntity);
        return userConverter.toDTO(userEntity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    // @Override
    // public void delete(long[] ids) {
    // for (long item : ids) {
    // // UserEntity entityToDelete = user UserEntity();
    // // entityToDelete.setId(item); // Assuming setId method exists in UserEntity
    // userRepository.deleteById(item);
    // }
    // }

    @Override
    public boolean delete(List<Long> ids) {
        List<UserEntity> usersToDelete = userRepository.findAllById(ids);

        if (usersToDelete.size() != ids.size()) {
            // Log the IDs that were not found
            ids.removeAll(usersToDelete.stream().map(UserEntity::getId).collect(Collectors.toList()));
            System.out.println("IDs not found: " + ids);
            return false; // Some IDs were not found
        }
        userRepository.deleteAll(usersToDelete);
        return true;
        // for (long item : ids) {
        // // UserEntity entityToDelete = user UserEntity();
        // // entityToDelete.setId(item); // Assuming setId method exists in UserEntity
        // userRepository.deleteById(item);
        // }
    }

    // @Override
    // public void delete(long[] ids) {
    // for(long item: ids) {
    // userRepository.delete(item);
    // }
    // }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findOneById(id);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
        return userConverter.toDTO(userEntity);
    }

    // New method to get multiple users by IDs
    @Override
    public List<UserDTO> getUsersByIds(List<Long> ids) {
        List<UserEntity> userEntities = userRepository.findAllById(ids);
        if (userEntities.isEmpty()) {
            throw new UsernameNotFoundException("No users found with the given IDs");
        }
        return userEntities.stream().map(userConverter::toDTO).collect(Collectors.toList());
    }

    // New method to get all users
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userConverter::toDTO).collect(Collectors.toList());
    }
}