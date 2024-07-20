package com.example.demo.configuration;

import java.util.Collections;
// import java.util.HashSet;

// import javax.print.attribute.HashAttributeSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.constant.PredefinedRole;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
// import com.example.demo.enums.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.experimental.NonFinal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUserName(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .build());

                RoleEntity adminRole = roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .build());

                // var roles = new HashSet<Role>();
                // roles.add(adminRole);

                UserEntity user = UserEntity.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(Collections.singletonList(adminRole))
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }

}
