package com.rhsnc.ovpe.service;

import com.rhsnc.ovpe.domain.Todo;
import com.rhsnc.ovpe.mapper.TodoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DoServiceImpl implements DoService {

    private final TodoMapper todoMapper;

    public DoServiceImpl(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        try {
            return todoMapper.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all todos", e);
        }
    }
}
