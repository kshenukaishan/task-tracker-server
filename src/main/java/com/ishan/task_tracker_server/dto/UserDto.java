package com.ishan.task_tracker_server.dto;

import com.ishan.task_tracker_server.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;

}
