package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import cl.ciisa.todoapp.controllers.AuthController;
import cl.ciisa.todoapp.controllers.TaskController;
import cl.ciisa.todoapp.lib.TilValidator;
import cl.ciisa.todoapp.models.Task;
import cl.ciisa.todoapp.models.User;
import cl.ciisa.todoapp.ui.DatePickerFragment;
import cl.ciisa.todoapp.utils.DateUtils;

public class NewTaskActivity extends AppCompatActivity {
    private TextInputLayout tilTitle, tilDescription, tilDue;
    private Button btnRegister, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        tilTitle = findViewById(R.id.activity_new_task_field_title);
        tilDescription = findViewById(R.id.activity_new_task_field_description);
        tilDue = findViewById(R.id.activity_new_task_field_due);
        btnRegister = findViewById(R.id.activity_new_task_btn_register);
        btnBack = findViewById(R.id.activity_new_task_btn_back);

        tilDue.getEditText().setOnClickListener(view -> {
            DatePickerFragment.showDatePickerDialog(this, tilDue, new Date());
        });

        btnRegister.setOnClickListener(view -> {
            String title = tilTitle.getEditText().getText().toString();
            String description = tilDescription.getEditText().getText().toString();
            String due = tilDue.getEditText().getText().toString();



            boolean validTitle = new TilValidator(tilTitle)
                    .required()
                    .strMin(3)
                    .isValid();
            boolean validDescription = new TilValidator(tilDescription)
                    .strMin(3)
                    .isValid();
            boolean validDue = new TilValidator(tilDue)
                    .required()
                    .date()
                    .dateBefore(DateUtils.addDays(new Date(), 1))
                    .isValid();

            if (!validTitle || !validDescription || !validDue) {
                return;
            }

            Date dueDate = DateUtils.unsafeParse(due);

            AuthController authController = new AuthController(view.getContext());

            User user = authController.getUserSession();

            Task task = new Task(title, description, dueDate, user.getId());

            TaskController controller = new TaskController(view.getContext());

            controller.register(task);

            Toast.makeText(view.getContext(), "Registrar", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(view -> {
            super.onBackPressed();
        });

    }
}