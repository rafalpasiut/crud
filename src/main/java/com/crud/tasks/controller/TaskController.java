package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/task")
public class TaskController {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {

        return taskMapper.mapToTaskDtoList(dbService.findAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.findTaskById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(@RequestParam Long taskId) {
        dbService.deletTaskById(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTask(@RequestBody TaskDto task) throws TaskNotFoundException {
        Integer result = dbService.updateTask(taskMapper.mapToTask(task));
        if (result == 0) {
            throw new TaskNotFoundException(task.getId());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        dbService.saveTask(taskMapper.mapToTask(taskDto));
    }
}
