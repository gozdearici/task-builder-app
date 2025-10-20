package com.questymuse.tasks.repositories;

import com.questymuse.tasks.TestDataUtil;
import com.questymuse.tasks.domain.entities.Task;
import com.questymuse.tasks.domain.entities.TaskList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TaskRepositoryIntegrationTests {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Test
    @DisplayName("Save and find Task by ID")
    public void testThatSaveAndFindByIdTask() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setTaskList(taskList);

        TaskList savedTaskList = taskListRepository.save(taskList);
        task.setTaskList(savedTaskList);
        Task savedTask = taskRepository.save(task);

        UUID taskId = savedTask.getId();

        assertThat(taskRepository.findById(taskId)).isPresent();
        assertThat(taskRepository.findById(taskId).get().getTitle())
                                .isEqualTo(task.getTitle());
    }

    @Test
    @DisplayName("Find all Tasks by TaskList ID")
    public void testFindByTaskListId() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();

        Task task1 = TestDataUtil.createTaskSampleA(taskList);
        Task task2 = TestDataUtil.createTaskSampleB(taskList);
        task1.setTaskList(taskList);
        task2.setTaskList(taskList);

        TaskList savedTaskList = taskListRepository.save(taskList);
        task1.setTaskList(savedTaskList);
        task2.setTaskList(savedTaskList);

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> tasks = taskRepository.findByTaskListId(savedTaskList.getId());
        assertThat(tasks).hasSize(2);
    }

    @Test
    @DisplayName("Delete Task by ID and TaskList ID")
    public void testDeleteByTaskListIdAndTaskId() {
        TaskList taskList = TestDataUtil.createTaskListSampleA();
        Task task = TestDataUtil.createTaskSampleA(taskList);
        task.setTaskList(taskList);

        TaskList savedTaskList = taskListRepository.save(taskList);
        task.setTaskList(savedTaskList);
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteByTaskListIdAndId(savedTaskList.getId(), savedTask.getId());

        assertThat(taskRepository.findById(savedTask.getId())).isNotPresent();
    }
}
