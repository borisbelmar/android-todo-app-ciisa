package cl.ciisa.todoapp.models;

import java.io.Serializable;

public class Task implements Serializable {
    private long id;
    private String title;
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
