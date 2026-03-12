package com.acme.todolist.adapters.rest_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.acme.todolist.application.port.in.AddTodoItem;
import com.acme.todolist.application.port.in.GetTodoItems;
import com.acme.todolist.domain.TodoItem;

@WebMvcTest(controllers = TodoListController.class)
class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTodoItems getTodoItems;

    @MockBean
    private AddTodoItem addTodoItem;

    @Test
    void should_return_all_todo_items() throws Exception {
        TodoItem todoItem = new TodoItem(
                "1",
                Instant.parse("2026-03-12T15:00:00Z"),
                "Faire les courses",
                true);

        when(getTodoItems.getTodoItems()).thenReturn(List.of(todoItem));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].content").value("Faire les courses"))
                .andExpect(jsonPath("$[0].visible").value(true));
    }

    @Test
    void should_create_a_todo_item() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  \"id\": \"99\",
                                  \"time\": \"2026-03-12T15:00:00Z\",
                                  \"content\": \"Test exo 3\",
                                  \"visible\": true
                                }
                                """))
                .andExpect(status().isCreated());

        verify(addTodoItem, times(1)).addTodoItem(any(TodoItem.class));
    }
}