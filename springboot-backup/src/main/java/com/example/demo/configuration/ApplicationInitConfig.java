// package com.example.demo.configuration;

// import java.util.HashSet;

// import javax.print.attribute.HashAttributeSet;

// import org.springframework.boot.ApplicationRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.example.demo.entity.UserEntity;
// import com.example.demo.enums.Role;
// import com.example.demo.repository.UserRepository;

// @Configuration
// public class ApplicationInitConfig {

// @Bean
// ApplicationRunner applicationRunner(UserRepository userRepository){
// return args -> {
// if(userRepository.findByUserName("Admin").isEmpty()){

// var roles = new HashSet<>();
// roles.add(Role.ADMIN.name());
// UserEntity userEntity = UserEntity.builder()
// .userName("Admin")
// .build()
// }

// };
// }
// }
