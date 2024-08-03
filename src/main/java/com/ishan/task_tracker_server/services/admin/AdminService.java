package com.ishan.task_tracker_server.services.admin;

import com.ishan.task_tracker_server.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

}
