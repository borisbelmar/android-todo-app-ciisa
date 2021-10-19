package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.ciisa.todoapp.controllers.AuthController;
import cl.ciisa.todoapp.controllers.TaskController;
import cl.ciisa.todoapp.lib.TilValidator;
import cl.ciisa.todoapp.models.Task;
import cl.ciisa.todoapp.models.User;
import cl.ciisa.todoapp.ui.DatePickerFragment;
import cl.ciisa.todoapp.ui.TaskAdapter;
import cl.ciisa.todoapp.utils.DateUtils;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle, tvClearFilter;
    private ListView lvAllTasks;
    private TextInputLayout tilFrom, tilTo;
    private Button btnLogout, btnNewTask, btnFilter;
    private AuthController authController;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authController = new AuthController(this);
        taskController = new TaskController(this);

        tvTitle = findViewById(R.id.activity_main_tv_title);
        tvClearFilter = findViewById(R.id.activity_main_tv_clear_filter);
        lvAllTasks = findViewById(R.id.activity_main_lv_all_tasks);
        tilFrom = findViewById(R.id.activity_main_til_from);
        tilTo = findViewById(R.id.activity_main_til_to);
        btnLogout = findViewById(R.id.activity_main_btn_logout);
        btnNewTask = findViewById(R.id.activity_main_btn_new_task);
        btnFilter = findViewById(R.id.activity_main_btn_filter);

        tilFrom.getEditText().setOnClickListener(view -> {
            DatePickerFragment.showDatePickerDialog(this, tilFrom, new Date());
        });

        tilTo.getEditText().setOnClickListener(view -> {
            DatePickerFragment.showDatePickerDialog(this, tilTo, new Date());
        });

        User user = authController.getUserSession();
        tvTitle.setText(String.format("Notas de %s", user.getFirstName()));

        List<Task> taskList = taskController.getAll();
        this.setAllTasksAdapter(taskList);

        btnNewTask.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), NewTaskActivity.class);
            view.getContext().startActivity(i);
        });

        btnLogout.setOnClickListener(view -> { authController.logout(); });

        btnFilter.setOnClickListener(view -> {
            String fromStr = tilFrom.getEditText().getText().toString();
            String toStr = tilTo.getEditText().getText().toString();

            boolean validFrom = new TilValidator(tilFrom)
                    .required()
                    .date()
                    .dateBefore(DateUtils.unsafeParse(toStr))
                    .isValid();
            boolean validTo = new TilValidator(tilTo)
                    .required()
                    .date()
                    .dateAfter(DateUtils.unsafeParse(fromStr))
                    .isValid();

            if (validFrom && validTo) {
                Date from = DateUtils.unsafeParse(fromStr);
                Date to = DateUtils.unsafeParse(toStr);

                List<Task> taskRangeList = taskController.getRange(from, to);
                this.setAllTasksAdapter(taskRangeList);
            }
        });

        tvClearFilter.setOnClickListener(view -> {
            tilFrom.getEditText().setText("");
            tilTo.getEditText().setText("");
            this.setAllTasksAdapter(taskList);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Task> taskList = taskController.getAll();
        this.setAllTasksAdapter(taskList);
    }

    private void setAllTasksAdapter(List<Task> taskList) {
        TaskAdapter adapter = new TaskAdapter(this, taskList);
        lvAllTasks.setAdapter(adapter);

        lvAllTasks.setOnItemClickListener(((adapterView, view, index, id) -> {
            Task task = taskList.get(index);

            Intent i = new Intent(view.getContext(), TaskDetailActivity.class);
            i.putExtra("task", task);
            view.getContext().startActivity(i);
        }));
    }
}