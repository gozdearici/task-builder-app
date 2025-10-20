package com.questymuse.tasks.services;

import com.questymuse.tasks.TestDataUtil;
import com.questymuse.tasks.domain.entities.TaskList;
import com.questymuse.tasks.repositories.TaskListRepository;
import com.questymuse.tasks.services.impl.TaskListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TaskListServiceImplUnitTests {

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListServiceImpl taskListService;

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskList = TestDataUtil.createTaskListSampleA();
        taskList.setId(UUID.randomUUID());
    }

    @Test
    void testListTaskLists() {
        when(taskListRepository.findAll()).thenReturn(List.of(taskList));

        List<TaskList> lists = taskListService.listTaskLists();

        assertThat(lists).hasSize(1);
        assertThat(lists.get(0).getTitle()).isEqualTo("Daily Tasks");
        verify(taskListRepository, times(1)).findAll();
    }

    @Test
    void testCreateTaskList() {
        TaskList newList = new TaskList();
        newList.setTitle("New List");
        newList.setDescription("New Description");

        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

        TaskList created = taskListService.createTaskList(newList);

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Daily Tasks");
        verify(taskListRepository, times(1)).save(any(TaskList.class));
    }

    @Test
    void testFindTaskListById() {
        when(taskListRepository.findById(taskList.getId())).thenReturn(Optional.of(taskList));

        Optional<TaskList> found = taskListService.findTaskListById(taskList.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Daily Tasks");
        verify(taskListRepository, times(1)).findById(taskList.getId());
    }

    @Test
    void testUpdateTaskList() {
        TaskList updatedData = new TaskList();
        updatedData.setId(taskList.getId());
        updatedData.setTitle("Updated Title");
        updatedData.setDescription("Updated Description");

        when(taskListRepository.findById(taskList.getId())).thenReturn(Optional.of(taskList));
        when(taskListRepository.save(taskList)).thenReturn(updatedData);

        TaskList updated = taskListService.updateTaskList(taskList.getId(), updatedData);

        assertThat(updated.getTitle()).isEqualTo("Updated Title");
        assertThat(updated.getDescription()).isEqualTo("Updated Description");
        verify(taskListRepository, times(1)).findById(taskList.getId());
        verify(taskListRepository, times(1)).save(taskList);
    }

    @Test
    void testDeleteTaskList() {
        doNothing().when(taskListRepository).deleteById(taskList.getId());

        taskListService.deleteTaskList(taskList.getId());

        verify(taskListRepository, times(1)).deleteById(taskList.getId());
    }
}
