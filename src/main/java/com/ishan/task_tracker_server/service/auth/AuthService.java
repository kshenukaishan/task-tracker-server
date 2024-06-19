package com.ishan.task_tracker_server.service.auth;

import com.ishan.task_tracker_server.dto.SignUpRequest;
import com.ishan.task_tracker_server.dto.UserDto;

public interface AuthService {

    UserDto signUpUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);

}
