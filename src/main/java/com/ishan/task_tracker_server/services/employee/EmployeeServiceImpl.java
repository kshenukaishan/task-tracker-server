package com.ishan.task_tracker_server.services.employee;

import com.ishan.task_tracker_server.dto.TaskDto;
import com.ishan.task_tracker_server.entity.Task;
import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.repository.TaskRepository;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaskRepository taskRepository;
    private final JwtUtils jwtUtils;

    @Override
    public List<TaskDto> getTasksByUserId() {
        User loggedInUser = jwtUtils.getLoggedInUser();
        if(loggedInUser != null) {
            return taskRepository.findAllByUserId(loggedInUser.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
