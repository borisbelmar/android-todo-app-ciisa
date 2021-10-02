package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import cl.ciisa.todoapp.controllers.AuthController;
import cl.ciisa.todoapp.models.User;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private TextInputLayout tilEmail, tilPassword;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authController = new AuthController(this);
        authController.checkUserSession();

        btnRegister = findViewById(R.id.activity_login_btn_register);
        btnLogin = findViewById(R.id.activity_login_btn_login);
        tilEmail = findViewById(R.id.activity_login_field_email);
        tilPassword = findViewById(R.id.activity_login_field_password);

        btnLogin.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Iniciando sesión", Toast.LENGTH_SHORT).show();

            String email = tilEmail.getEditText().getText().toString();
            String password = tilPassword.getEditText().getText().toString();

            boolean emailValid = !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
            boolean passwordValid = !password.isEmpty();

            if (!emailValid) {
                tilEmail.setError("El email es inválido");
            } else {
                tilEmail.setError(null);
                tilEmail.setErrorEnabled(false);
            }

            if (!passwordValid) {
                tilPassword.setError("Campo requerido");
            } else {
                tilPassword.setError(null);
                tilPassword.setErrorEnabled(false);
            }

           if (emailValid && passwordValid) {
               authController.login(email, password);
           } else {
               Toast.makeText(view.getContext(), "Campos inválidos", Toast.LENGTH_SHORT).show();
           }
        });

        btnRegister.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), RegisterActivity.class);
            startActivity(i);
            finish();
        });
    }
}