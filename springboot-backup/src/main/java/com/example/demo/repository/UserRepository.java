package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserEntity;

// import java.util.List;
// // import java.util.List;
// import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneById(Long id);

    // boolean existsByUserName(String userName);

    UserEntity findByUserName(String userName);

    UserEntity findByPassword(String password);

    boolean existsByUserName(String userName);

    // boolean existsByUsername(String userName);
}
