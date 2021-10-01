package cl.ciisa.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.ciisa.todoapp.models.Task;
import cl.ciisa.todoapp.ui.TaskAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvAllTasks;
    private Button btnLogout;

    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAllTasks = findViewById(R.id.activity_main_lv_all_tasks);
        btnLogout = findViewById(R.id.activity_main_btn_logout);

        for (int x = 0; x < 10; ++x) {
            Task newTask = new Task(String.format("Title %d", x), String.format("Description %d", x));
            newTask.setId(x);
            taskList.add(newTask);
        }

        TaskAdapter adapter = new TaskAdapter(this, taskList);

        lvAllTasks.setAdapter(adapter);

        lvAllTasks.setOnItemClickListener(((adapterView, view, index, id) -> {
            Task task = taskList.get(index);

            Intent i = new Intent(view.getContext(), TaskDetailActivity.class);
            i.putExtra("task", task);
            view.getContext().startActivity(i);
        }));

        btnLogout.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Cerrando Sesión", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(view.getContext(), LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}