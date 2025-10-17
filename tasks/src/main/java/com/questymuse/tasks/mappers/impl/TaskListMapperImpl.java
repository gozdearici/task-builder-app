package com.questymuse.tasks.mappers.impl;

import com.questymuse.tasks.domain.dto.TaskDto;
import com.questymuse.tasks.domain.dto.TaskListDto;
import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.domain.entities.TaskStatus;
import com.questymuse.tasks.mappers.TaskListMapper;
import com.questymuse.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                mapTasksFromTaskListDto(taskListDto),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                mapTasksToDto(taskList),
                calculateTasksSize(taskList.getTasks()),
                calculateTaskListProgress(taskList.getTasks())
        );
    }

    private List<Task> mapTasksFromTaskListDto(TaskListDto taskListDto) {
        return Optional.ofNullable(taskListDto.tasks())
                .map(tasks -> tasks.stream()
                        .map(taskMapper::fromDto)
                        .toList()
                ).orElse(null);
    }

    private List<TaskDto> mapTasksToDto(TaskList taskList) {
        return Optional.ofNullable(taskList.getTasks())
                .map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null);
    }

    public Double calculateTaskListProgress(List<Task> tasks) {
        if(null == tasks) return null;

        long closedTaskCount = tasks.stream()
                                    .filter(task -> task.getStatus() == TaskStatus.CLOSED)
                                    .count();

        return (double) closedTaskCount / tasks.size();
    }

    public int calculateTasksSize(List<Task> tasks) {
        return Optional.ofNullable(tasks).map(List::size).orElse(0);
    }
}
