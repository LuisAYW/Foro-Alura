package com.api.ForoHub.Topic;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record TopicDTO(
    @NotEmpty(message = "El título no puede estar vacío")
    String title,

    @NotEmpty(message = "El mensaje no puede estar vacío")
    String message,

    @NotNull(message = "El autor no puede estar vacío")
    Integer authorId,

    @NotNull(message = "El curso no puede estar vacío")
    Integer courseId) 
{}
