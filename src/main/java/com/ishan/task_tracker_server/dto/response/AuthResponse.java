package com.ishan.task_tracker_server.dto.response;

import com.ishan.task_tracker_server.enums.UserRole;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;

}
