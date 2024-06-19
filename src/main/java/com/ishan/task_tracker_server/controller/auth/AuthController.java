package com.ishan.task_tracker_server.controller.auth;

import com.ishan.task_tracker_server.dto.SignUpRequest;
import com.ishan.task_tracker_server.dto.UserDto;
import com.ishan.task_tracker_server.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        if(authService.hasUserWithEmail(signUpRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email is already taken!");
        UserDto createdUserDto = authService.signUpUser(signUpRequest);
        if(createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

}
