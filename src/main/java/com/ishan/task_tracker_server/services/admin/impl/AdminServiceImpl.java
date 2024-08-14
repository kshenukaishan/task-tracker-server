package com.ishan.task_tracker_server.services.admin.impl;

import com.ishan.task_tracker_server.dto.TaskDto;
import com.ishan.task_tracker_server.dto.UserDto;
import com.ishan.task_tracker_server.entity.Task;
import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.enums.UserRole;
import com.ishan.task_tracker_server.repository.TaskRepository;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if(optionalUser.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(taskDto.getTaskStatus());
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }
}
