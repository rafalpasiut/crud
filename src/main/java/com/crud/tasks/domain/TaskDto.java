package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String content;

    public TaskDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
