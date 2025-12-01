<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .completed {
            text-decoration: line-through;
            color: #888;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1>Todo List</h1>
        
        <div class="card mb-4">
            <div class="card-body">
                <form id="todoForm">
                    <div class="mb-3">
                        <input type="text" class="form-control" id="title" placeholder="제목" required>
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" id="description" placeholder="설명"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">추가</button>
                </form>
            </div>
        </div>

        <div id="ovpeList">
            <c:forEach items="${todos}" var="todo">
                <div class="card mb-2" data-id="${todo.id}">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="card-title ${todo.completed ? 'completed' : ''}">${todo.title}</h5>
                                <p class="card-text ${todo.completed ? 'completed' : ''}">${todo.description}</p>
                            </div>
                            <div>
                                <button class="btn btn-success btn-sm toggle-btn">${todo.completed ? '완료 취소' : '완료'}</button>
                                <button class="btn btn-danger btn-sm delete-btn">삭제</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // Todo 추가
            $('#todoForm').on('submit', function(e) {
                e.preventDefault();
                const todo = {
                    title: $('#title').val(),
                    description: $('#description').val()
                };

                $.ajax({
                    url: '/todos',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(todo),
                    success: function(response) {
                        location.reload();
                    }
                });
            });

            // Todo 상태 토글
            $('.toggle-btn').on('click', function() {
                const todoId = $(this).closest('.card').data('id');
                $.ajax({
                    url: '/todos/' + todoId + '/toggle',
                    type: 'PUT',
                    success: function(response) {
                        location.reload();
                    }
                });
            });

            // Todo 삭제
            $('.delete-btn').on('click', function() {
                const todoId = $(this).closest('.card').data('id');
                $.ajax({
                    url: '/todos/' + todoId,
                    type: 'DELETE',
                    success: function() {
                        location.reload();
                    }
                });
            });
        });
    </script>
</body>
</html> 