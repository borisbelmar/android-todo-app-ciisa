package cl.ciisa.todoapp.models;

import java.util.Date;

public interface ITask {
    long getId();
    String getTitle();
    String getDescription();
    Date getDue();
    long getUserId();
}
