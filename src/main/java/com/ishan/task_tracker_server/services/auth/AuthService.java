package com.ishan.task_tracker_server.services.auth;

import com.ishan.task_tracker_server.dto.SignupRequest;
import com.ishan.task_tracker_server.dto.UserDto;

public interface AuthService {

    UserDto registerUser(SignupRequest signupRequest);

    boolean isUserWithEmail(String email);

}
