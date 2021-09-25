package cl.ciisa.todoapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.ciisa.todoapp.R;
import cl.ciisa.todoapp.models.Task;

public class TaskAdapter extends BaseAdapter {
    private Context ctx;
    private List<Task> taskList;

    public TaskAdapter(Context ctx, List<Task> taskList) {
        this.ctx = ctx;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return taskList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(ctx);

        view = inflater.inflate(R.layout.item_task, null);

        Task task = taskList.get(i);

        TextView tvTitle = view.findViewById(R.id.item_task_tv_title);
        TextView tvId = view.findViewById(R.id.item_task_tv_id);
        TextView tvDescription = view.findViewById(R.id.item_task_tv_description);

        tvId.setText(Long.toString(task.getId()));
        tvTitle.setText(task.getTitle());
        tvDescription.setText(task.getDescription());

        return view;
    }
}
