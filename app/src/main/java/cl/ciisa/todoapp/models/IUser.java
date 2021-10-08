package cl.ciisa.todoapp.models;

import java.util.Date;

public interface IUser {
    long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Date getBirthday();
    String getPassword();
}
