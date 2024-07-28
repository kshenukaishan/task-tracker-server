package com.ishan.task_tracker_server.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
