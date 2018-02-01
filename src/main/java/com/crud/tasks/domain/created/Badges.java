package com.crud.tasks.domain.created;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges {

    private Integer votes;
    private AttachmentsByType attachmentsByType;
}
