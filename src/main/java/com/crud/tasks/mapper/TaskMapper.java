package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Task mapToTask(TaskDto taskDto) {
        if (taskDto != null) {
            return new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getContent());
        } else {
            return new Task();
        }
    }

    public TaskDto mapToTaskDto(Task task) {
        if (task != null) {
            return new TaskDto(task.getId(), task.getTitle(), task.getContent());
        } else {
            return new TaskDto();
        }
    }

    public List<TaskDto> mapToTaskDtoList(List<Task> taskList) {
        if (taskList != null) {
            return taskList.stream()
                    .map(task -> new TaskDto(task.getId(), task.getTitle(), task.getContent()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
