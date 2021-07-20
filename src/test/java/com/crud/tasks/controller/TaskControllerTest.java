package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
@SpringJUnitWebConfig
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetAllTasks() throws Exception {
        // Given
        when(service.getAllTasks()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "Content");
        TaskDto taskDto = new TaskDto(1L, "Task", "Content");

        when(service.getTaskById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .param("taskId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "Content");
        TaskDto taskDto = new TaskDto(1L, "task", "content");

        when(service.saveTask(any(Task.class))).thenReturn(new Task(1L, "Task", "Content"));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "Content");
        TaskDto taskDto = new TaskDto(1L, "Task", "Content");
        TaskDto taskDto2 = new TaskDto(2L, "updatedTask", "updatedContent");

        when(service.saveTask(task)).thenReturn(new Task(2L, "updatedTask", "updatedContent"));
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto2);
        when(taskMapper.mapToTask(any())).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updatedTask")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updatedContent")))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "Content");

        when(service.saveTask(task)).thenReturn(task);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask")
                        .param("taskId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}