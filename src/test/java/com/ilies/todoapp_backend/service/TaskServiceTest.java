package com.ilies.todoapp_backend.service;

import com.ilies.todoapp_backend.exception.TaskNotFoundException;
import com.ilies.todoapp_backend.model.Task;
import com.ilies.todoapp_backend.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    // Test pour vérifier la création d'une nouvelle tâche
    @Test
    public void testCreateTask() {
        Task task = new Task(null, "New Task", false);
        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("New Task", savedTask.getTitle());
        assertFalse(savedTask.isDone());
    }

    // Test pour vérifier la récupération d'une tâche par ID
    @Test
    public void testFindTaskById() {
        Task task = new Task(null, "Existing Task",  false);
        Task savedTask = taskRepository.save(task);

        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(savedTask.getId(), foundTask.get().getId());
    }

    // Test pour vérifier la gestion d'une exception lorsqu'une tâche n'est pas trouvée par ID
    @Test
    public void testFindTaskByIdNotFound() {
        assertThrows(TaskNotFoundException.class, () -> {
            TaskService taskService = new TaskService();
            taskService.setTaskRepository(taskRepository);
            taskService.findTaskById(9999L);
        });
    }

}

