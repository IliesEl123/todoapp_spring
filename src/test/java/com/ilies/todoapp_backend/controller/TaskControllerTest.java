package com.ilies.todoapp_backend.controller;

import com.ilies.todoapp_backend.model.Task;
import com.ilies.todoapp_backend.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    // Test pour récupérer toutes les tâches
    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.findAllTasks()).thenReturn(Arrays.asList(new Task(1L, "Task 1",  false),
                new Task(2L, "Task 2",  true)));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Task 1"))
                .andExpect(jsonPath("$.[1].title").value("Task 2"));

        verify(taskService, times(1)).findAllTasks();
    }

    // Test pour récupérer une tâche par ID
    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Task 1",  false);
        when(taskService.findTaskById(task.getId())).thenReturn(task);

        mockMvc.perform(get("/api/tasks/{id}", task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.isDone").value(false));

        verify(taskService, times(1)).findTaskById(task.getId());
    }

    // Test pour créer une nouvelle tâche
    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task(null, "New Task", false);
        Task savedTask = new Task(1L, "New Task",  false);
        when(taskService.saveTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Task\",\"isDone\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.isDone").value(false));

        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    // Test pour mettre à jour une tâche existante
    @Test
    public void testUpdateTask() throws Exception {
        Task updatedTask = new Task(1L, "Updated Task",  true);

        when(taskService.updateTask(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/tasks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"Updated Task\", \"isDone\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTask"));

        verify(taskService, times(1)).updateTask(any(Task.class));
    }

    // Test pour supprimer une tâche par ID
    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);
        mockMvc.perform(delete("/api/tasks/{id}", 1))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }

}
