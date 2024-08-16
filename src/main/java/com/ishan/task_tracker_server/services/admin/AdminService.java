package com.ishan.task_tracker_server.services.admin;

import com.ishan.task_tracker_server.dto.TaskDto;
import com.ishan.task_tracker_server.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);

}
