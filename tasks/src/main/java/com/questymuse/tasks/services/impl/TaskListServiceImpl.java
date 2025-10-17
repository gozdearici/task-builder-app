package com.questymuse.tasks.services.impl;

import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.repositories.TaskListRepository;
import com.questymuse.tasks.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()) {
            throw new IllegalArgumentException("ID already is set");
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> findTaskListById(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(taskListId == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }

        if(!Objects.equals(taskListId, taskList.getId())){
            throw new IllegalArgumentException("Task list id must match the one provided");
        }

        TaskList existenceTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        existenceTaskList.setTitle(taskList.getTitle());
        existenceTaskList.setDescription(taskList.getDescription());
        existenceTaskList.setUpdatedDate(LocalDateTime.now());
        return taskListRepository.save(existenceTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        if(taskListId == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }

        taskListRepository.deleteById(taskListId);
    }
}
