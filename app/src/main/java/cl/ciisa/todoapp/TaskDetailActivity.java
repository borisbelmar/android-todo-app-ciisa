package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import cl.ciisa.todoapp.controllers.TaskController;
import cl.ciisa.todoapp.models.Task;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView tvTitleId, tvTitle, tvDescription, tvDue;
    private Button btnDelete, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Task task = (Task) getIntent().getSerializableExtra("task");

        tvTitleId = findViewById(R.id.activity_task_detail_tv_title_id);
        tvTitle = findViewById(R.id.activity_task_detail_tv_title);
        tvDescription = findViewById(R.id.activity_task_detail_tv_description);
        tvDue = findViewById(R.id.activity_task_detail_tv_due);
        btnBack = findViewById(R.id.activity_task_detail_btn_back);
        btnDelete = findViewById(R.id.activity_task_detail_btn_delete);

        tvTitleId.setText(String.format("Detalles de la tarea %d", task.getId()));
        tvTitle.setText(task.getTitle());
        tvDescription.setText(task.getDescription());
        tvDue.setText(task.getStringDue());

        btnDelete.setOnClickListener(view -> {
            TaskController controller = new TaskController(view.getContext());
            controller.delete(task.getId());
        });

        btnBack.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }
}