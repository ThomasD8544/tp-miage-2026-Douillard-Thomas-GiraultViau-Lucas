package com.acme.todolist.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TodoItemTest {

    @Test
    public void devraitRetournerUnTodoItemAvecLate() {
        TodoItem item = new TodoItem("1", Instant.parse("2020-02-27T10:31:43Z"), "Ma tâche");

        String finalContent = item.finalContent();

        Assertions.assertThat(finalContent).startsWith("[LATE!]");
    }

    @Test
    public void devraitRetournerUnTodoItemSansLate() {
        TodoItem item = new TodoItem("1", Instant.now(), "Ma tâche");

        String finalContent = item.finalContent();

        Assertions.assertThat(finalContent).doesNotStartWith("[LATE!]");
    }
}