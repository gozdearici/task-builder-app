package com.questymuse.tasks.services.impl;

import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.domain.entities.TaskPriority;
import com.questymuse.tasks.domain.entities.TaskStatus;
import com.questymuse.tasks.repositories.TaskListRepository;
import com.questymuse.tasks.repositories.TaskRepository;
import com.questymuse.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class TaskServiceImpl implements TaskService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(taskListId == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }

        if(task.getId() != null) {
            throw new IllegalArgumentException("Task has already an id");
        }

        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = Optional.ofNullable(task.getStatus()).orElse(TaskStatus.OPEN);
        LocalDateTime now = LocalDateTime.now();

        TaskList taskList = taskListRepository.findById(taskListId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid task list ID provided"));

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskPriority,
                taskStatus,
                now,
                now,
                taskList
        );

        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTaskById(UUID taskListId, UUID taskId){
        if(taskListId == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }
        if(taskId == null) {
            throw new IllegalArgumentException("Task must have an id");
        }

        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(taskListId == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }

        if(taskId == null) {
            throw new IllegalArgumentException("Task must have an id");
        }

        if(!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task id must match the one provided");
        }

        if(task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority cannot be null");
        }

        if(task.getStatus() == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }

        Task existenceTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existenceTask.setTitle(task.getTitle());
        existenceTask.setDescription(task.getDescription());
        existenceTask.setDueDate(task.getDueDate());
        existenceTask.setPriority(task.getPriority());
        existenceTask.setStatus(task.getStatus());
        existenceTask.setUpdatedDate(LocalDateTime.now());
        return taskRepository.save(existenceTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        if(taskId == null) {
            throw new IllegalArgumentException("Task id cannot be null");
        }

        if(taskListId == null) {
            throw new IllegalArgumentException("Task list id cannot be null");
        }

        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
