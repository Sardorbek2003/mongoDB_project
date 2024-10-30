package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo {
    private String id;
    private String userId;
    private String title;
    private boolean completed;


    public Todo(String id, String todoTitle, boolean b) {
        this.userId = id;
        this.title = todoTitle;
        this.completed = b;
    }
}
