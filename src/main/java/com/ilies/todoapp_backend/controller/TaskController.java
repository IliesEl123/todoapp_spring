package com.ilies.todoapp_backend.controller;

import com.ilies.todoapp_backend.dto.TaskDto;
import com.ilies.todoapp_backend.model.Task;
import com.ilies.todoapp_backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Convertit une tâche en DTO
    private TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.isDone());
    }

    // Convertit un DTO en tâche
    private Task toEntity(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getTitle(), taskDto.isDone());
    }

    // Récupère la liste de toutes les tâches
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        List<TaskDto> taskDtos = tasks.stream().map(this::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    // Récupère une tâche par ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        return new ResponseEntity<>(toDto(task), HttpStatus.OK);
    }

    // Crée une nouvelle tâche
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = toEntity(taskDto);
        Task createdTask = taskService.saveTask(task);
        return new ResponseEntity<>(toDto(createdTask), HttpStatus.CREATED);
    }

    // Met à jour une tâche existante
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task taskToUpdate = toEntity(taskDto);
        taskToUpdate.setId(id);
        Task updatedTask = taskService.updateTask(taskToUpdate);
        return new ResponseEntity<>(toDto(updatedTask), HttpStatus.OK);
    }

    // Supprime une tâche par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Supprime toutes les tâches
    @DeleteMapping("/tasks")
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.ok().build();
    }
}
