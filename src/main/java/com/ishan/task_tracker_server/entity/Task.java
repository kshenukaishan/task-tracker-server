package com.ishan.task_tracker_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ishan.task_tracker_server.dto.TaskDto;
import com.ishan.task_tracker_server.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public TaskDto getTaskDto() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setEmployeeId(user.getId());
        taskDto.setEmployeeName(user.getName());
        taskDto.setDueDate(dueDate);
        taskDto.setPriority(priority);
        taskDto.setTaskStatus(taskStatus);
        return taskDto;
    }

}
