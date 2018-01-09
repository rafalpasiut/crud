package com.crud.tasks.controller;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super("Task of ID: \"" + id + "\" not found");
    }
}
