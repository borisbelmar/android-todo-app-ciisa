package cl.ciisa.todoapp.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import java.util.Date;

import cl.ciisa.todoapp.LoginActivity;
import cl.ciisa.todoapp.MainActivity;
import cl.ciisa.todoapp.dao.UserDao;
import cl.ciisa.todoapp.lib.BCrypt;
import cl.ciisa.todoapp.lib.TodoAppDatabase;
import cl.ciisa.todoapp.models.User;
import cl.ciisa.todoapp.models.UserEntity;
import cl.ciisa.todoapp.models.UserMapper;

public class AuthController {
    private final String KEY_USER_ID = "userId";
    private final String KEY_EMAIL = "userEmail";
    private final String KEY_FIRST_NAME = "userFirstName";
    private final String KEY_LAST_NAME = "userLastName";

    private UserDao userDao;
    private Context ctx;
    private SharedPreferences preferences;

    public AuthController(Context ctx) {
        this.ctx = ctx;
        int PRIVATE_MODE = 0;
        String PREF_NAME = "TodoAppPref";
        this.preferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.userDao = TodoAppDatabase.getInstance(ctx).userDao();
    }

    private void setUserSession(User user) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putLong(KEY_USER_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.apply();
    }

    public User getUserSession() {
        long id = preferences.getLong(KEY_USER_ID, 0);
        String firstName = preferences.getString(KEY_FIRST_NAME, "");
        String lastName = preferences.getString(KEY_LAST_NAME, "");
        String email = preferences.getString(KEY_EMAIL, "");

        User user = new User(firstName, lastName, email, new Date());
        user.setId(id);

        return user;
    }

    public void checkUserSession() {
        long id = preferences.getLong(KEY_USER_ID, 0);

        final int TIMEOUT = 3000;

        new Handler().postDelayed(() -> {
            if (id != 0) {
                Toast.makeText(ctx, "Bienvenido denuevo", Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, MainActivity.class);
                ctx.startActivity(i);
            } else {
                Intent i = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(i);
            }
            ((Activity) ctx).finish();
        }, TIMEOUT);
    }

    public void register(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        UserEntity userEntity = new UserMapper(user).toEntity();

        userDao.insert(userEntity);

        Toast.makeText(ctx, String.format("Usuario %s registrado", user.getEmail()), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(i);
    }

    public void login(String email, String password) {
        UserEntity userEntity = userDao.findByEmail(email);

        if (userEntity == null) {
            Toast.makeText(ctx, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new UserMapper(userEntity).toBase();

        if (BCrypt.checkpw(password, user.getPassword())) {
            setUserSession(user);
            Toast.makeText(ctx, String.format("Bienvenido %s", email), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ctx, MainActivity.class);
            ctx.startActivity(i);
            ((Activity) ctx).finish();
        } else {
            Toast.makeText(ctx, String.format("Credenciales inválidas", email), Toast.LENGTH_SHORT).show();
        }
    }

    public void logout() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(ctx, "Cerrando Sesión", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ctx, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
        ((Activity) ctx).finish();
    }
}
