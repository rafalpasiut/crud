package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "title1", "content1"));
        taskDtos.add(new TaskDto(2L, "title2", "content2"));
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[1].content", is("content2")));
    }

    @Test
    public void shouldGetEmptyTasks() throws Exception {
        //Given
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(dbService.findTaskById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(new Task()));
        //When & Then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldThrowExceptionGetTask() throws Exception {
        //Given
        Long taskId = 1L;
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).then((Answer) invocation -> {
            throw new TaskNotFoundException(taskId);
        });
        //When & Then
        try {
            mockMvc.perform(get("/v1/task/getTask").param("taskId", "1"));
            Assert.fail("Exception not thrown");
        } catch (Exception e) {
            Assert.assertEquals(e.getCause().getClass(), TaskNotFoundException.class);
        }
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/v1/task/deleteTask").param("taskId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        Gson gson = new Gson();
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(taskDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        Gson gson = new Gson();
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(taskDto)))
                .andExpect(status().isOk());
    }
}