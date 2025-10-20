package com.questymuse.tasks.services;

import com.questymuse.tasks.TestDataUtil;
import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.repositories.TaskListRepository;
import com.questymuse.tasks.repositories.TaskRepository;
import com.questymuse.tasks.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TaskServiceImplUnitTests {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskList taskList;
    private Task task;

    public TaskServiceImplUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testThatListTasks() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());

        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setId(UUID.randomUUID());
        task.setTaskList(taskList);

        when(taskRepository.findByTaskListId(taskList.getId()))
                .thenReturn(List.of(task));

        List<Task> tasks = taskService.listTasks(taskList.getId());

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Buy groceries");
        verify(taskRepository, times(1)).findByTaskListId(taskList.getId());
    }

    @Test
    void testThatCreateTask() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());

        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setTaskList(taskList);

        when(taskListRepository.findById(taskList.getId())).thenReturn(Optional.of(taskList));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task created = taskService.createTask(taskList.getId(), task);

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Buy groceries");
        verify(taskListRepository, times(1)).findById(taskList.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testThatGetTaskById() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());

        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setId(UUID.randomUUID());
        task.setTaskList(taskList);

        when(taskRepository.findByTaskListIdAndId(taskList.getId(), task.getId()))
                .thenReturn(Optional.of(task));

        Optional<Task> found = taskService.getTaskById(taskList.getId(), task.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Buy groceries");
        verify(taskRepository, times(1)).findByTaskListIdAndId(taskList.getId(), task.getId());
    }

    @Test
    void testThatUpdateTask() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());

        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setId(UUID.randomUUID());
        task.setTaskList(taskList);

        Task updatedTaskData = new Task();
        updatedTaskData.setId(task.getId());
        updatedTaskData.setTitle("Updated Title");
        updatedTaskData.setDescription("Updated Description");
        updatedTaskData.setPriority(task.getPriority());
        updatedTaskData.setStatus(task.getStatus());
        updatedTaskData.setDueDate(task.getDueDate());
        updatedTaskData.setTaskList(taskList);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskData);

        Task updated = taskService.updateTask(taskList.getId(), task.getId(), updatedTaskData);

        assertThat(updated).isNotNull();
        assertThat(updated.getTitle()).isEqualTo("Updated Title");
        assertThat(updated.getDescription()).isEqualTo("Updated Description");
        verify(taskRepository, times(1)).findById(task.getId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testThatDeleteTask() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());

        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setId(UUID.randomUUID());
        task.setTaskList(taskList);

        doNothing().when(taskRepository).deleteByTaskListIdAndId(taskList.getId(), task.getId());

        taskService.deleteTask(taskList.getId(), task.getId());

        verify(taskRepository, times(1)).deleteByTaskListIdAndId(taskList.getId(), task.getId());
    }
}
