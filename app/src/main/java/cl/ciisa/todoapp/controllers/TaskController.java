package cl.ciisa.todoapp.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.ciisa.todoapp.dao.TaskDao;
import cl.ciisa.todoapp.lib.TodoAppDatabase;
import cl.ciisa.todoapp.models.Task;
import cl.ciisa.todoapp.models.TaskEntity;
import cl.ciisa.todoapp.models.TaskMapper;
import cl.ciisa.todoapp.models.User;

public class TaskController {
    private Context ctx;
    private TaskDao taskDao;

    public TaskController(Context ctx) {
        this.ctx = ctx;
        this.taskDao = TodoAppDatabase.getInstance(ctx).taskDao();
    }

    public void register(Task task) {
        TaskMapper mapper = new TaskMapper(task);
        TaskEntity newTask = mapper.toEntity();
        taskDao.insert(newTask);
        ((Activity) ctx).onBackPressed();
    }

    public void delete(long id) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        taskDao.delete(id);
                        ((Activity) ctx).onBackPressed();
                    } catch(Error error) {
                        error.printStackTrace();
                        Toast.makeText(this.ctx, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
        builder.setMessage("Estás seguro de eliminar la tarea?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    public List<Task> getAll() {
        AuthController authController = new AuthController(ctx);
        User user = authController.getUserSession();
        List<TaskEntity> taskEntityList = taskDao.findAll(user.getId());
        List<Task> taskList = new ArrayList<>();

        for(TaskEntity taskEntity : taskEntityList) {
            TaskMapper mapper = new TaskMapper(taskEntity);
            Task task = mapper.toBase();
            taskList.add(task);
        }

        return taskList;
    }

    public List<Task> getRange(Date from, Date to) {
        AuthController authController = new AuthController(ctx);
        User user = authController.getUserSession();
        List<TaskEntity> taskEntityList = taskDao.findByRange(from, to, user.getId());
        List<Task> taskList = new ArrayList<>();

        for(TaskEntity taskEntity : taskEntityList) {
            TaskMapper mapper = new TaskMapper(taskEntity);
            Task task = mapper.toBase();
            taskList.add(task);
        }

        return taskList;
    }
}
