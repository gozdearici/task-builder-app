package com.questymuse.tasks.services;

import com.questymuse.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List <TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional <TaskList> findTaskListById(UUID id);
    TaskList updateTaskList(UUID taskListId, TaskList taskList);
    void deleteTaskList(UUID taskListId);
}
