package com.protick.todomanagementrestapi.controller;

import com.protick.todomanagementrestapi.dto.todo.TaskRequestDto;
import com.protick.todomanagementrestapi.dto.todo.TaskResponseDto;
import com.protick.todomanagementrestapi.model.Task;
import com.protick.todomanagementrestapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private TaskService taskService;

    public TodoController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> taskListResponse = new ArrayList<>();
        taskService.getAllTasks().forEach(
                task -> {
                    taskListResponse.add(TaskResponseDto.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .description(task.getDescription())
                            .isCompleted(task.isCompleted())
                            .build());
                }
        );
        return new ResponseEntity<>(taskListResponse, HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<Objects> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        var newTask = taskService.createTask(taskRequestDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTask.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable int id, @RequestBody TaskRequestDto taskRequestDto) {
        var updatedTask = taskService.updateTask(id, taskRequestDto);
        var response = TaskResponseDto.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .isCompleted(updatedTask.isCompleted())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDto> getTaskByID(@PathVariable int id) {
        var task = taskService.getTaskById(id);
        var response = TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .isCompleted(task.isCompleted())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
