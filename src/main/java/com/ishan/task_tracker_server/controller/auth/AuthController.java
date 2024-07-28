package com.ishan.task_tracker_server.controller.auth;

import com.ishan.task_tracker_server.dto.request.SignupRequest;
import com.ishan.task_tracker_server.dto.UserDto;
import com.ishan.task_tracker_server.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest request) {
        if(authService.isUserWithEmail(request.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User is already exists!");
        UserDto createdUserDto = authService.registerUser(request);
        if(createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("User has been created!");
        }
    }

}
