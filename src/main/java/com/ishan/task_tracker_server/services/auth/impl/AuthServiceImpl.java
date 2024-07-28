package com.ishan.task_tracker_server.services.auth.impl;

import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.enums.UserRole;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.services.auth.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> optionalUser = userRepository.findUserByUserRole(UserRole.ADMIN);

        if(optionalUser.isEmpty()) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("12345"));
            admin.setUserRole(UserRole.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin Account has created!");
        } else {
            System.out.println("Admin user is already exists!");
        }
    }

}
