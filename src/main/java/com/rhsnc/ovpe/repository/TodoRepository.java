package com.rhsnc.ovpe.repository;

import com.rhsnc.ovpe.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
} 