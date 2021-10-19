package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import cl.ciisa.todoapp.controllers.AuthController;
import cl.ciisa.todoapp.lib.TilValidator;
import cl.ciisa.todoapp.models.User;
import cl.ciisa.todoapp.ui.DatePickerFragment;
import cl.ciisa.todoapp.utils.DateUtils;

public class RegisterActivity extends AppCompatActivity {
    private final String DATE_PATTERN = "yyyy-MM-dd";
    private TextInputLayout tilBirthday, tilFirstName, tilLastName, tilEmail, tilPassword;
    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilFirstName = findViewById(R.id.activity_register_field_first_name);
        tilLastName = findViewById(R.id.activity_register_field_last_name);
        tilEmail = findViewById(R.id.activity_register_field_email);
        tilPassword = findViewById(R.id.activity_register_field_password);
        tilBirthday = findViewById(R.id.activity_register_field_birthday);
        btnRegister = findViewById(R.id.activity_register_btn_register);
        btnLogin = findViewById(R.id.activity_register_btn_login);

        tilBirthday.getEditText().setOnClickListener(view -> {
            DatePickerFragment.showDatePickerDialog(this, tilBirthday, new Date());
        });

        btnRegister.setOnClickListener(view -> {
            String firstName = tilFirstName.getEditText().getText().toString();
            String lastName = tilLastName.getEditText().getText().toString();
            String email = tilEmail.getEditText().getText().toString();
            String password = tilPassword.getEditText().getText().toString();
            String birthday = tilBirthday.getEditText().getText().toString();

            boolean validFirstName = new TilValidator(tilFirstName)
                    .required()
                    .strMin(3)
                    .isValid();
            boolean validLastName = new TilValidator(tilLastName)
                    .required()
                    .strMin(3)
                    .isValid();
            boolean validEmail = new TilValidator(tilEmail)
                    .required()
                    .email()
                    .isValid();
            boolean validPassword = new TilValidator(tilPassword)
                    .required()
                    .strMin(6)
                    .isValid();
            boolean validBirthday = new TilValidator(tilBirthday)
                    .required()
                    .date()
                    .dateBefore(DateUtils.addDays(new Date(), 1))
                    .isValid();

            if (!validFirstName || !validLastName || !validEmail || !validPassword || !validBirthday) {
                return;
            }

            Date birthdayDate = DateUtils.unsafeParse(birthday);

            User user = new User(firstName, lastName, email, birthdayDate);
            user.setPassword(password);

            AuthController controller = new AuthController(view.getContext());

            controller.register(user);
        });

        btnLogin.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}