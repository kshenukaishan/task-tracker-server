package com.ishan.task_tracker_server.service.auth.impl;

import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.service.auth.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
