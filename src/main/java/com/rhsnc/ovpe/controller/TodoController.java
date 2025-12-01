package com.rhsnc.ovpe.controller;

import com.rhsnc.ovpe.entity.Todo;
import com.rhsnc.ovpe.service.TodoService;
import com.rhsnc.ovpe.service.DoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final DoService doService;

    // JSP용 엔드포인트 (기존 유지)
    @GetMapping("/list")
    public String listTodos(Model model) {
        model.addAttribute("todos", doService.findAll());
        model.addAttribute("newTodo", new Todo());
        return "todo/list";
    }

    // Vue.js용 REST API 엔드포인트
    @GetMapping
    @ResponseBody
    public java.util.List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    @ResponseBody
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("/{id}/toggle")
    @ResponseBody
    public Todo toggleTodoStatus(@PathVariable Long id) {
        return todoService.toggleTodoStatus(id);
    }
} 