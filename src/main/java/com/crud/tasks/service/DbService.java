package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbService {

    @Autowired
    private TaskRepository repository;

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> findTaskById(final Long id) {
        return repository.findById(id);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deletTaskById(final Long id) {
        repository.deleteById(id);
    }

    public void updateTask(final Task task) throws TaskNotFoundException {
        Integer result = repository.updateTask(task.getTitle(), task.getContent(), task.getId());
        if (result == 0) {
            throw new TaskNotFoundException(task.getId());
        }
    }
}
