package com.rhsnc.ovpe.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Todo {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 