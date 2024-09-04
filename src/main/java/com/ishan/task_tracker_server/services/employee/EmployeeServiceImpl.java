package com.ishan.task_tracker_server.services.employee;

import com.ishan.task_tracker_server.dto.TaskDto;
import com.ishan.task_tracker_server.entity.Task;
import com.ishan.task_tracker_server.entity.User;
import com.ishan.task_tracker_server.enums.TaskStatus;
import com.ishan.task_tracker_server.repository.TaskRepository;
import com.ishan.task_tracker_server.repository.UserRepository;
import com.ishan.task_tracker_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> taskById = taskRepository.findById(id);
        if(taskById.isPresent()){
            Task existingTask = taskById.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {
            case "PENDING" -> TaskStatus.PENDING;
            case "IN PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DIFFERED" -> TaskStatus.DIFFERED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
