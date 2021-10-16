package cl.ciisa.todoapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class TaskEntity implements ITask {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "due")
    private Date due;

    @ColumnInfo(name = "user_id")
    private long userId;

    public TaskEntity(long id, String title, String description, Date due, long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due = due;
        this.userId = userId;
    }

    @Override
    public long getId() {
        return id;
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
}
