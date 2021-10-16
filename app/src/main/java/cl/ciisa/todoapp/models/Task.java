package cl.ciisa.todoapp.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable, ITask {
    private long id;
    private String title;
    private String description;
    private Date due;
    private long userId;

    public Task(String title, String description, Date due, long userId) {
        this.title = title;
        this.description = description;
        this.due = due;
        this.userId = userId;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDue() {
        return due;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    public String getStringDue() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(due);
    }
}
