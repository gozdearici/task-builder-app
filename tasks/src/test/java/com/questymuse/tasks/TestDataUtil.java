package com.questymuse.tasks;

import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.domain.entities.TaskPriority;
import com.questymuse.tasks.domain.entities.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class TestDataUtil {
    private TestDataUtil() {
    }

    /**
     * Creates a sample TaskList with no tasks.
     */
    public static TaskList createTaskListSampleA() {
        TaskList taskList = new TaskList();
        taskList.setTitle("Daily Tasks");
        taskList.setDescription("Tasks for the day");
        taskList.setCreatedDate(LocalDateTime.now());
        taskList.setUpdatedDate(LocalDateTime.now());
        return taskList;
    }

    public static TaskList createTaskListSampleB() {
        TaskList taskList = new TaskList();
        taskList.setTitle("Work Tasks");
        taskList.setDescription("Tasks for the week");
        taskList.setCreatedDate(LocalDateTime.now());
        taskList.setUpdatedDate(LocalDateTime.now());
        return taskList;
    }

    /**
     * Creates a sample Task linked to a TaskList.
     */
    public static Task createTaskSampleA(TaskList taskList) {
        Task task = new Task();
        task.setTitle("Buy groceries");
        task.setDescription("Milk, Bread, Eggs, Fruits");
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.OPEN);
        task.setCreatedDate(LocalDateTime.now());
        task.setUpdatedDate(LocalDateTime.now());
        return task;
    }

    public static Task createTaskSampleB(TaskList taskList) {
        Task task = new Task();
        task.setTitle("Clean the house");
        task.setDescription("Wash dishes, wipe the shelves, dust the bed");
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.OPEN);
        task.setCreatedDate(LocalDateTime.now());
        task.setUpdatedDate(LocalDateTime.now());
        task.setTaskList(taskList);

        return task;
    }

    /**
     * Creates a list of predefined tasks for testing bulk operations.
     */
    public List <Task> createMultipleTasksSample(TaskList taskList) {
        return List.of(
                createTaskSampleA(taskList),
                createTaskSampleB(taskList)
        );
    }
}
