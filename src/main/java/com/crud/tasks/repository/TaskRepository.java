package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();

    @Override
    Task save(Task task);

    @Override
    Optional<Task> findById(Long id);

    @Query(value = "UPDATE tasks SET name = :NAME, description = :DESCRIPTION WHERE ID = :ID")
    @Modifying
    @Transactional
    Integer updateTask(@Param("NAME") String name, @Param("DESCRIPTION") String description, @Param("ID") Long id);

    @Override
    long count();
}
