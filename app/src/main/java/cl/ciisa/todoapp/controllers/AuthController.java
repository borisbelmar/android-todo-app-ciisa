package cl.ciisa.todoapp.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import cl.ciisa.todoapp.LoginActivity;
import cl.ciisa.todoapp.MainActivity;
import cl.ciisa.todoapp.lib.BCrypt;
import cl.ciisa.todoapp.models.User;

public class AuthController {
    private final String KEY_USER_ID = "userId";
    private final String KEY_EMAIL = "userEmail";
    private final String KEY_FIRST_NAME = "userFirstName";
    private final String KEY_LAST_NAME = "userLastName";

    private Context ctx;
    private SharedPreferences preferences;

    public AuthController(Context ctx) {
        this.ctx = ctx;
        int PRIVATE_MODE = 0;
        String PREF_NAME = "TodoAppPref";
        this.preferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
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
        if (id != 0) {
            Intent i = new Intent(ctx, MainActivity.class);
            ctx.startActivity(i);
            ((Activity) ctx).finish();
        }
    }

    public void register(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Implementar el registro en Base de datos

        Toast.makeText(ctx, String.format("Usuario %s registrado", user.getEmail()), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(i);
    }

    public void login(String email, String password) {
        User user = new User("Boris", "Belmar", "bbelmar@ciisa.cl", new Date());
        user.setPassword("$2a$10$YtWAhrL.q45dscD8PUt/zOswiimOai0EsHTsfBm5S6rfVC1sILGQC");
        user.setId(1);
        if (BCrypt.checkpw(password, user.getPassword())) {
            setUserSession(user);
            Toast.makeText(ctx, String.format("Bienvenido %s", email), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ctx, MainActivity.class);
            ctx.startActivity(i);
            ((Activity) ctx).finish();
        } else {
            Toast.makeText(ctx, String.format("La contraseña es incorrecta", email), Toast.LENGTH_SHORT).show();
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
