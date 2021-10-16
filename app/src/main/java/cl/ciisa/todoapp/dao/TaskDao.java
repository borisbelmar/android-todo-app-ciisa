package cl.ciisa.todoapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import cl.ciisa.todoapp.models.TaskEntity;

@Dao
public interface TaskDao {
    @Query("SELECT id, title, description, due, user_id FROM tasks WHERE user_id = :userId")
    List<TaskEntity> findAll (long userId);

    @Query("SELECT id, title, description, due, user_id FROM tasks WHERE user_id = :userId AND due BETWEEN :from AND :to")
    List<TaskEntity> findByRange (Date from, Date to, long userId);

    @Insert
    long insert(TaskEntity task);

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(long id);
}
