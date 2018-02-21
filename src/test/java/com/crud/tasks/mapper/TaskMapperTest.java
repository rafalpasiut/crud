package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskMapperTest {

    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L,"title", "content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(task.getTitle(),taskDto.getTitle());
        Assert.assertEquals(task.getContent(),taskDto.getContent());
        Assert.assertEquals(task.getId(),taskDto.getId());
    }

    @Test
    public void mapToTaskNull() {
        //Given
        TaskDto taskDto = null;
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertNotNull(task);
    }

    @Test
    public void mapToTaskEmptyTask() {
        //Given
        TaskDto taskDto = new TaskDto();
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getId(),taskDto.getId());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(1L,"title","content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(task.getTitle(),taskDto.getTitle());
        Assert.assertEquals(task.getContent(),taskDto.getContent());
        Assert.assertEquals(task.getId(),taskDto.getId());
    }

    @Test
    public void mapToTaskDtoNull() {
        //Given
        Task task = null;
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertNotNull(taskDto);
    }

    @Test
    public void mapToTaskDtoEmpty() {
        //Given
        Task task = new Task();
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(task.getTitle(),taskDto.getTitle());
        Assert.assertEquals(task.getContent(),taskDto.getContent());
        Assert.assertEquals(task.getId(),taskDto.getId());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        Task task1 = new Task(1L,"title1","content1");
        Task task2 = new Task(2L,"title2","content2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        Assert.assertEquals(2,taskDtos.size());
        Assert.assertEquals("title2",taskDtos.get(1).getTitle());
    }

    @Test
    public void mapToTaskDtoListNull() {
        //Given
        List<Task> tasks = null;
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        Assert.assertNotNull(taskDtos);
        Assert.assertEquals(0,taskDtos.size());
    }

    @Test
    public void mapToTaskDtoListEmptyList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        Assert.assertNotNull(taskDtos);
        Assert.assertEquals(0,taskDtos.size());
    }
}