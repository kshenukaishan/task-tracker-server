package com.ishan.task_tracker_server.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String email;
    private String password;

}
