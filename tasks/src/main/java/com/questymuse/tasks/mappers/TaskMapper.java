package com.questymuse.tasks.mappers;

import com.questymuse.tasks.domain.dto.TaskDto;
import com.questymuse.tasks.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
