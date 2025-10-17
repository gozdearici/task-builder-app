package com.questymuse.tasks.mappers;

import com.questymuse.tasks.domain.dto.TaskListDto;
import com.questymuse.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
