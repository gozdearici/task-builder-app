package com.questymuse.tasks.controllers;

import com.questymuse.tasks.domain.dto.TaskDto;
import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.mappers.TaskMapper;
import com.questymuse.tasks.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path ="/api/task-lists/{task_list_id}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List <TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId).stream()
                                                .map(taskMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task createTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(createTask);
    }

    @GetMapping(path="/{task_id}")
    public Optional<TaskDto> getTaskById(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId){
        return taskService.getTaskById(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path="/{task_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto) {
        Task updateTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(updateTask);
    }

    @DeleteMapping("/{task_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
