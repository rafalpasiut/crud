package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbService {

    @Autowired
    private TaskRepository repository;

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public Task findTaskById(String id) throws NumberFormatException {
        return repository.findById(Long.decode(id)).get();
    }
}
