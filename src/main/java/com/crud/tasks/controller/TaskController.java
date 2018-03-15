package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "tasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.findAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.findTaskById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        dbService.deletTaskById(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTask(@RequestBody TaskDto task) {
        dbService.updateTask(taskMapper.mapToTask(task));
    }

    @RequestMapping(method = RequestMethod.POST, value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        Task savedTask = dbService.saveTask(taskMapper.mapToTask(taskDto));
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }
}