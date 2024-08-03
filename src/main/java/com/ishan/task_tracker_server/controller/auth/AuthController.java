package com.ishan.task_tracker_server.controller.auth;

import com.ishan.task_tracker_server.dto.request.LoginRequest;
import com.ishan.task_tracker_server.dto.request.SignupRequest;
import com.ishan.task_tracker_server.dto.UserDto;
import com.ishan.task_tracker_server.dto.response.AuthResponse;
import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.services.auth.AuthService;
import com.ishan.task_tracker_server.services.jwt.UserService;
import com.ishan.task_tracker_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest request) {
        if(authService.isUserWithEmail(request.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User is already exists!");
        UserDto createdUserDto = authService.registerUser(request);
        if(createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials not valid");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(request.getEmail());
        final String token = jwtUtils.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse();
        if(optionalUser.isPresent()) {
            authResponse.setJwt(token);
            authResponse.setUserId(optionalUser.get().getId());
            authResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authResponse;
    }

}
