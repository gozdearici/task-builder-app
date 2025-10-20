package com.questymuse.tasks.repositories;

import com.questymuse.tasks.TestDataUtil;
import com.questymuse.tasks.domain.entities.TaskList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TaskListRepositoryIntegrationTests {
    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Task List Save and Find By ID")
    public void testThatSaveAndFindByIdTasks() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        TaskList savedTaskList = taskListRepository.save(taskList);

        UUID taskListId = savedTaskList.getId();

        assertThat(taskListRepository.findById(taskListId)).isPresent();
        assertThat(taskListRepository.findById(taskListId).get().getTitle())
                                    .isEqualTo(taskList.getTitle());
    }

    @Test
    @DisplayName("Find All Task List")
    public void testThatFindAllTasks() {
        TaskList taskListSampleA = TestDataUtil.createTaskListSampleA();
        TaskList taskListSampleB = TestDataUtil.createTaskListSampleB();
        TaskList savedTaskList1 = taskListRepository.save(taskListSampleA);
        TaskList savedTaskList2 = taskListRepository.save(taskListSampleB);

        assertThat(taskListRepository.findAll()).hasSize(2);
        assertThat(taskListRepository.findAll()).contains(savedTaskList1, savedTaskList2);
    }


    @Test
    @DisplayName("TaskList delete should remove TaskList and cascade delete Tasks")
    public void testDeleteTaskListCascadesTasks() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();

        TaskList savedTaskList = taskListRepository.save(taskList);

        UUID taskListId = savedTaskList.getId();

        taskListRepository.deleteById(taskListId);

        assertThat(taskListRepository.findById(taskListId)).isNotPresent();
    }
}
