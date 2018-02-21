package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void findAllTasksEmptyList() {
        //Given
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());
        //When
        List<Task> tasks = dbService.findAllTasks();
        //Then
        Assert.assertEquals(0, tasks.size());
        Assert.assertNotNull(tasks);
    }

    @Test
    public void findAllTasks() {
        //Given
        List<Task> tasksInDb = new ArrayList<>();
        tasksInDb.add(new Task(1L, "title1", "content1"));
        tasksInDb.add(new Task(2L, "title2", "content2"));
        when(taskRepository.findAll()).thenReturn(tasksInDb);
        //When
        List<Task> tasks = dbService.findAllTasks();
        //Then
        Assert.assertEquals(2, tasks.size());
        Assert.assertEquals(tasks.get(1).getTitle(), tasksInDb.get(1).getTitle());
    }

    @Test
    public void findTaskByIdNoTask() {
        //Given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        //When
        Optional<Task> task = dbService.findTaskById(1L);
        //Then
        Assert.assertFalse(task.isPresent());
    }

    @Test
    public void findTaskById() {
        //Given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task(1L, "title1", "content1")));
        //When
        Optional<Task> task = dbService.findTaskById(1L);
        //Then
        Assert.assertEquals("title1", task.get().getTitle());
        Assert.assertEquals("content1", task.get().getContent());
    }

    @Test
    public void saveTask() {
        //Given
        Task taskToSave = new Task(1L, "title1", "content1");
        when(taskRepository.save(taskToSave)).thenReturn(taskToSave);
        //When
        Task task = dbService.saveTask(taskToSave);
        //Then
        Assert.assertEquals("title1", task.getTitle());
        Assert.assertEquals("content1", task.getContent());
    }

    @Test
    public void updateTask() {
        //Given
        Task taskToUpdate = new Task(1L, "title1", "content1");
        when(taskRepository.updateTask("title1", "content1", 1L)).thenReturn(1);
        //When
        Integer id = dbService.updateTask(taskToUpdate);
        //Then
        Assert.assertEquals((Long) id.longValue(), taskToUpdate.getId());
    }

    @Test
    public void deletTaskByIdNull(){
        //When
        dbService.deletTaskById(null);
        //Then
        //Ok if no exception
    }

    @Test
    public void deletTaskById(){
        //When
        dbService.deletTaskById(1L);
        //Then
        //Ok if no exception
    }

}