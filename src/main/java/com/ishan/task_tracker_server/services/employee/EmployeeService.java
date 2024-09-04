package com.ishan.task_tracker_server.services.employee;

import com.ishan.task_tracker_server.dto.TaskDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTasksByUserId();

    TaskDto updateTask(Long id, String status);

}
