package com.ishan.task_tracker_server.dto;

import com.ishan.task_tracker_server.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;

}
