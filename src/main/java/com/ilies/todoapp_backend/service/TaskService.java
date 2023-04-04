package com.ilies.todoapp_backend.service;

import com.ilies.todoapp_backend.exception.TaskNotFoundException;
import com.ilies.todoapp_backend.model.Task;
import com.ilies.todoapp_backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Récupère toutes les tâches
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // Récupère une tâche par ID
    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    }

    // Crée ou met à jour une tâche
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Met à jour une tâche existante
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    // Supprime une tâche par ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Supprime toutes les tâches
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    // Ajout d'un setter pour permettre l'injection de taskRepository dans les tests
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

}
