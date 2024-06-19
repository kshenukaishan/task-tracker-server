package com.ishan.task_tracker_server.service.auth.impl;

import com.ishan.task_tracker_server.dto.SignUpRequest;
import com.ishan.task_tracker_server.dto.UserDto;
import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.enums.UserRole;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.service.auth.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> userOptional = userRepository.findByUserRole(UserRole.ADMIN);
        if(userOptional.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("User created successfully!");
        } else {
            System.out.println("Admin account already exists!");
        }
    }

    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
