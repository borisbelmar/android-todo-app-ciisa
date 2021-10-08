package cl.ciisa.todoapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import cl.ciisa.todoapp.models.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT id, first_name, last_name, email, birthday, password FROM users WHERE email = :email LIMIT 1")
    UserEntity findByEmail (String email);

    @Insert
    long insert(UserEntity user);
}
