package com.protick.todomanagementrestapi.service;

import com.protick.todomanagementrestapi.dto.todo.TaskRequestDto;
import com.protick.todomanagementrestapi.exception.ResourceNotFoundException;
import com.protick.todomanagementrestapi.model.Task;
import com.protick.todomanagementrestapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findByUsername(getUserName());
    }



    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setUsername(getUserName());
        task.setTitle(taskRequestDto.title());
        task.setDescription(taskRequestDto.description());
        task.setCompleted(taskRequestDto.isCompleted());
        return taskRepository.save(task);
    }

    public Task updateTask(int id, TaskRequestDto taskRequestDto) {
        Predicate<? super Task> predicate = task -> task.getId() == id;
        var existingTask = taskRepository.findByUsername(getUserName())
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        existingTask.setId(id);
        existingTask.setTitle(taskRequestDto.title());
        existingTask.setDescription(taskRequestDto.description());
        existingTask.setUsername(getUserName());
        existingTask.setCompleted(taskRequestDto.isCompleted());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    private String getUserName() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
