package cl.ciisa.todoapp.lib;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cl.ciisa.todoapp.dao.UserDao;
import cl.ciisa.todoapp.models.UserEntity;
import cl.ciisa.todoapp.utils.Converters;

@Database(entities = {UserEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TodoAppDatabase extends RoomDatabase {
    private static final String DB_NAME = "todo_app_db";
    private static TodoAppDatabase instance;

    public static synchronized TodoAppDatabase getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(), TodoAppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
}
