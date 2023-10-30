package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 1;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(taskIdCounter++);
        task.setCreateDate(new Date());
        tasks.add(task);
        return task;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        Task existingTask = tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
            existingTask.setCompletedDate(updatedTask.getCompletedDate());
            return existingTask;
        }
        return null;
    }
}
